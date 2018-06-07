package com.zfoo.news.login.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-06-06 21:03
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        System.out.println(this.getClass().getSimpleName() + "-->preHandle(), 在业务处理器处理请求之前被调用" );


        HttpSession session = request.getSession();
        String contextPath = session.getServletContext().getContextPath();
        String[] noNeedAuthPage = new String[]{"home", "checkLogin", "register", "loginAjax", "login", "product", "category", "search"};

        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri, contextPath);
//        System.out.println(uri);
//        if (uri.startsWith("/fore")) {
//            String method = StringUtils.substringAfterLast(uri, "/fore");
//            if (!Arrays.asList(noNeedAuthPage).contains(method)) {
//                UserEntity user = (UserEntity) session.getAttribute("user");
//                if (null == user) {
//                    response.sendRedirect("loginPage");
//                    return false;
//                }
//            }
//        }

        return true;

    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println(this.getClass().getSimpleName() + "-->postHandle(), 生成视图之前执行的动作被调用" );
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     * <p>
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println(this.getClass().getSimpleName() + "-->afterCompletion(), 在访问视图之后被调用");
    }


}
