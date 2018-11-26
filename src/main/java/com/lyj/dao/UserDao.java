package com.lyj.dao;

import com.lyj.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 陆英杰
 * 2018/9/25 9:42
 */
public interface UserDao extends JpaRepository<User,Integer>{


}
