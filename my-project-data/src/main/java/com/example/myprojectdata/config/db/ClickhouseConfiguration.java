package com.example.myprojectdata.config.db;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

/**
 * Clickhouse 配置加载类
 *
 * @author zohar
 * @version 1.0
 * 2021-12-01 23:31
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "spring.datasource.ck")
public class ClickhouseConfiguration {
    private String url;
    private String username;
    private String password;
    private String driverClassName;

    @PostConstruct
    public void log() {
        log.info("Configuration loaded: {}", this);
    }
}
