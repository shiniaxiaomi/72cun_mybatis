package com.lyj.service;

import com.lyj.dao.FolderDao;
import com.lyj.dao.URLDao;
import com.lyj.dao.UserSettingsDao;
import com.lyj.entity.Folder;
import com.lyj.entity.Result;
import com.lyj.entity.User;
import com.lyj.entity.UserSettings;
import com.lyj.util.ResultUtil;
import com.lyj.util.VarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by 陆英杰
 * 2018/10/15 14:54
 */

@Service
public class FolderService {

    @Autowired
    FolderDao folderDao;

    @Autowired
    UserSettingsDao userSettingsDao;

    @Autowired
    URLDao urlDao;

    public void insertDefaultFolder(User user){
        Folder folder = new Folder(VarUtil.intFalse, "默认文件夹", 0, user.getId(), 0);
        folderDao.addFolder(folder);
    }


    public List<Folder> getByUserId(Integer userId){
        List<Folder> folders = folderDao.getByUserId(userId);
        return folders;
    }

    //获取根目录的id
    public Integer getRootFolderId(Integer userId){
        Folder folder = folderDao.getFolderByUserIdAndPid(userId, 0);
        return folder.getId();
    }

//    @Transactional
    public Result insertChildren(Folder folder) {

        int flag=folderDao.addFolder(folder);//新增folder,并获取到了自增id
        if(flag==1){
            folderDao.incrFoderNumById(folder.getPid());//更新父folder的num
            return ResultUtil.success("保存成功!");

        }else{
            return ResultUtil.error("保存失败!");
        }
    }

//    @Transactional
    public Result delete(Folder folder, boolean isDefaultFolder, User user) {

        Folder folder2 = folderDao.getFolderById(folder.getId());
        if(folder2==null){
            return ResultUtil.error("文件夹不存在");
        }

        if(folder2.getFolderNum()>0){
            return ResultUtil.error("该文件夹下还有文件夹,先删除子文件夹!");
        }

        //将父文件夹个数减1
        folderDao.deleteById(folder2.getId());
        Folder pFloder = folderDao.getFolderById(folder.getPid());
        if(pFloder==null){
            return ResultUtil.error("父文件夹不存在");
        }
        folderDao.decrFolderNumById(pFloder.getId());

        if(isDefaultFolder){//将自定义文件夹设置成默认文件夹
            UserSettings settings = userSettingsDao.getUserSettingsByUserId(user.getId());
            int rootFolderId = folderDao.getFolderIdByUserIdAndPid(user.getId(), 0);//查找根文件夹的id
            userSettingsDao.updateDefaultFolderId(rootFolderId,user.getId());
        }

        //将删除文件夹下的所有url删除
        urlDao.deleteByFolderId(folder.getId());

        return ResultUtil.success("删除成功!");
    }

//    @Transactional
    public Result update(Folder folder) {

        Folder folder1 = folderDao.getFolderById(folder.getId());
        folder1.setName(folder.getName());

        Integer last_pid=folder1.getPid();
        Integer now_pid=folder.getPid();


        if(last_pid==now_pid){//如果没有改变文件夹的位置
            folderDao.update(folder1);
        }else {
            folder1.setPid(now_pid);
            folderDao.update(folder1);

            //增加现在父文件夹的FoderNum
            folderDao.incrFoderNumById(now_pid);
            //增加原文件夹的FoderNum
            folderDao.decrFolderNumById(last_pid);
        }

        return ResultUtil.success("更改成功!");
    }

    public int getRootFolderId(User user) {
        return folderDao.getFolderIdByUserIdAndPid(user.getId(), 0);//查找根文件夹的id
    }

    public String getFolderNameById(int id){
        return folderDao.getFolderNameById(id);
    }
}
