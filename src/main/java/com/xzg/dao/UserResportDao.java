package com.xzg.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xzg.domain.User;
@Repository
public interface UserResportDao extends JpaRepository<User, Long>{
	User findByUserName(String name);
	
	@Transactional
    @Modifying
    @Query("update User t set t.userName = :userName where t.id = :id")
    int updateUserById(@Param("userName") String userName, @Param("id") int id);

    @Query("select t from User t ")
    List<User> getUserList();
    
}
