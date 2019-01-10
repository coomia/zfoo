<%--
  Created by IntelliJ IDEA.
  User: sunqx
  Date: 2019/1/9
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
<html>
<head>
    <title>upload-file-test</title>
</head>
<body>
<form action="http://localhost:8080/fileUpload" method="post" enctype="multipart/form-data">
    <p>
        <span>文件上传：</span>
        <input id="file" name="file" type="file"/>
    </p>
    <button type="submit"><b>提交文件</b></button>
</form>
</body>
</html>
