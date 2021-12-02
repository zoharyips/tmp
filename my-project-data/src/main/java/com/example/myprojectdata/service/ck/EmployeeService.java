package com.example.myprojectdata.service.ck;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myprojectdata.mapper.ck.EmployeeMapper;
import com.example.myprojectdata.model.ck.Employee;
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
