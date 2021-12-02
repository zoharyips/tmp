package com.example.my_project.data.service.ck;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.my_project.data.mapper.ck.EmployeeMapper;
import com.example.my_project.data.model.ck.Employee;
import org.springframework.stereotype.Service;

/**
 * Please enter the class name.
 *
 * @author zohar
 * @version 1.0
 * 2021-12-01 23:51
 */
@Service
public class EmployeeService extends ServiceImpl<EmployeeMapper, Employee> implements IService<Employee> {
}
