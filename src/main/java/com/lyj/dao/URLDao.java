package com.lyj.dao;

import com.lyj.entity.URL;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 陆英杰
 * 2018/9/25 9:42
 */
@Repository
public interface URLDao{

    //xml---根据folderId查询url
    List<URL> getUrlsByUserIdAndFolderId(@Param("userId") Integer userId, @Param("folderId")int folderId,RowBounds rowBounds);//分页
    //xml---根据folderId查询总数
    int getUrlsCountByUserIdAndFolderId(@Param("userId")Integer userId, @Param("folderId")int folderId);//查询总数

    //xml---根据keywords查询url
    List<URL> getUrlsByUserIdAndKeywords(@Param("userId")Integer userId,@Param("keywords") String keywords,RowBounds rowBounds);//分页
    //xml---根据keywords查询总数
    int getUrlsCountByKeywords(@Param("userId")Integer userId,@Param("keywords") String keywords);//查询总数

    //xml---根据folderName查询url
    List<URL> queryByFolderName(@Param("userId")Integer userId,@Param("keywords") String keywords, RowBounds rowBounds);//分页
    //xml---根据folderName查询总数
    int getUrlsCountByFolderName(@Param("userId")Integer userId, @Param("keywords") String keywords);//查询总数

    //xml---根据urlName和folderName查询url
    List<URL> getUrlsByUrlNameAndFolderName(@Param("userId")Integer userId,@Param("urlName")String urlName,@Param("folderName") String folderName);
    //xml---根据urlName和folderName查询总数
    int getUrlsCountByUrlNameAndFolderName(@Param("userId")Integer userId,@Param("urlName") String urlName,@Param("folderName") String folderName);

    //xml---根据id更新url
    int update(URL url);

    @Delete("delete from url where id=#{id}")
    int delete(Integer id);
    @Insert("insert into url (url,name,folderId,userId,createTime) values (#{url},#{name},#{folderId},#{userId},#{createTime})")
    int addUrl(URL url);
    @Delete("delete from url where folderId=#{folderId}")
    void deleteByFolderId(Integer folderId);


}
