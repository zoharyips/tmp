package com.example.my_project.data.service.ck;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.my_project.data.mapper.ck.DepartmentMapper;
import com.example.my_project.data.model.ck.Department;
import org.springframework.stereotype.Service;

/**
 * Please enter the class name.
 *
 * @author zohar
 * @version 1.0
 * 2021-12-01 23:53
 */
@Service
public class DepartmentService extends ServiceImpl<DepartmentMapper, Department> implements IService<Department> {

    public Department getTest() {
        return this.getById("1");
    }
}
