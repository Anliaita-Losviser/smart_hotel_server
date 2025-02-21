package com.smartHotel.service.impl;

import com.smartHotel.config.SnowflakeIdGenerator;
import com.smartHotel.service.IdGenerateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IdGenerateServiceImpl implements IdGenerateService {
    @Resource(name = "snowflakeIdGenerator")
    private SnowflakeIdGenerator snowflakeIdGenerator;
    
    @Override
    public long generateUserId() {
        return snowflakeIdGenerator.nextId();
    }
}
