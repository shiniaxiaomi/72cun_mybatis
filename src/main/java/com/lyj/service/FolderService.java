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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

        Folder f=new Folder();
        f.setPid(0);
        f.setName("默认文件夹");
        f.setFolderNum(0);
        f.setHasURL(VarUtil.intFalse);
        f.setUser(user);

        folderDao.save(f);
    }


    public List<Folder> query(Integer userId){

        List<Folder> folders = folderDao.findFoldersByUser_Id(userId);
        return folders;
    }

    public Integer queryRootFolderId(Integer userId){
        Folder folder = folderDao.findFolderByUser_IdAndPid(userId, 0);
        return folder.getId();
    }

    @Transactional
    public Result insertChildren(Folder folder) {

        Folder save = folderDao.save(folder);
        if(save!=null){

            Optional<Folder> folder1 = folderDao.findById(folder.getPid());

            folderDao.updateFolderNumById(folder1.get().getFolderNum()+1,folder1.get().getId());


            return ResultUtil.success("保存成功!",
                    new Folder(save.getId(),save.getName(),save.getPid(),save.getFolderNum(),save.getHasURL()));

        }
        return ResultUtil.error("保存失败!");
    }

    @Transactional
    public Result delete(Folder folder, boolean isDefaultFolder, User user) {

        Optional<Folder> folder1 = folderDao.findById(folder.getId());
        if(!folder1.isPresent()){
            return ResultUtil.error("文件夹不存在");
        }

        Folder folder2=folder1.get();
        if(folder2.getFolderNum()>0){
            return ResultUtil.error("该文件夹下还有文件夹,先删除子文件夹!");
        }

        folderDao.delete(folder2);

        Optional<Folder> pFloder = folderDao.findById(folder.getPid());
        if(!pFloder.isPresent()){
            return ResultUtil.error("父文件夹不存在");
        }

        Folder pFloder1=pFloder.get();
        folderDao.updateFolderNumById(pFloder1.getFolderNum()-1,pFloder1.getId());

        if(isDefaultFolder){//将自定义文件夹设置成默认文件夹
            UserSettings settings = userSettingsDao.findByUser_Id(user.getId());
            Folder rootFolder = folderDao.findFolderByUser_IdAndPid(user.getId(), 0);//查找根文件夹的id
            settings.setDefaultFolderId(rootFolder.getId());
            userSettingsDao.save(settings);
        }

        //将删除文件夹下的所有url删除
        urlDao.deleteByFolder_Id(folder.getId());

        return ResultUtil.success("删除成功!");
    }

    @Transactional
    public Result update(Folder folder) {

        Folder folder1 = folderDao.findById(folder.getId()).get();
        folder1.setName(folder.getName());

        Integer last_pid=folder1.getPid();
        Integer now_pid=folder.getPid();


        if(last_pid==now_pid){//如果没有改变文件夹的位置
            folderDao.save(folder1);
        }else {
            folder1.setPid(now_pid);
            folderDao.save(folder1);

            //修改现在父文件夹的信息
            Folder pFolder = folderDao.findById(now_pid).get();
            folderDao.updateFolderNumById(pFolder.getFolderNum()+1,now_pid);
            //修改原文件夹的信息
            Folder pFolder2 = folderDao.findById(last_pid).get();
            folderDao.updateFolderNumById(pFolder2.getFolderNum()-1,last_pid);
        }

        return ResultUtil.success("更改成功!");
    }

    public int getRootFolderId(User user) {
        Folder folder = folderDao.findFolderByUser_IdAndPid(user.getId(), 0);
        return folder.getId();
    }
}
