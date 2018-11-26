package com.lyj.config;

import com.lyj.config.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Created by Yingjie.Lu on 2018/9/17.
 */


@Configuration
public class WebConfig implements WebMvcConfigurer {


    /**
     * 添加自定义的拦截器(用户登入验证)
     *
     * excludePathPatterns: 是根据实际请求的url路径进行排除的 (就是http://localhost:8087/之后的url路径)
     *      /index/** : 表示以/index/开头的url路径全部都会被拦截
     * addPathPatterns: 是根据实际请求的url路径进行排除的 (就是http://localhost:8087/之后的url路径)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        //拦截的作用是判断是否已经登入(即判断session中是否已经有user对象)

        //这个拦截的要看的就是请求的url是否包含指定的内容
        registry.addInterceptor(new LoginCheckInterceptor()).addPathPatterns("/**")
//                .excludePathPatterns("/index/**","/res/**","/scripts/**")//排除url请求是以index,res,scripts开头的静态文件
                .excludePathPatterns("/js/**","/icon/**")//排除url请求是以js,icon开头的静态文件
                .excludePathPatterns("/user/login","/user/save","/index","/login","/","/fast/saveAndLogin")//排除登入和注册请求,还有快捷操作
                ;
    }

    /**
     * addViewControllers可以方便的实现一个请求直接映射成视图，而无需书写controller
     * registry.addViewController("请求路径").setViewName("请求页面文件路径")
     *
     * 此方法可以很方便的实现一个请求到视图的映射，而无需书写controller
     *
     * 这个ViewControllers的优先级比Interceptors低,所以如果Interceptors没有配置放行,基本上会被拦截掉
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/login").setViewName("main");//将/login请求映射到template下的main.html页面

    }

    //配置视图解析器
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

    }

    //配置静态资源路径
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }


}
