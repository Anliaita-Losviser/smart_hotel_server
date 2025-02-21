package com.smartHotel.mapper;

import com.smartHotel.entity.Employees;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employees where username = #{username}")
    Employees getByUsername(String username);
    
    /**
     * 新增员工
     * @param employees
     */
    @Insert("insert into employees values "+
            "(#{id},#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status},#{authority},#{createTime},#{updateTime})")
    void insert(Employees employees);
}
