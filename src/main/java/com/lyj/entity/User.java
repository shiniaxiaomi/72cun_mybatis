package com.lyj.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 陆英杰
 * 2018/9/25 9:40
 */

@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30,unique = true)
    private String userName;

    @Column(length = 30)
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<URL> urls=new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Folder> folders=new HashSet<>();

    @OneToOne(mappedBy = "user")
    private UserSettings userSettings;

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public Set<URL> getUrls() {
        return urls;
    }

    public void setUrls(Set<URL> urls) {
        this.urls = urls;
    }

    public Set<Folder> getFolders() {
        return folders;
    }

    public void setFolders(Set<Folder> folders) {
        this.folders = folders;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
