package com.lyj.service;

import com.lyj.dao.URLDao;
import com.lyj.entity.URL;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by Yingjie.Lu on 2018/9/17.
 */

//@Transactional //加上事务
@Service
public class URLService {


    @Autowired
    URLDao urlDao;


//    /**
//     * 根据folder的id查询url
//     * @param userId
//     * @param folder
//     * @param pageRequest
//     * @return
//     */
//    public Page<URL> findByFolder(Integer userId, Folder folder, PageRequest pageRequest){
//
//        return urlDao.findByFolder(userId,folder.getId(),pageRequest);
//
//    }
//
//
////    @Transactional
//    public Result update(Integer userId, URL url, Integer pid) {
//
//        urlDao.update(url.getName(),url.getUrl(),pid,url.getId(),userId);
//        return ResultUtil.success("更新成功!");
//    }
//
//    public Result delete(Integer id) {
//        urlDao.deleteById(id);
//        return ResultUtil.success("删除成功!");
//    }
//
//    /**
//     * 查询url的标记查询url
//     * @param userId
//     * @param urlName
//     * @param pageRequest
//     * @return
//     */
//    public Page<URL> queryAll(Integer userId, String urlName, PageRequest pageRequest) {
//        return urlDao.findAllByUser_IdAndNameLike(userId,"%"+urlName+"%",pageRequest);
//    }
//
//    public Result save(URL url){
//        url.setCreateTime(new Timestamp(new Date().getTime()));
//        URL save = urlDao.save(url);
//        if(save!=null){
//            return ResultUtil.success("保存成功!",save);
//        }else {
//            return ResultUtil.error("保存失败!",save);
//        }
//    }
//
//
//    /**
//     * 根据url的标记和folder的名称共同查询url
//     * @param userId
//     * @param keywords
//     * @param pageRequest
//     * @return
//     */
//    public Page<URL> queryUrlAndFolder(Integer userId, String keywords, PageRequest pageRequest) {
//        return urlDao.findAllByUser_IdAndNameLikeAndFolder_NameLike(userId,"%"+keywords+"%",pageRequest);
//    }
//
//    /**
//     * 根据folder的名字查询url
//     * @param userId
//     * @param keywords
//     * @param pageRequest
//     * @return
//     */
//    public Page<URL> findByFolder_Name(Integer userId, String keywords, PageRequest pageRequest) {
//        return urlDao.findByFolder_Name(userId,"%"+keywords+"%",pageRequest);
//    }

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
