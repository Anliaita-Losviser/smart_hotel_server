package com.smartHotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "添加员工时传递的数据模型")
public class EmployeeDTO implements Serializable {

    @Schema($schema = "id-使用雪花算法生成")
    private Long id;

    private String username;

    private String name;

    private String phone;
    
    @Schema($schema = "1女，2男")
    private String sex;

    private String idNumber;

}
