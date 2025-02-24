package com.smartHotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "修改密码")
public class EmployeeEditPasswordDTO {
    
    private Long empId;
    
    private String newPassword;
    
    private String authCode;
}
