package com.smartHotel.mapStructs;

import com.smartHotel.dto.AuthorityDTO;
import com.smartHotel.entity.Authorities;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorityMapStruct {
    AuthorityMapStruct instance = Mappers.getMapper(AuthorityMapStruct.class);
    
    /**
     * DTO转实体类
     * @param authorityDTO
     * @return
     */
    Authorities convertToAuthorities(AuthorityDTO authorityDTO);
}
