package com.zfoo.wtest.ajax;

import com.zfoo.util.AssertionUtils;
import com.zfoo.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-08-02 18:18
 */
@Controller
@RequestMapping("")
public class UploadImageTest {


    // 处理一个png图片的上传，并保存在服务器中
    @RequestMapping("uploadImage")
    public void uploadImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream inputStream = request.getInputStream();

        String header = "data:image/png;base64,";

        byte[] imageBytes = IOUtils.toByteArray(inputStream);
        String imageStr = new String(imageBytes);

        AssertionUtils.isTrue(imageStr.startsWith(header));

        // 去掉头部header
        String imageStrData = imageStr.substring(header.length());
        // 解码
        byte[] bytes = new BASE64Decoder().decodeBuffer(imageStrData);

        FileOutputStream fileOutputStream = new FileOutputStream("uploadImage.png");
        fileOutputStream.write(bytes);

        IOUtils.closeIO(inputStream, fileOutputStream);
    }
}
