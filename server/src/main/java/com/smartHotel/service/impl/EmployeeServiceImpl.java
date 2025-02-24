package com.smartHotel.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartHotel.constant.MessageConstant;
import com.smartHotel.constant.PasswordConstant;
import com.smartHotel.constant.StatusConstant;
import com.smartHotel.dto.EmployeeDTO;
import com.smartHotel.dto.EmployeeEditPasswordDTO;
import com.smartHotel.dto.EmployeeLoginDTO;
import com.smartHotel.dto.EmployeePageQueryDTO;
import com.smartHotel.entity.Employees;
import com.smartHotel.exception.AccountLockedException;
import com.smartHotel.exception.AccountNotFoundException;
import com.smartHotel.exception.PasswordErrorException;
import com.smartHotel.mapStructs.EmployeeMapStruct;
import com.smartHotel.mapper.EmployeeMapper;
import com.smartHotel.result.PageResult;
import com.smartHotel.service.EmployeeService;
import com.smartHotel.service.IdGenerateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Resource(name = "employeeMapper")
    private EmployeeMapper employeeMapper;
    
    @Resource(name = "idGenerateServiceImpl")
    private IdGenerateService idGenerateService;

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    @Override
    public Employees login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();
        //进行md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //1、根据用户名查询数据库中的数据
        Employees employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus().equals(StatusConstant.DISABLE)) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }
    
    /**
     * 新增员工
     * @param employeeDTO
     */
    @Override
    public void addNewEmployee(EmployeeDTO employeeDTO) {
        Employees employee,selectEmployee;
        //对象属性拷贝，DTO转实体
        employee = EmployeeMapStruct.instance.convertToEmployees(employeeDTO);
        
        //先根据username查询用户，查到了说明数据库里面有了，报异常
        selectEmployee = employeeMapper.getByUsername(employee.getUsername());
        if(selectEmployee != null){
            //账号存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_ALREADY_EXIST);
        }
        //生成id
        employee.setId(idGenerateService.generateUserId());
        //设置账号状态，ENABLE为启用
        employee.setStatus(StatusConstant.ENABLE);
        //设置密码，默认123456
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        //设置创建时间和修改时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        //设置用户权限，默认普通员工
        employee.setAuthority(null);
        employeeMapper.insert(employee);
    }
    
    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        Page<Employees> pages = employeeMapper.pageQuery(employeePageQueryDTO);
        long total = pages.getTotal();
        List<Employees> records = pages.getResult();
        return new PageResult(total,records);
    }
    
    /**
     * 启用或禁用账号
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        //构造实体对象传进去
        Employees employees = Employees.builder()
                .status(status)
                .id(id)
                .build();
        
        employeeMapper.update(employees);
    }
    
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @Override
    public Employees getById(Long id) {
        Employees employee = employeeMapper.getById(id);
        employee.setPassword("******");
        return employee;
    }
    
    /**
     * 编辑员工信息
     * @param employeeDTO
     */
    @Override
    public void update(EmployeeDTO employeeDTO) {
        Employees employee = EmployeeMapStruct.instance.convertToEmployees(employeeDTO);
        employee.setUpdateTime(LocalDateTime.now());
        log.info("即将更新：{}",employee);
        employeeMapper.update(employee);
    }
    
    /**
     * 修改密码
     * @param employeeEditPasswordDTO
     */
    @Override
    public void editPassword(EmployeeEditPasswordDTO employeeEditPasswordDTO) {
        //先查询要修改的员工
        Employees employee = employeeMapper.getById(employeeEditPasswordDTO.getEmpId());
        //设置新密码
        employee.setPassword(DigestUtils.md5DigestAsHex(employeeEditPasswordDTO.getNewPassword().getBytes()));
        employee.setUpdateTime(LocalDateTime.now());
        employeeMapper.update(employee);
    }
    
}
