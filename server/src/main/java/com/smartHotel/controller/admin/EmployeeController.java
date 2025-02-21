package com.smartHotel.controller.admin;

import com.smartHotel.constant.JwtClaimsConstant;
import com.smartHotel.dto.EmployeeDTO;
import com.smartHotel.dto.EmployeeLoginDTO;
import com.smartHotel.entity.Employees;
import com.smartHotel.properties.JwtProperties;
import com.smartHotel.result.Result;
import com.smartHotel.service.EmployeeService;
import com.smartHotel.service.IdGenerateService;
import com.smartHotel.utils.JwtUtil;
import com.smartHotel.vo.EmployeeLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */

@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Schema(description = "员工管理控制器与相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @Operation(summary = "员工登陆操作")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employees employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @Operation(summary = "员工登出操作")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }
    
    /**
     * 新增
     * @param employeeDTO
     * @return
     */
    @Operation(summary = "新增员工操作")
    @PostMapping
    public Result addNewEmployee(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工:{}",employeeDTO);
        
        employeeService.addNewEmployee(employeeDTO);
        return Result.success();
    }
    
    
    @Resource(name = "idGenerateServiceImpl")
    private IdGenerateService idGenerateService;
    /**
     * 雪花算法生成id
     * @return
     */
    @GetMapping("/generate-id")
    public String generateId() {
        long id = idGenerateService.generateUserId();
        return "生成的 ID: " + id;
    }
}
