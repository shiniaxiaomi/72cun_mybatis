package com.lyj.service;

import com.lyj.dao.UserSettingsDao;
import com.lyj.entity.Result;
import com.lyj.entity.User;
import com.lyj.entity.UserSettings;
import com.lyj.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        userSettings.setUserId(user.getId());

        Integer rootFolderId = folderService.getRootFolderId(user.getId());
        userSettings.setDefaultFolderId(rootFolderId);//默认设置的自定的文件夹是默认文件夹

        userSettingsDao.addUserSettings(userSettings);
    }

    public Result update(int defaultFolderId, int userId){

        int flag = userSettingsDao.updateDefaultFolderId(defaultFolderId,userId);
        if(flag==1){
            String folderName = folderService.getFolderNameById(defaultFolderId);//查询folderName

            UserSettings userSettings = new UserSettings();
            userSettings.setDefaultFolderId(defaultFolderId);
            userSettings.setDefaultFolderName(folderName);
            return ResultUtil.success("更改成功!",userSettings);
        }else{
            return ResultUtil.error("更改失败!");
        }
    }

    public UserSettings query(int userId) {

        return userSettingsDao.getUserSettingsByUserId(userId);

    }
}
