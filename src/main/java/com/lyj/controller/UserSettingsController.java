package com.lyj.controller;

import com.lyj.entity.Result;
import com.lyj.entity.User;
import com.lyj.entity.UserSettings;
import com.lyj.service.FolderService;
import com.lyj.service.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by 陆英杰
 * 2018/10/17 12:12
 */

@RestController
@RequestMapping("/userSettings")
public class UserSettingsController {


    @Autowired
    UserSettingsService userSettingsService;

    @RequestMapping("/query")
    public UserSettings query(HttpSession session){
        User user = (User) session.getAttribute("user");

        return userSettingsService.query(user);
    }


    @RequestMapping("/update")
    public Result update(HttpSession session, UserSettings userSettings){

        User user = (User) session.getAttribute("user");

        return userSettingsService.update(user,userSettings);
    }


}
