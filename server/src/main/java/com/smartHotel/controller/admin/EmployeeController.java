package com.smartHotel.controller.admin;

import com.smartHotel.constant.JwtClaimsConstant;
import com.smartHotel.constant.MessageConstant;
import com.smartHotel.dto.EmployeeDTO;
import com.smartHotel.dto.EmployeeEditPasswordDTO;
import com.smartHotel.dto.EmployeeLoginDTO;
import com.smartHotel.dto.EmployeePageQueryDTO;
import com.smartHotel.entity.Employees;
import com.smartHotel.properties.JwtProperties;
import com.smartHotel.result.PageResult;
import com.smartHotel.result.Result;
import com.smartHotel.service.EmployeeService;
import com.smartHotel.utils.JwtUtil;
import com.smartHotel.vo.EmployeeLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
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

    @Resource(name = "employeeServiceImpl")
    private EmployeeService employeeService;
    @Resource(name = "jwtProperties")
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
    
    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @Operation(summary = "员工分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("员工分页查询：{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }
    
    /**
     * 启用或禁用账号
     * @return
     */
    @Operation(summary = "启用或禁用账号")
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable("status") Integer status,Long id){
        log.info("启用或禁用员工账号{},{}",id,status);
        employeeService.startOrStop(status,id);
        return Result.success(MessageConstant.OPRATION_SUCCESS);
    }
    
    /**
     * 根据ID查询员工
     * @param id
     * @return
     */
    @Operation(summary = "根据ID查询员工")
    @GetMapping("/{id}")
    public Result<Employees> getById(@PathVariable Long id){
        Employees employee = employeeService.getById(id);
        return Result.success(employee);
    }
    
    /**
     * 编辑员工信息
     * @param employeeDTO
     * @return
     */
    @Operation(summary = "编辑员工信息")
    @PutMapping
    public Result update(@RequestBody EmployeeDTO employeeDTO){
        log.info("编辑员工信息:{}",employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }
    
    /**
     * 修改密码
     * @return
     */
    @Operation(summary = "修改密码")
    @PutMapping("/editPassword")
    public Result editPassword(@RequestBody EmployeeEditPasswordDTO employeeEditPasswordDTO){
        log.info("修改密码");
        employeeService.editPassword(employeeEditPasswordDTO);
        return Result.success();
    }
}
