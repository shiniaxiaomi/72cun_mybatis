package com.lyj.config.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 陆英杰
 * 2018/12/2 3:05
 */
public class ModelAndViewArgumentResolver implements HandlerMethodArgumentResolver {

    //静态常量
    public static final String baseCssAndJs=
            "<link href='https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css' rel='stylesheet'>" +
            "<link rel='stylesheet' href='https://unpkg.com/element-ui@2.4.8/lib/theme-chalk/index.css'>" +
            "<script src='https://cdn.jsdelivr.net/npm/vue@2.5.17/dist/vue.js'></script>" +
            "<script src='https://unpkg.com/element-ui@2.4.8/lib/index.js'></script>" +
            "<script src='https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js'></script>" +
            "<script src='/js/store.min.js'></script>" +
            "<script src='/js/commonElement_1.0.0.js'></script>";//可能会有所变动,所以需要修改版本

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> aClass = methodParameter.getParameterType();
        return aClass== ModelAndView.class;//如果是ModelAndView,则继续往下执行
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

//        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
//        HttpServletRequest response = nativeWebRequest.getNativeResponse(HttpServletRequest.class);

        ModelAndView mv=new ModelAndView();
        mv.addObject("baseCssAndJs",baseCssAndJs);

        return mv;
    }
}
