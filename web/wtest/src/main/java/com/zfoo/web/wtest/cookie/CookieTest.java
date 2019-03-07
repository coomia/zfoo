package com.zfoo.web.wtest.cookie;

import com.zfoo.util.TimeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Cookie 是存储在客户端计算机上的文本文件，并保留了各种跟踪信息，步骤如下：
 * 1.服务器脚本向浏览器发送一组 Cookie。例如：姓名、年龄或识别号码等。
 * 2.浏览器将这些信息存储在本地计算机上，以备将来使用。
 * 3.当下一次浏览器向 Web 服务器发送任何请求时，浏览器会把这些 Cookie 信息发送到服务器，服务器将使用这些信息来识别用户。Cookie 通常设置在 HTTP 头信息中。
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-08-22 14:39
 */
@Controller
@RequestMapping("")
public class CookieTest {

    // 1.可在同一应用服务器内共享方法：设置cookie.setPath("/");
    // 本机tomcat/webapp下面有两个应用：webapp_a和webapp_b，
    // 1）原来在webapp_a下面设置的cookie，在webapp_b下面获取不到，path默认是产生cookie的应用的路径。
    // 2）若在webapp_a下面设置cookie的时候，增加一条cookie.setPath("/");或者cookie.setPath("/webapp_b/");就可以在webapp_b下面获取到cas设置的cookie了。
    // 3）此处的参数，是相对于应用服务器存放应用的文件夹的根目录而言的(比如tomcat下面的webapp)，因此cookie.setPath("/");之后，
    // 可以在webapp文件夹下的所有应用共享cookie，而cookie.setPath("/webapp_b/");是指cas应用设置的cookie只能在webapp_b应用下的获得，即便是产生这个cookie的webapp_a应用也不可以。
    // 4）设置cookie.setPath("/webapp_b/jsp")或者cookie.setPath("/webapp_b/jsp/")的时候，只有在webapp_b/jsp下面可以获得cookie，在webapp_b下面但是在jsp文件夹外的都不能获得cookie。

    // 2.跨域共享cookie的方法：设置cookie.setDomain("");
    //A机所在的域：hom,A有应用webapp_a
    //B机所在的域：jszx.com，B有应用webapp_b

    // 测试地址:localhost:8080/set-cookie-test
    @RequestMapping("set-cookie-test")
    public String setCookieTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 创建cookie记录当前时间
        Cookie cookie = new Cookie("lastTime", System.currentTimeMillis() + "");
        // 设置一下cookie的保存路径：工程名+配置网址路径，读取cookie是按请求的地址寻找cookie的
        // 如果你配置请求没有一级目录，这样全网站所有的网址请求都能找到你这个cookie
        cookie.setPath("/");
        // cookie.setPath("/sh-web-servlet/servlet");
        // 设置cookie存活时间：负值表示浏览器关闭cookie消失；正值表示，存活的时间，单位为秒；0表示删除cookie。
        cookie.setMaxAge(60 * 5);
        response.addCookie(cookie);
        return "cookie/set-cookie-test";
    }

    // 测试地址:localhost:8080/get-cookie-test
    @RequestMapping("get-cookie-test")
    public String getCookieTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 读取cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            // 遍历数组
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("lastTime")) {
                    // 取出cookie的值
                    String value = cookie.getValue();
                    String lastTime = TimeUtils.timeToString(Long.parseLong(value));
                    request.setAttribute("lastTime", lastTime);
                }
            }
        }
        return "cookie/get-cookie-test";
    }

}
