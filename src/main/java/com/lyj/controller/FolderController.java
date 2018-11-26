package com.lyj.controller;

import com.lyj.entity.Folder;
import com.lyj.entity.Result;
import com.lyj.entity.User;
import com.lyj.service.FolderService;
import com.lyj.util.VarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by 陆英杰
 * 2018/10/15 14:53
 */

@RestController
@RequestMapping("/folder")
public class FolderController {

    @Autowired
    FolderService folderService;


    @RequestMapping("/query")
    public List<Folder> query(HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Folder> folders = folderService.query(user.getId());

        return folders;

    }

    @RequestMapping("/insertChildren")
    public Result insertChildren(Folder folder, HttpSession session){

        User user = (User) session.getAttribute("user");

        folder.setUser(user);
        folder.setHasURL(VarUtil.intFalse);
        folder.setFolderNum(0);

        return folderService.insertChildren(folder);

    }

    @RequestMapping("/delete")
    public Result delete(Folder folder,boolean isDefaultFolder,HttpSession session){

        User user = (User) session.getAttribute("user");

        return folderService.delete(folder,isDefaultFolder,user);
    }

    @RequestMapping("/update")
    public Result update(Folder folder){

        return folderService.update(folder);
    }

    @RequestMapping("/getRootFolderId")
    public int getRootFolderId(HttpSession session){
        User user = (User) session.getAttribute("user");
        return folderService.getRootFolderId(user);
    }


}
