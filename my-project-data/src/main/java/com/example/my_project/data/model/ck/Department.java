package com.example.my_project.data.model.ck;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * Please enter the class name.
 *
 * @author zohar
 * @version 1.0
 * 2021-12-01 23:49
 */
@Data
@Builder
@TableName("test_department")
public class Department {
    private String id;
    private String name;
}
