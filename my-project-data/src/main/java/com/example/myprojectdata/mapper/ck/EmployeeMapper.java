package com.example.myprojectdata.mapper.ck;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myprojectdata.model.ck.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Please enter the class name.
 *
 * @author zohar
 * @version 1.0
 * 2021-12-01 23:50
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 查询最大 ID
     *
     * @return 最大 ID
     */
    @Select("SELECT id FROM test_employee ORDER BY id DESC LIMIT 1")
    String maxId();
}
