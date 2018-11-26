package com.lyj.service;

import com.lyj.dao.UserSettingsDao;
import com.lyj.entity.Result;
import com.lyj.entity.User;
import com.lyj.entity.UserSettings;
import com.lyj.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by 陆英杰
 * 2018/10/17 12:11
 */

@Service
public class UserSettingsService {

    @Autowired
    UserSettingsDao userSettingsDao;

    @Autowired
    FolderService folderService;

    public void add(User user) {

        UserSettings userSettings=new UserSettings();
        userSettings.setUser(user);

        Integer rootFolderId = folderService.queryRootFolderId(user.getId());

        userSettings.setDefaultFolderId(rootFolderId);//默认设置的自定的文件夹是默认文件夹

        userSettingsDao.save(userSettings);
    }

    public Result update(User user, UserSettings userSettings){

        UserSettings setting = userSettingsDao.findByUser_Id(user.getId());
        setting.setDefaultFolderId(userSettings.getDefaultFolderId());

        UserSettings save = userSettingsDao.save(setting);
        if(save!=null){
            UserSettings settings = query(user);//查询名称
            return ResultUtil.success("更改成功!",settings);
        }else{
            return ResultUtil.error("更改失败!");
        }
    }

    public UserSettings query(User user) {
        Map settings = userSettingsDao.findIdAndNameByUser_Id(user.getId());
        UserSettings userSettings=new UserSettings();

        userSettings.setDefaultFolderId((Integer) settings.get("defaultFolderId"));
        userSettings.setDefaultFolderName((String) settings.get("defaultFolderName"));

        return userSettings;
    }
}
