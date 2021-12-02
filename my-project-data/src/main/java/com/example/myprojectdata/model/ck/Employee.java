package com.example.myprojectdata.model.ck;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * 雇员类
 * <p>
 * DESC: For test
 * </p>
 *
 * @author zohar
 * @version 1.0
 * 2021-12-01 23:45
 */
@Data
@Builder
@TableName("test_employee")
public class Employee {
    private String id;
    private String name;
    private Integer age;
    private String address;
    private String departmentId;
}
