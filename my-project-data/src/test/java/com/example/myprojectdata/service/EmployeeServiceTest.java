package com.example.myprojectdata.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.myprojectdata.model.ck.Employee;
import com.example.myprojectdata.service.ck.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Please enter the class name.
 *
 * @author zohar
 * @version 1.0
 * 2021-12-01 23:56
 */
@Slf4j
@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 测试单表 MP 提供的方法查询
     */
    @Test
    void justTest() {
        Employee employee = assertDoesNotThrow(() -> employeeService.getById("1"));
        log.info("justTest||employee={}", employee);
    }

    /**
     * 测试使用 QueryWrapper 查询
     */
    @Test
    void testQueryWrapper() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "Jack");
        Employee employee = assertDoesNotThrow(() -> employeeService.getOne(queryWrapper));
        log.info("testQueryWrapper||employee={}", employee);
    }
}