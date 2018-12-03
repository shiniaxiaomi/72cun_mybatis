package com.lyj.service;

import com.lyj.dao.FolderDao;
import com.lyj.dao.UserDao;
import com.lyj.entity.User;
import com.lyj.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Created by Yingjie.Lu on 2018/9/17.
 */

@Service
public class UserService {


    @Autowired
    UserDao userDao;

    @Autowired
    FolderDao folderDao;


    public boolean isExists(User user){
        if(!StringUtil.isEmpty(user.getUserName())){
            int num= userDao.exists(user.getUserName());
            if(num==1){
                return true;
            }
        }
        return false;
    }

    public int saveUser(User user){
        return userDao.addUser(user);
    }

    public boolean login(User user, HttpSession session){
        if(user.getUserName()!=null && user.getPassword()!=null){
            User one = userDao.getUser(user.getUserName());
            if(one!=null && one.getPassword().equals(user.getPassword())){
                session.setAttribute("user",one);
                return true;
            }
        }

        return false;
    }


}
