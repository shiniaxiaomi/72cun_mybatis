package com.lyj.service;

import com.lyj.dao.URLDao;
import com.lyj.entity.Folder;
import com.lyj.entity.Result;
import com.lyj.entity.URL;
import com.lyj.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;


/**
 * Created by Yingjie.Lu on 2018/9/17.
 */

@Transactional //加上事务
@Service
public class URLService {


    @Autowired
    URLDao urlDao;


//    public void saveURL(URL url){
//        url.setCreateTime(new Date());
//        urlDao.save(url);
//    }

//    public List<URL> findAll(){
//
////        URL url=new URL();
////        url.setUser(user);
////        return urlDao.findAll(Example.of(url));
//        return null;
//
//    }

    /**
     * 根据folder的id查询url
     * @param userId
     * @param folder
     * @param pageRequest
     * @return
     */
    public Page<URL> findByFolder(Integer userId, Folder folder, PageRequest pageRequest){

        return urlDao.findByFolder(userId,folder.getId(),pageRequest);

    }


    @Transactional
    public Result update(Integer userId, URL url, Integer pid) {

        urlDao.update(url.getName(),url.getUrl(),pid,url.getId(),userId);
        return ResultUtil.success("更新成功!");
    }

    public Result delete(Integer id) {
        urlDao.deleteById(id);
        return ResultUtil.success("删除成功!");
    }

    /**
     * 查询url的标记查询url
     * @param userId
     * @param urlName
     * @param pageRequest
     * @return
     */
    public Page<URL> queryAll(Integer userId, String urlName, PageRequest pageRequest) {
        return urlDao.findAllByUser_IdAndNameLike(userId,"%"+urlName+"%",pageRequest);
    }

    public Result save(URL url){
        url.setCreateTime(new Timestamp(new Date().getTime()));
        URL save = urlDao.save(url);
        if(save!=null){
            return ResultUtil.success("保存成功!",save);
        }else {
            return ResultUtil.error("保存失败!",save);
        }
    }


    /**
     * 根据url的标记和folder的名称共同查询url
     * @param userId
     * @param keywords
     * @param pageRequest
     * @return
     */
    public Page<URL> queryUrlAndFolder(Integer userId, String keywords, PageRequest pageRequest) {
        return urlDao.findAllByUser_IdAndNameLikeAndFolder_NameLike(userId,"%"+keywords+"%",pageRequest);
    }

    /**
     * 根据folder的名字查询url
     * @param userId
     * @param keywords
     * @param pageRequest
     * @return
     */
    public Page<URL> findByFolder_Name(Integer userId, String keywords, PageRequest pageRequest) {
        return urlDao.findByFolder_Name(userId,"%"+keywords+"%",pageRequest);
    }
}
