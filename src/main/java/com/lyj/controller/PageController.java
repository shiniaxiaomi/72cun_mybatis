package com.lyj.controller;

import com.lyj.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by Yingjie.Lu on 2018/10/7.
 */

@Controller
public class PageController {


    //返回的是登入页面
    @RequestMapping("/")
    public ModelAndView toLogin(HttpSession session,ModelAndView mv){


//        ModelAndView mv=new ModelAndView("login");
        mv.setViewName("login");

        User user = (User) session.getAttribute("user");
        if(user!=null){//说明用户已经存在
            mv.addObject("user",user);
        }

        //从session中获取值并放入mv
        sessionToMV(session,mv,"url");
        sessionToMV(session,mv,"title");
        sessionToMV(session,mv,"type");

        return mv;
    }

    @RequestMapping("/searchUrl")
    public ModelAndView searchUrl(ModelAndView mv){
        mv.setViewName("searchUrl");
        return mv;
    }

    @RequestMapping("/urlManager")
    public ModelAndView urlManager(ModelAndView mv){
        mv.setViewName("urlManager");
        return mv;
    }


    //从session中获取值并放入mv中,如果为null,则变成""
    public void sessionToMV(HttpSession session,ModelAndView mv,String name){
        Object obj=session.getAttribute(name);
        if(obj==null){//如果为空,则不添加
//            mv.addObject(name,"");
        }else{
            mv.addObject(name,obj);
        }
    }


}
