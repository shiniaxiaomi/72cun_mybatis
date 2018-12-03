package com.lyj.dao;

import com.lyj.entity.Folder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 陆英杰
 * 2018/10/15 14:54
 */
@Repository
public interface FolderDao{

    //可以通过对象_属性来加入对象中的对象中的属性字段
//    @Query("select new Folder(f.id,f.name,f.pid,f.folderNum,f.hasURL) from Folder f join f.user user where user.id=:userId")
//    public List<Folder> findFoldersByUser_Id(@Param("userId") Integer userId);


//    @Modifying
//    @Query(value = "update folder f set f.folderNum=:num where f.id=:id",nativeQuery = true)
//    public void updateFolderNumById(@Param("num") Integer folderNum,@Param("id") Integer folderId);


    //根据用户和pid获取文件夹
//    public Folder findFolderByUser_IdAndPid(Integer userId,Integer pid);

    //增加folder
    @Insert("insert into folder (hasURL,name,pid,userId,folderNum) values (#{hasURL},#{name},#{pid},#{userId},#{folderNum})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id") //数据插入成功后，id值被反填到folder对象中，调用getId()就可以获取
    int addFolder(Folder f);

    //获取folder集合
    @Select("select * from folder where userId=#{userId}")
    List<Folder> getByUserId(Integer userId);

    //根据userId和pid获取folder
    @Select("select * from folder where userId=#{userId} and pid=#{pid}")
    Folder getFolderByUserIdAndPid(@Param("userId") Integer userId, @Param("pid") int pid);

    //递增folderNum
    @Update("update folder set folderNum=folderNum+1 where id=#{id}")
    void incrFoderNumById(Integer id);

    //获取folder
    @Select("select * from folder where id=#{id}")
    Folder getFolderById(Integer id);

    //删除folder
    @Delete("delete from folder where id=#{id}")
    void deleteById(Integer id);

    //递减folderNum
    @Update("update folder set folderNum=folderNum-1 where id=#{id}")
    void decrFolderNumById(Integer id);

    //获取folderId
    @Select("select id from folder where userId=#{userId} and pid=#{pid}")
    int getFolderIdByUserIdAndPid(@Param("userId")Integer userId,@Param("pid") int pid);

    //xml
    void update(Folder folder);

    @Select("select name from folder where id=#{id}")
    String getFolderNameById(int id);
}
