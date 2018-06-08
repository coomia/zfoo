package com.zfoo.wtest.ajax;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-06-08 13:26
 */
@Controller
@RequestMapping("")
public class AjaxTest {

    @RequestMapping("login")
    public String home(Model model) {
        return "login/login";
    }


    public static void main(String[] args) {
        System.out.println("sdfsaf");
    }
}
