package com.example.my_project.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.my_project")
public class MyProjectApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyProjectApiApplication.class, args);
    }

}
