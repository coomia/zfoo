package com.zfoo.news.login.control;

import com.zfoo.news.user.manager.IUserManager;
import com.zfoo.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.util.List;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-06-06 20:14
 */
@Controller
@RequestMapping("")
public class LoginControl {

    @Autowired
    IUserManager userManager;

    @RequestMapping("login")
    public String home(Model model) {


        return "login/login";
    }

}
