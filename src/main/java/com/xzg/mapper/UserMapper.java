package com.xzg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import com.xzg.domain.User;

//@Mapper
public interface UserMapper {

    List<User> getAll();

    User getOne(Long id);
    
    void insert(User user);

    void update(User user);
    
    @Delete("DELETE FROM t_user WHERE id =#{id}")
    void delete(Long id);


}
