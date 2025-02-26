package com.smartHotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "添加权限时传递的数据模型")
public class AuthorityDTO {
    private Long id;
    
    private String name;
    
    private String code;
}
