package com.smartHotel.service;

import com.smartHotel.dto.EmployeeDTO;
import com.smartHotel.dto.EmployeeEditPasswordDTO;
import com.smartHotel.dto.EmployeeLoginDTO;
import com.smartHotel.dto.EmployeePageQueryDTO;
import com.smartHotel.entity.Employees;
import com.smartHotel.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employees login(EmployeeLoginDTO employeeLoginDTO);
    
    /**
     * 新增员工
     * @param employeeDTO
     */
    void addNewEmployee(EmployeeDTO employeeDTO);
    
    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);
    
    /**
     * 启用或禁用员工
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);
    
    /**
     * 根据ID查询员工
     * @param id
     * @return
     */
    Employees getById(Long id);
    
    /**
     * 编辑员工信息
     * @param employeeDTO
     */
    void update(EmployeeDTO employeeDTO);
    
    /**
     * 修改密码
     * @param employeeEditPasswordDTO
     */
    void editPassword(EmployeeEditPasswordDTO employeeEditPasswordDTO);
}
