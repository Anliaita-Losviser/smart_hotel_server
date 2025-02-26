package com.smartHotel.service.impl;

import com.smartHotel.constant.MessageConstant;
import com.smartHotel.dao.AuthorityDao;
import com.smartHotel.dto.AuthorityDTO;
import com.smartHotel.entity.Authorities;
import com.smartHotel.exception.AccountNotFoundException;
import com.smartHotel.mapStructs.AuthorityMapStruct;
import com.smartHotel.service.AuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Slf4j
@Service
public class AuthorityServiceImpl implements AuthorityService {
    
    @Resource(name = "authorityDao")
    private AuthorityDao authorityDao;
    
    /**
     * 新增权限
     * @param authorityDTO
     */
    @Override
    public void addNewAuthority(AuthorityDTO authorityDTO) {
        Authorities authority,selectAuthority;
        //转换
        authority = AuthorityMapStruct.instance.convertToAuthorities(authorityDTO);
        //查询已有信息
        selectAuthority = authorityDao.findByName(authority.getName());
        if(selectAuthority!=null){
            //权限存在
            throw new AccountNotFoundException(MessageConstant.AUHTORYTY_ALREADY_EXIST);
        }
        selectAuthority = authorityDao.findByCode(authority.getCode());
        if(selectAuthority!=null){
            //权限存在
            throw new AccountNotFoundException(MessageConstant.AUHTORYTY_ALREADY_EXIST);
        }
        
        authority.setCreateTime(LocalDateTime.now());
        authority.setUpdateTime(LocalDateTime.now());
        authorityDao.save(authority);
    }
}
