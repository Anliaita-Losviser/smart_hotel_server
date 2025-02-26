package com.smartHotel.dao;

import com.smartHotel.entity.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 权限表持久层
 */
@Repository
public interface AuthorityDao extends JpaRepository<Authorities,Long> {
    Authorities findByName(String name);
    Authorities findByCode(String code);
}
