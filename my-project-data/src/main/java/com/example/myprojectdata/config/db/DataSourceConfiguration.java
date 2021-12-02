package com.example.myprojectdata.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 数据源管理
 *
 * @author zohar
 * @version 1.0
 * 2021-12-01 23:32
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({
        ClickhouseConfiguration.class
})
public class DataSourceConfiguration {

    /**
     * clickhouse 的 Druid 连接数据源
     *
     * @param clickhouseConfiguration ck 配置
     * @return 数据源
     */
    @Bean
    public DataSource ckDruidDataSource(ClickhouseConfiguration clickhouseConfiguration) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(clickhouseConfiguration.getUrl());
        dataSource.setUsername(clickhouseConfiguration.getUsername());
        dataSource.setPassword(clickhouseConfiguration.getPassword());
        dataSource.setDriverClassName(clickhouseConfiguration.getDriverClassName());
        return dataSource;
    }

}
