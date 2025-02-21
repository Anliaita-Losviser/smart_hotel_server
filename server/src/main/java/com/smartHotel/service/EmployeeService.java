package com.smartHotel.service;

import com.smartHotel.dto.EmployeeDTO;
import com.smartHotel.dto.EmployeeLoginDTO;
import com.smartHotel.entity.Employees;

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
}
