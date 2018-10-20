package com.zfoo.river.module.login.facade;

import com.zfoo.river.module.user.manager.IUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("login")
    public String login() {


        return "login/login";
    }
}
