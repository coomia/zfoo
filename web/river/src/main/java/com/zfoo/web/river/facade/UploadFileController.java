package com.zfoo.web.river.facade;

import com.zfoo.util.AssertionUtils;
import com.zfoo.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;
import java.util.Date;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-08-02 18:18
 */
@Controller
public class UploadFileController {

    // 处理一个png图片的上传，并保存在服务器中
    @RequestMapping("/uploadImage")
    public void uploadImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream inputStream = request.getInputStream();

        String header = "data:image/png;base64,";

        byte[] imageBytes = IOUtils.toByteArray(inputStream);
        String imageStr = new String(imageBytes);

        AssertionUtils.isTrue(imageStr.startsWith(header));

        // 去掉头部header
        String imageStrData = imageStr.substring(header.length());
        // 解码
        byte[] bytes = Base64.getDecoder().decode(imageStrData);

        FileOutputStream fileOutputStream = new FileOutputStream("uploadImage.png");
        fileOutputStream.write(bytes);

        IOUtils.closeIO(inputStream, fileOutputStream);
    }


    // 测试地址:localhost:8080/file/upload-file-test.html
    // 通过流的方式上传文件
    // @RequestParam("file") 将name=file控件得到的文件封装成 MultipartFile 对象
    @ResponseBody
    @RequestMapping("/fileUpload")
    public String fileUploadByZeroCopy(@RequestParam("file") MultipartFile file) throws IOException {
        String path="D:/"+new Date().getTime()+file.getOriginalFilename();
        File newFile=new File(path);
        //通过MultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(newFile);
        return "success";
    }

    // 同fileUploadByZeroCopy，效率会差一点，因为把bytes读到了内存
    // @ResponseBody
    // @RequestMapping("fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            outputStream = new FileOutputStream("D:/" + new Date().getTime() + file.getOriginalFilename());
            //获取输入流 MultipartFile 中可以直接得到文件的流
            inputStream = file.getInputStream();
            outputStream.write(IOUtils.toByteArray(inputStream));
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeIO(outputStream, inputStream);
        }
        return "success";
    }

}
