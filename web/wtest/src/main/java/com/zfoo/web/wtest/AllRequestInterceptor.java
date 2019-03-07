package com.zfoo.web.wtest;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-06-07 09:45
 */
public class AllRequestInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println(this.getClass().getSimpleName() + "-->preHandle(), 在业务处理器处理请求之前被调用");
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println(this.getClass().getSimpleName() + "-->postHandle(), 生成视图之前执行的动作被调用");
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println(this.getClass().getSimpleName() + "-->afterCompletion(), 在访问视图之后被调用");
        System.out.println("***************************************************************************************************************************");
        System.out.println("sdf");
    }


}
