package com.lyj.util;

import com.lyj.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;


/**
 * Created by 陆英杰
 * 2018/9/27 0:50
 */

@ControllerAdvice
public class ExceptionHandle {

//    private final static Logger logger= LoggerFactory.getLogger(ExceptionHandle.class);

    /**
     * 统一的Exception异常处理,可以直接将异常返回给客户端,方便直接观察异常信息
     */
    @ExceptionHandler(value = Exception.class)
    public Result handle(Exception e){
//        logger.error("Exception异常:"+ Arrays.asList(e.getStackTrace()));
        e.printStackTrace();
        return ResultUtil.error("Exception异常",e);
    }
}
