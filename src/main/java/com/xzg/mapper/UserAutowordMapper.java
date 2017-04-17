package com.xzg.mapper;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.xzg.domain.FriendsInfo;
import com.xzg.domain.UserSexEnum;

@Repository
@Transactional
public interface UserAutowordMapper {

	@Select("select * from friends_info")
	@Results({
	        @Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
	    })
	 List<FriendsInfo> findFriendsAll();
	@Select("SELECT * FROM friends_info WHERE friends_id = #{id}")
	@Results({
		@Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
	})
	FriendsInfo getFriendById(int id);
	
	@Delete("DELETE FROM friends_info WHERE friends_id =#{id}")
	 void deleteFriendsbyId(int id);
	
	@Insert("INSERT INTO friends_info(user_id,friends_id,friends_name,isonline,user_sex) "
			+ "VALUES(#{user_id}, #{friends_id}, #{friends_name}, #{isonline},#{userSex})")
	void insertFrined(FriendsInfo friendsInfo);

	@Update("UPDATE users SET friends_name=#{friends_name},isonline=#{isonline} WHERE friends_id =#{friends_id}")
	void update(FriendsInfo friendsInfo);
}
