package com.smartHotel.service;

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
     * 生成id
     * @return
     */
    long generateUserId();
}
