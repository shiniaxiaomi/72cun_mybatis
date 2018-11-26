package com.lyj.controller;

import com.lyj.entity.Result;
import com.lyj.entity.User;
import com.lyj.service.FolderService;
import com.lyj.service.URLService;
import com.lyj.service.UserService;
import com.lyj.service.UserSettingsService;
import com.lyj.util.ResultUtil;
import com.lyj.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by 陆英杰
 * 2018/9/17 0:41
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    URLService urlService;

    @Autowired
    FolderService folderService;

    @Autowired
    UserSettingsService userSettingsService;


    /**
     * 注册用户
     * @return 返回一个json对象
     */
    @ResponseBody
    @RequestMapping("/save")
    public Result save(User user){
        if(!StringUtil.isEmpty(user.getUserName()) && !StringUtil.isEmpty(user.getPassword())){
            if(!userService.isExists(user)){//判断是否已经存在该用户名
                User saveUser = userService.saveUser(user);
                if(saveUser!=null) {//保存成功
                    folderService.insertDefaultFolder(saveUser);//创建一个默认的文件夹
                    userSettingsService.add(saveUser);

                    return ResultUtil.success("注册成功");
                }
            }else{
                return ResultUtil.error("该用户名已存在");
            }
        }

        return ResultUtil.error("注册失败");
    }

    /**
     * 直接返回String类型,然后模板引擎会在返回的字符串后面加上.html后缀,
     * 然后再到templates文件夹中找到对应的模板进行渲染,染回返回给客户端
     *
     * @param model : 返回给模板引擎,在渲染的时候可以直接取到model中设置的值
     */
    @RequestMapping("/main")
    public String userMain(Model model,HttpSession session){
        model.addAttribute("user",session.getAttribute("user"));
        return "main";
    }



    /**
     * 退出登入
     *      使用redirect进行重定向 : 网页进行重定向,直接让客户端重新发起/请求
     */
    @RequestMapping("/exit")
    public String exit(HttpSession session){
        session.removeAttribute("user");//删除用户
        return "redirect:/";
    }



    /**
     * forward(转发):
     *      1.表示服务器内部进行的转发,但是浏览器上的网址却没有发生变化
     *      2.是服务器内部的重定向，服务器直接访问目标地址的 url网址，把里面的东西读取出来，但是客户端并不知道，
     *        因此用forward的话，客户端浏览器的网址是不会发生变化的。
     * redirect(重定向):
     *      1.是客户端的重定向，是完全的跳转。即服务器返回的一个url给客户端浏览器，
     *        然后客户端浏览器会重新发送一次请求，到新的url里面，因此浏览器中显示的url网址会发生变化。
     *      2.因为这种方式比forward多了一次网络请求，因此效率会低于forward。
     *
     *  mv.setViewName("forward:/index");//url: http://localhost:8087/index
     *  mv.setViewName("forward:index");//url: http://localhost:8087/user/index    当前路径下的url请求转变
     *  mv.setViewName("forward:/user/index");//url:mv.setViewName("forward:/user/index");
     */
    @ResponseBody
    @RequestMapping("/login")
    public Result login(User user, HttpSession session){

        User sessionUser = (User) session.getAttribute("user");
        if(sessionUser!=null){//说明用户已经存在
            return ResultUtil.success();
        }else{
            User loginedUser = userService.login(user);
            if(loginedUser!=null){
                session.setAttribute("user",loginedUser);
                return ResultUtil.success("登入成功");
            }else{
                return ResultUtil.error("用户名或密码错误");
            }
        }
    }


//    //在map中设置数据,并返回给前端,用来判断要跳转到那个快捷请求
//    public void mapPutString(Map<String,String> map, HttpSession session, String name, String str, boolean isTrans){
//        try {
//            if(StringUtil.isEmpty(str)){
//                map.put(name,"");
//            }else {
//                if(isTrans){
//                    map.put(name, URLDecoder.decode(str,"utf-8"));
//                }else {
//                    map.put(name,str);
//                }
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }







}
