package com.zfoo.river.module.login.facade;

import com.zfoo.event.EventContext;
import com.zfoo.river.module.login.event.LoginEvent;
import com.zfoo.river.module.user.entity.UserEntity;
import com.zfoo.river.module.user.manager.IUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

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

    @RequestMapping("test")
    public String test() {
        String name = "jaysunxiao";
        String password = "admin";

        // 入库
        UserEntity entity = new UserEntity();
        entity.setId(new Random().nextLong());
        entity.setName(name);
        entity.setPassword(password);
        userManager.addUser(entity);

        // 抛出一个同步事件
        EventContext.getEventBusManager().syncSubmit(LoginEvent.valueOf(name ,password));
        return "test/test"; // test/test.jsp
    }
}
