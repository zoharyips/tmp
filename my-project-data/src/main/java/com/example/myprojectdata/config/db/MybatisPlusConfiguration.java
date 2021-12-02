package com.example.myprojectdata.config.db;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * MP 配置类
 *
 * @author zohar
 * @version 1.0
 * 2021-12-01 23:37
 */
@Configuration
@MapperScan(value = "com.example.mp.demo.mapper.ck", sqlSessionFactoryRef = "ckSqlSessionFactory")
public class MybatisPlusConfiguration {

    /**
     * 创建基于 ck 的 Mybatis Plus SQL Session Factory
     *
     * @return clickhouseDruidSqlSessionFactory
     * @throws Exception Unknown
     */
    @Bean(name = "ckSqlSessionFactory")
    public SqlSessionFactory ckSqlSessionFactory(DataSource ckDruidDataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(ckDruidDataSource);
        // 可以增加许多额外功能
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.addInterceptor(new MybatisLogInterceptor());
        factoryBean.setConfiguration(configuration);
        return factoryBean.getObject();
    }
}
