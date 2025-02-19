package com.smartHotel.service;

import com.smartHotel.dto.EmployeeLoginDTO;
import com.smartHotel.entity.Employee;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

}
