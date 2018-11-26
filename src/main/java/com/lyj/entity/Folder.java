package com.lyj.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 陆英杰
 * 2018/10/15 14:48
 */

@Entity
public class Folder {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer pid;

    //所拥有的文件夹的个数
    private Integer folderNum;

    //是否有url(1表示文件夹下还有url;0表示文件夹下没有url)
    private Integer hasURL;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "folder")
    private Set<URL> urls=new HashSet<>();

    public Folder() {
    }

    public Folder(Integer id) {
        this.id=id;
    }

    public Folder(Integer id,String name) {
        this.id=id;
        this.name=name;
    }

    public Folder(String name, Integer pid, Integer folderNum, Integer hasURL, User user) {
        this.name = name;
        this.pid = pid;
        this.folderNum = folderNum;
        this.hasURL = hasURL;
        this.user = user;
    }

    public Folder(Integer id, String name, Integer pid, Integer folderNum, Integer hasURL){
        this.id=id;
        this.name = name;
        this.pid = pid;
        this.folderNum = folderNum;
        this.hasURL = hasURL;
    }

    public Folder(Integer folderId, String folderName, Integer pid) {
        this.id=folderId;
        this.name = folderName;
        this.pid = pid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getFolderNum() {
        return folderNum;
    }

    public void setFolderNum(Integer folderNum) {
        this.folderNum = folderNum;
    }

    public Set<URL> getUrls() {
        return urls;
    }

    public void setUrls(Set<URL> urls) {
        this.urls = urls;
    }

    public Integer getHasURL() {
        return hasURL;
    }

    public void setHasURL(Integer hasURL) {
        this.hasURL = hasURL;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
