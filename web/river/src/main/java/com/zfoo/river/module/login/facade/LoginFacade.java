package com.zfoo.river.module.login.facade;

import com.zfoo.event.EventContext;
import com.zfoo.river.module.login.event.LoginEvent;
import com.zfoo.river.module.user.entity.UserEntity;
import com.zfoo.river.module.user.manager.IUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.util.UUID;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018/10/20
 */
@Controller
@RequestMapping("")
public class LoginFacade {

    @Autowired
    private IUserManager userManager;

    // http://localhost:8080/test
    @RequestMapping("test")
    public String testEvent() {
        String name = "jaysunxiao";
        String password = "admin";

        // 入库
        UserEntity entity = new UserEntity();
        entity.setId(new Random().nextLong());
        entity.setName(name);
        entity.setPassword(password);
        userManager.addUser(entity);

        // 抛出一个同步事件
        EventContext.getEventBusManager().syncSubmit(LoginEvent.valueOf(name, password));
        return "test/test"; // test/test.jsp
    }


    // http://localhost:8080/test/aaa?param=bbb
    @RequestMapping(value = "test/{userName}", method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE + ";charset=utf-8")
    @ResponseBody
    public String testParam(@PathVariable String userName, @RequestParam String param) {
        System.out.println(userName);
        System.out.println(param);

        return "Hello World";
    }


    // SSO英文全称Single Sign On，单点登录。SSO是在多个应用系统中，用户只需要登录一次就可以访问所有相互信任的应用系统。
    // http://localhost:8080/test/session
    @RequestMapping(value = "test/session", method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE + ";charset=utf-8")
    @ResponseBody
    public void testSession(HttpServletRequest request, HttpServletResponse response) {
        // 不能用request.setAttribute()，HttpServletRequest不能保证同一个Session所有的request都能取到myAttribute
        HttpSession session = request.getSession();
        if (session.getAttribute("myAttribute") == null) {
            System.out.println("myAttribute = null");
            session.setAttribute("myAttribute", UUID.randomUUID().toString());
        } else {
            System.out.println("myAttribute = " + session.getAttribute("myAttribute"));
        }
    }
}
