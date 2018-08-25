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

    // 测试地址:localhost:8080/ajax-test
    @RequestMapping("ajax-test")
    public String ajaxTest() {
        return "ajax/ajax-test";
    }


}
