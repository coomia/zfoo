package com.zfoo.web.wtest.form;

import com.zfoo.util.FileUtils;
import com.zfoo.util.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * A simple wrapper for Apache Commons FileUploadUtils for allowing it to work with Netty and other IO servers.
 *
 * @author https://github.com/javadelight/delight-fileupload
 * @version 1.0
 * @since 2019/1/8
 */
public class FileUploadUtils {

    public static List<FileItem> parse(final byte[] data, final String contentType) {
        try {
            final FileItemFactory factory = new DiskFileItemFactory();
            final ServletFileUpload upload = new ServletFileUpload(factory);
            final HttpServletRequest request = new MockHttpServletRequest(data, contentType);
            final boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if ((!isMultipart)) {
                throw new Exception("Illegal request for uploading files. Multipart request expected.");
            }
            return upload.parseRequest(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * form表单的格式准守一定的规则，所以需要按照固定的格式去解析
     *
     * ----------------------------681395766232748508178371
     * Content-Disposition: form-data; name="test"; filename="testmail.xlsx"
     * Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
     *
     * body
     * ----------------------------681395766232748508178371--
     * @throws IOException IO异常
     */
    @Test
    public void test() throws IOException {
        byte[] bytes = FileUtils.readFileToByteArray(new File("D:\\excel\\target.xls"));
        List<FileItem> list = parse(bytes, "multipart/form-data; boundary=--------------------------681395766232748508178371");

        for (FileItem item : list) {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\excel\\" + new Date().getTime() + item.getName()));
            fileOutputStream.write(IOUtils.toByteArray(item.getInputStream()));
            fileOutputStream.close();
        }

    }

}
