package com.xzg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xzg.domain.User;

@Repository
@Transactional
public interface UserMapper {

    List<User> getAll();

    User getOne(int id);
    
    void insert(User user);

    void updateUser(User user);
    
    int isExits(int id,String password);
    //检查用户密码并返回用户实例
    User returnUserByPw(@Param("id") int id,@Param("password")String password);
    
    void updateUserIp(User user);
}
