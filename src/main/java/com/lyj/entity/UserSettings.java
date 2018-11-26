package com.lyj.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by 陆英杰
 * 2018/10/17 12:03
 */

@Entity
public class UserSettings {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    private Integer defaultFolderId;//设置自定义文件夹

    @Transient
    private String defaultFolderName;//自定义文件夹名称


    public UserSettings() {
    }

    public UserSettings(Integer defaultFolderId, String defaultFolderName) {
        this.defaultFolderId = defaultFolderId;
        this.defaultFolderName = defaultFolderName;
    }

    public Integer getDefaultFolderId() {
        return defaultFolderId;
    }

    public void setDefaultFolderId(Integer defaultFolderId) {
        this.defaultFolderId = defaultFolderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDefaultFolderName() {
        return defaultFolderName;
    }

    public void setDefaultFolderName(String defaultFolderName) {
        this.defaultFolderName = defaultFolderName;
    }
}
