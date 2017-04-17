package com.xzg.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xzg.domain.User;

@Repository
@Transactional
public interface UserMapper {

    List<User> getAll();

    User getOne(Long id);
    
    void insert(User user);

    void updateUser(User user);
    
}
