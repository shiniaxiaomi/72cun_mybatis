package com.lyj.dao;

import com.lyj.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by 陆英杰
 * 2018/10/15 14:54
 */
public interface FolderDao extends JpaRepository<Folder,Integer> {

    //可以通过对象_属性来加入对象中的对象中的属性字段
    @Query("select new Folder(f.id,f.name,f.pid,f.folderNum,f.hasURL) from Folder f join f.user user where user.id=:userId")
    public List<Folder> findFoldersByUser_Id(@Param("userId") Integer userId);


    @Modifying
    @Query(value = "update folder f set f.folderNum=:num where f.id=:id",nativeQuery = true)
    public void updateFolderNumById(@Param("num") Integer folderNum,@Param("id") Integer folderId);


    //根据用户和pid获取文件夹
    public Folder findFolderByUser_IdAndPid(Integer userId,Integer pid);

}
