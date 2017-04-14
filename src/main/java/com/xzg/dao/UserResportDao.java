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
@Transactional
public interface UserResportDao extends JpaRepository<User, Long>{
	
	User findByUserName(String name);
/*@Query 注解中编写 JPQL 语句，但必须使用@Modifying 进行修饰. 以通知 SpringData,这是一个UPDATE或DELETE操作 */
    @Modifying
    @Query("update User t set t.userName = :userName where t.id = :id")
    int updateUserById(@Param("userName") String userName, @Param("id") int id);

    @Query("select t from User t ")
    List<User> getUserList();
   
    @Query("select t from User t where t.id = :id")
    User findByUserId(@Param("id")int id);
    
}
