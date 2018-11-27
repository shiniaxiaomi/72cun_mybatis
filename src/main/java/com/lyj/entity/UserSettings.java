package com.lyj.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by 陆英杰
 * 2018/10/17 12:03
 */

@NoArgsConstructor(force = true) //生成无参构造方法
@Getter
@Setter
public class UserSettings {

    private Integer id;

    private int userId;

    private Integer defaultFolderId;//设置自定义文件夹

    private String defaultFolderName;//自定义文件夹名称

    public UserSettings(Integer defaultFolderId, String defaultFolderName) {
        this.defaultFolderId = defaultFolderId;
        this.defaultFolderName = defaultFolderName;
    }
}
