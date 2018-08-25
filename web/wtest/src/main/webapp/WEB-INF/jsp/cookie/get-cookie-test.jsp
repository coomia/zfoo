<%--
  User: jaysunxiao
  Date: 2018/8/22
  Time: 14:55
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>


<%
    String lastTime = (String) request.getAttribute("lastTime");
%>

<html>
<head>
    <title>get-cookie-test</title>
</head>
<body>

<p><%=lastTime%>

</p>
</body>
</html>
