package com.smartHotel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.smartHotel.dto.EmployeePageQueryDTO;
import com.smartHotel.entity.Employees;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employees> {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employees where username = #{username}")
    Employees getByUsername(String username);

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employees> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);
    
}
