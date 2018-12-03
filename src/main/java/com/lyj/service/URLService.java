package com.lyj.service;

import com.lyj.dao.URLDao;
import com.lyj.entity.URL;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;


/**
 * Created by Yingjie.Lu on 2018/9/17.
 */


@Service
public class URLService {


    @Autowired
    URLDao urlDao;

    @Autowired
    private DataSource dataSource;


    public List<URL> getUrlsByUserIdAndFolderId(Integer userId, int folderId,Integer pageIndex,Integer pageSize) {
        RowBounds rowBounds=new RowBounds((pageIndex-1)*pageSize,pageSize);//分页用

        return urlDao.getUrlsByUserIdAndFolderId(userId,folderId,rowBounds);
    }

    public int getUrlsCountByUserIdAndFolderId(Integer userId, int folderId) {
        return urlDao.getUrlsCountByUserIdAndFolderId(userId,folderId);
    }

    public List<URL> queryByUrlName(Integer userId, String keywords,Integer pageIndex,Integer pageSize) {
        RowBounds rowBounds=new RowBounds((pageIndex-1)*pageSize,pageSize);//分页用
        return urlDao.getUrlsByUserIdAndKeywords(userId,keywords,rowBounds);
    }

    public int getUrlsCountByKeywords(Integer userId, String keywords) {
        return urlDao.getUrlsCountByKeywords(userId,keywords);
    }

    public List<URL> queryByFolderName(Integer userId, String keywords, Integer pageIndex, Integer pageSize) {
        RowBounds rowBounds=new RowBounds((pageIndex-1)*pageSize,pageSize);//分页用
        return urlDao.queryByFolderName(userId,keywords,rowBounds);
    }

    public int getUrlsCountByFolderName(Integer userId, String keywords) {
        return urlDao.getUrlsCountByFolderName(userId,keywords);
    }


    public int update(URL url) {
        return urlDao.update(url);
    }

    public int delete(Integer id) {
        return urlDao.delete(id);
    }

    public int addUrl(URL url) {

        return urlDao.addUrl(url);
    }

    //综合查询--个数
    public int getUrlsCountByUrlNameAndFolderName(Integer userId, String urlName, String folderName) {
        return urlDao.getUrlsCountByUrlNameAndFolderName(userId,urlName,folderName);
    }
    //综合查询-url
    public List<URL> getUrlsByUrlNameAndFolderName(Integer userId, String urlName, String folderName) {
        return urlDao.getUrlsByUrlNameAndFolderName(userId,urlName,folderName);
    }
}
