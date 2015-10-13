<%--
  Created by IntelliJ IDEA.
  User: xiaohei
  Date: 2015/8/22
  Time: 10:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;

%>
<base href="<%=basePath%>">
<html>
<head>
  <title></title>
  <link rel="stylesheet" type="text/css" href="/css/u8server.css">

</head>
<body>

  <div class="u8_header">
      <div class="u8_logo">

      </div>
      <div class="u8_title">
        U8Server后台管理系统
      </div>
      <div style="float:right;color: #ffffff;font-size: 12px;margin-top: 5px;margin-right: 10px;">
          <span>当前登录用户：</span><span>${session.adminName}</span>
      </div>
  </div>
</body>
</html>
