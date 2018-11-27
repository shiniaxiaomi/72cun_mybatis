package com.lyj.dao;

import com.lyj.entity.URL;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 陆英杰
 * 2018/9/25 9:42
 */
@Repository
public interface URLDao{
    @Delete("delete from url where folderId=#{folderId}")
    void deleteByFolderId(Integer folderId);


    @Select("select u.id,u.url,u.name,u.folderId,u.createTime,f.name as location " +
            "from url u left join folder f on u.folderId=f.id " +
            "where u.userId=#{userId} and u.folderId=#{folderId} order by u.createTime desc")
    List<URL> getUrlsByUserIdAndFolderId(@Param("userId") Integer userId, @Param("folderId")int folderId,RowBounds rowBounds);//分页
    @Select("select count(1) from url where userId=#{userId} and folderId=#{folderId}")
    int getUrlsCountByUserIdAndFolderId(@Param("userId")Integer userId, @Param("folderId")int folderId);//查询总数

    //分页
    @Select("select u.id,u.url,u.name,u.folderId,u.createTime,f.name as location " +
            "from url u left join folder f on u.folderId=f.id " +
            "where u.userId=#{userId} and u.name like CONCAT('%',#{keywords},'%') order by u.createTime desc")
    List<URL> getUrlsByUserIdAndKeywords(@Param("userId")Integer userId,@Param("keywords") String keywords,RowBounds rowBounds);//分页
    @Select("select count(1) from url where userId=#{userId} and name like CONCAT('%',#{keywords},'%')")
    int getUrlsCountByKeywords(@Param("userId")Integer userId,@Param("keywords") String keywords);//查询总数

    @Select("select u.id,u.url,u.name,u.folderId,u.createTime,f.name as location " +
            "from url u left join folder f on u.folderId=f.id " +
            "where u.userId=#{userId} and f.name like CONCAT('%',#{keywords},'%') order by u.createTime desc")
    List<URL> queryByFolderName(@Param("userId")Integer userId,@Param("keywords") String keywords, RowBounds rowBounds);//分页
    @Select("select count(1) from url u left join folder f on u.folderId=f.id where u.userId=#{userId} and f.name like CONCAT('%',#{keywords},'%')")
    int getUrlsCountByolderName(@Param("userId")Integer userId,@Param("keywords") String keywords);//查询总数

    //xml
    int update(URL url);

    @Delete("delete from url where id=#{id}")
    int delete(Integer id);

    @Insert("insert into url (url,name,folderId,userId,createTime) values (#{url},#{name},#{folderId},#{userId},#{createTime})")
    int addUrl(URL url);


//    @Query("select new URL(url.id,url.name,url.url,url.createTime,folder.id,folder.name)  from URL url join url.user user join url.folder folder where user.id=:userId and folder.id=:folderId")
//    Page<URL> findByFolder(@Param("userId") Integer userId,@Param("folderId") Integer folderId,Pageable pageable);

//    @Query("select new URL(url.id,url.name,url.url,url.createTime,folder.id,folder.name)  from URL url join url.user user join url.folder folder where user.id=:userId and folder.name like :keywords")
//    Page<URL> findByFolder_Name(@Param("userId") Integer userId,@Param("keywords") String keywords,Pageable pageable);

//    @Modifying
//    @Query(value = "update url set name=:name,url=:url,folder_id=:pid where id=:id and user_id=:userId",nativeQuery = true)
//    void update(@Param("name") String name,@Param("url") String url,@Param("pid") Integer pid,@Param("id") Integer urlId,@Param("userId") Integer userId);


//    @Query("select new URL(url.id,url.name,url.url,url.createTime,folder.id,folder.name) from URL url join url.user user " +
//            "join url.folder folder where user.id=:userId and url.name like :urlName")
//    Page<URL> findAllByUser_IdAndNameLike(@Param("userId") Integer userId, @Param("urlName") String urlName, Pageable pageable);

//    @Query("select new URL(url.id,url.name,url.url,url.createTime,folder.id,folder.name) from URL url join url.user user " +
//            "join url.folder folder where user.id=:userId and (url.name like :keywords or folder.name like :keywords)")
//    Page<URL> findAllByUser_IdAndNameLikeAndFolder_NameLike(@Param("userId") Integer userId, @Param("keywords") String keywords, Pageable pageable);


//    @Modifying
//    @Query(value = "delete from url where folder_id=:folderId",nativeQuery = true)
//    public void deleteByFolder_Id(@Param("folderId") Integer folderId);


}
