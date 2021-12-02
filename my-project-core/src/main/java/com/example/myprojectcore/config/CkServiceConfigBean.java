package com.example.myprojectcore.config;

import com.example.myprojectdata.service.ck.DepartmentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CkServiceConfigBean {

    @Bean
    public DepartmentService departmentService() {
        return new DepartmentService();
    }
}
