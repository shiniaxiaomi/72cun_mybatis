package com.lyj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


/**
 * Created by 陆英杰
 * 2018/9/25 11:36
 */

@NoArgsConstructor(force = true) //生成无参构造方法
@Getter
@Setter
public class URL {
    private Integer id;

    private String url;

    private String name;

    private int folderId;

    private int userId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;//创建时间

    private String location;

    public URL(String name, String url) {
        this.name = name;
        this.url = url;
    }

}
