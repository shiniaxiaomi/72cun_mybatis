package com.lyj.dao;

import com.lyj.entity.URL;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by 陆英杰
 * 2018/9/25 9:42
 */
public interface URLDao extends JpaRepository<URL,Integer>{


//    @Query("select url from URL url where url.user")
//    List<URL> findByFolder(Integer userId, Integer folderId);

//    List<URL> findURLSByFolder_IdAndUser_Id(Integer userId, Integer folderId);

    @Query("select new URL(url.id,url.name,url.url,url.createTime,folder.id,folder.name)  from URL url join url.user user join url.folder folder where user.id=:userId and folder.id=:folderId")
    Page<URL> findByFolder(@Param("userId") Integer userId,@Param("folderId") Integer folderId,Pageable pageable);

    @Query("select new URL(url.id,url.name,url.url,url.createTime,folder.id,folder.name)  from URL url join url.user user join url.folder folder where user.id=:userId and folder.name like :keywords")
    Page<URL> findByFolder_Name(@Param("userId") Integer userId,@Param("keywords") String keywords,Pageable pageable);

    @Modifying
    @Query(value = "update url set name=:name,url=:url,folder_id=:pid where id=:id and user_id=:userId",nativeQuery = true)
    void update(@Param("name") String name,@Param("url") String url,@Param("pid") Integer pid,@Param("id") Integer urlId,@Param("userId") Integer userId);


//    @Query("select new com.lyj.entity.pojo.URL_POJO(url.id,url.name,url.url,folder.id,folder.name) from URL url join url.user user join url.folder folder where user.id=:userId and folder.id=:folderId")
//    @Query(value = "select u.id,u.name,u.url,u.folder_id,u.user_id from url u where u.user_id=:userId and u.name like :urlName",nativeQuery = true)

    @Query("select new URL(url.id,url.name,url.url,url.createTime,folder.id,folder.name) from URL url join url.user user " +
            "join url.folder folder where user.id=:userId and url.name like :urlName")
    Page<URL> findAllByUser_IdAndNameLike(@Param("userId") Integer userId, @Param("urlName") String urlName, Pageable pageable);

    @Query("select new URL(url.id,url.name,url.url,url.createTime,folder.id,folder.name) from URL url join url.user user " +
            "join url.folder folder where user.id=:userId and (url.name like :keywords or folder.name like :keywords)")
    Page<URL> findAllByUser_IdAndNameLikeAndFolder_NameLike(@Param("userId") Integer userId, @Param("keywords") String keywords, Pageable pageable);

//    @Modifying
//    public void deleteByFolder_IdAndUser_Id(Integer folderId,Integer userId);

    @Modifying
    @Query(value = "delete from url where folder_id=:folderId",nativeQuery = true)
    public void deleteByFolder_Id(@Param("folderId") Integer folderId);


}
