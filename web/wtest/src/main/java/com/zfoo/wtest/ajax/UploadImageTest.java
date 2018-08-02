package com.zfoo.wtest.ajax;

import com.zfoo.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("uploadImage")
    public void uploadImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream inputStream = request.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream("uploadImage.png");


        IOUtils.copy(inputStream, fileOutputStream);

        IOUtils.closeIO(inputStream, fileOutputStream);
    }
}
