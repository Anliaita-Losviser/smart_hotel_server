package com.smartHotel.service.impl;

import com.smartHotel.constant.MessageConstant;
import com.smartHotel.constant.PasswordConstant;
import com.smartHotel.constant.StatusConstant;
import com.smartHotel.dto.EmployeeDTO;
import com.smartHotel.dto.EmployeeLoginDTO;
import com.smartHotel.entity.Employees;
import com.smartHotel.exception.AccountLockedException;
import com.smartHotel.exception.AccountNotFoundException;
import com.smartHotel.exception.PasswordErrorException;
import com.smartHotel.mapStructs.EmployeeMapStruct;
import com.smartHotel.mapper.EmployeeMapper;
import com.smartHotel.service.EmployeeService;
import com.smartHotel.service.IdGenerateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
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
        Employees employees = new Employees();
        //对象属性拷贝，DTO转实体
        employees = EmployeeMapStruct.instance.convertToEmployees(employeeDTO);
        //生成id
        employees.setId(idGenerateService.generateUserId());
        //设置账号状态，ENABLE为启用
        employees.setStatus(StatusConstant.ENABLE);
        //设置密码，默认123456
        employees.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        //设置创建时间和修改时间
        employees.setCreateTime(LocalDateTime.now());
        employees.setUpdateTime(LocalDateTime.now());
        //设置用户权限，默认普通员工
        employees.setAuthority(null);
        employeeMapper.insert(employees);
    }
    
}
