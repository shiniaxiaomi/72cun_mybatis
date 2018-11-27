package com.lyj.entity;

import lombok.*;

/**
 * Created by 陆英杰
 * 2018/10/15 14:48
 */

@NoArgsConstructor(force = true) //生成无参构造方法
@Getter //让lombok自动生成getset方法和无参构造方法
@Setter
public class Folder {
    private int id;

    //是否有url(1表示文件夹下还有url;0表示文件夹下没有url)
    private int hasURL;

    private String name;

    private int pid;

    private int userId;

    //所拥有的文件夹的个数
    private int folderNum;


    //    public Folder(Integer id) {
//        this.id=id;
//    }
//
//    public Folder(Integer id,String name) {
//        this.id=id;
//        this.name=name;
//    }


    public Folder(int hasURL, String name, int pid, int userId, int folderNum) {
        this.hasURL = hasURL;
        this.name = name;
        this.pid = pid;
        this.userId = userId;
        this.folderNum = folderNum;
    }
}
