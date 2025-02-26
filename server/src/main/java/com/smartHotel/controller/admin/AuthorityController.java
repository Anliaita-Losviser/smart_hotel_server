package com.smartHotel.controller.admin;

import com.smartHotel.dto.AuthorityDTO;
import com.smartHotel.result.Result;
import com.smartHotel.service.AuthorityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/authority")
@Slf4j
@Schema(description = "权限管理控制器与相关接口")
public class AuthorityController {

    @Resource(name = "authorityServiceImpl")
    private AuthorityService authorityService;
    
    /**
     * 新增权限
     * @param authorityDTO
     * @return
     */
    @Operation(summary = "新增权限")
    @PostMapping
    public Result addNewAuthority(@RequestBody AuthorityDTO authorityDTO){
        log.info("新增权限：{}",authorityDTO);
        authorityService.addNewAuthority(authorityDTO);
        return Result.success();
    }
}
