package com.example.myprojectcore.service;

import com.example.myprojectdata.model.ck.Department;
import com.example.myprojectdata.service.ck.DepartmentService;
import com.sun.tools.javac.util.List;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    @Autowired
    DepartmentService departmentService;

    public List<Department> getSimple() {
        Department list = departmentService.getTest();
    }
}
