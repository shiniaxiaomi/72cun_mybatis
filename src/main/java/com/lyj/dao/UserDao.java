package com.lyj.dao;

import com.lyj.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by 陆英杰
 * 2018/9/25 9:42
 */
@Repository
public interface UserDao{


    @Select("select count(1) from user where userName=#{userName}")
    int exists(String userName);

    @Insert("insert into user (password,userName) values (#{password},#{userName})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id") //数据插入成功后，id值被反填到user对象中，调用getId()就可以获取
    int addUser(User user);

    @Select("select * from user where userName=#{userName}")
    User getUser(String userName);

}
