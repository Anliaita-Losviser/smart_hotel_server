package com.smartHotel.service.impl;

import com.smartHotel.config.SnowflakeIdGenerator;
import com.smartHotel.service.IdGenerateService;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IdGenerateServiceImpl implements IdGenerateService, IdentifierGenerator {
    @Resource(name = "snowflakeIdGenerator")
    private SnowflakeIdGenerator snowflakeIdGenerator;
    
    @Override
    public long generateUserId() {
        return snowflakeIdGenerator.nextId();
    }
    
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        return snowflakeIdGenerator.nextId();
    }
}
