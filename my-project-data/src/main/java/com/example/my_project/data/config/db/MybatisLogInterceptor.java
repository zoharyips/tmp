package com.example.my_project.data.config.db;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;


/**
 * Mybatis 日志拦截器，用于打印 Mybatis 查询日志
 *
 * 不仅支持 MybatisPlus，也支持 Mybatis
 *
 * Signature:
 * - type 拦截的类型 四大对象之一( Executor , ResultSetHandler , ParameterHandler , StatementHandler )
 * - method 拦截的方法
 * - args 方法的参数
 *
 * @author zohar
 * @version 1.0
 */
@Slf4j
@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class}
        ),
        @Signature(
                type = StatementHandler.class,
                method = "query",
                args = {Statement.class, ResultHandler.class}
        )})
public class MybatisLogInterceptor implements Interceptor {

    private static final DateFormat DATE_FORMAT =
            DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        switch (invocation.getMethod().getName()) {
            case "prepare":
                return handlePrepare(invocation);
            case "query":
                return handleQuery(invocation);
            default:
                return invocation.proceed();
        }
    }

    /**
     * 对 prepare 方法进行处理，目前用于打印 SQL
     *
     * @param invocation 调用方法
     * @return 调用结果
     * @throws InvocationTargetException 调用方法错误
     * @throws IllegalAccessException 调用方法作用域错误
     */
    static Object handlePrepare(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
        try {
            // 获取 StatementHandler ，默认是 RoutingStatementHandler
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            // 获取包装类真正的对象
            MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
            // 获取 SQL 查询的映射信息
            MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
            // 获取 SQL 绑定对象
            BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
            // 解析 SQL
            String sql = parseSql(mappedStatement.getConfiguration(), boundSql);
            // 获取执行的 Mapper 方法
            String sqlId = mappedStatement.getId();
            log.info("[Mybatis]    Active: {}", sqlId);
            log.info("[Mybatis] Preparing: {},", sql);
        } catch (Exception e){
            log.warn("handlePrepare||error={}", e.getMessage());
        }
        return invocation.proceed();
    }

    /**
     * 对 query 结果进行处理，目前仅打印结果数量
     *
     * @param invocation 调用方法
     * @return 调用结果
     * @throws InvocationTargetException 调用方法错误
     * @throws IllegalAccessException 调用方法作用域错误
     */
    @SuppressWarnings("rawtypes")
    static Object handleQuery(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
        List result = (List) invocation.proceed();
        log.info("[Mybatis]     Total: {}", result.size());
        return result;
    }

    /**
     * 解析完整 SQL
     *
     * @param configuration 配置
     * @param boundSql 绑定的 SQL
     * @return 完整 SQL
     */
    static String parseSql(Configuration configuration, BoundSql boundSql) {
        // 获取参数
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (CollectionUtils.isEmpty(parameterMappings) || Objects.isNull(parameterObject)) {
            return sql;
        }
        // 获取类型处理器注册器，类型处理器的功能是进行 java 类型和数据库类型的转换
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        // 如果根据 parameterObject.getClass(）可以找到对应的类型，则替换
        if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
            return sql.replaceFirst("\\?", Matcher.quoteReplacement(parseObjToStringParam(parameterObject)));
        }
        // MetaObject主要是封装了 originalObject 对象，提供了 get 和 set 方法操作属性
        // 主要支持对 JavaBean、Collection、Map 三种类型对象的操作
        MetaObject metaObject = configuration.newMetaObject(parameterObject);
        for (ParameterMapping parameterMapping : parameterMappings) {
            String propertyName = parameterMapping.getProperty();
            if (metaObject.hasGetter(propertyName)) {
                Object obj = metaObject.getValue(propertyName);
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(parseObjToStringParam(obj)));
            } else if (boundSql.hasAdditionalParameter(propertyName)) {
                // 该分支是动态sql
                Object obj = boundSql.getAdditionalParameter(propertyName);
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(parseObjToStringParam(obj)));
            } else {
                // 打印出缺失，提醒该参数缺失并防止错位
                sql = sql.replaceFirst("\\?", "缺失");
            }
        }
        return sql;
    }

    /**
     * 解析参数值
     *
     * @param obj 参数对象
     * @return 参数字符串表示
     */
    static String parseObjToStringParam(Object obj) {
        if (obj instanceof String) {
            return "'" + obj + "'";
        }
        if (obj instanceof Date) {
            return "'" + DATE_FORMAT.format(obj) + "'";
        }
        if (obj != null) {
            return obj.toString();
        }
        return "";
    }
}