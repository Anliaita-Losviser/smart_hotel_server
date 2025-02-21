package com.smartHotel.mapStructs;

import com.smartHotel.dto.EmployeeDTO;
import com.smartHotel.entity.Employees;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * BeanUtils.copyProperties失效，使用mapstruct替代
 */
@Mapper
public interface EmployeeMapStruct {
    EmployeeMapStruct instance = Mappers.getMapper(EmployeeMapStruct.class);
    
    /**
     * 实体类转换为DTO
     * @param employees
     * @return
     */
    EmployeeDTO convertToEmployeeDTO(Employees employees);
    
    /**
     * DTO转换为实体
     * @param employeeDTO
     * @return
     */
    Employees convertToEmployees(EmployeeDTO employeeDTO);
}
