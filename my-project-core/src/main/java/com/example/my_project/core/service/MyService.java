package com.example.my_project.core.service;

import com.example.my_project.data.model.ck.Department;
import com.example.my_project.data.service.ck.DepartmentService;
import com.sun.tools.javac.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    @Autowired
    DepartmentService departmentService;

    public List<Department> getSimple() {
        Department list = departmentService.getTest();
        return null;
    }
}
