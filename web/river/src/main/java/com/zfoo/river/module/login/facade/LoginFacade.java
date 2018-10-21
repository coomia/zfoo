package com.zfoo.river.module.login.facade;

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
        UserEntity entity = new UserEntity();
        entity.setId(new Random().nextLong());
        entity.setName("jaysunxiao");
        entity.setPassword("admin");
        userManager.addUser(entity);
        return "test/test"; // test/test.jsp
    }
}
