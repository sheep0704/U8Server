<%--
  Created by IntelliJ IDEA.
  User: xiaohei
  Date: 2015/8/22
  Time: 14:01
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

  <link rel="stylesheet" type="text/css" href="/js/plugins/easyui/themes/default/easyui.css">
  <link rel="stylesheet" type="text/css" href="/js/plugins/easyui/themes/icon.css">
  <link rel="stylesheet" type="text/css" href="/js/plugins/easyui/themes/color.css">
  <link rel="stylesheet" type="text/css" href="/css/u8server.css">

  <script type="text/javascript" src="/js/plugins/easyui/jquery.min.js"></script>
  <script type="text/javascript" src="/js/plugins/easyui/jquery.easyui.min.js"></script>
  <script type="text/javascript" src="/js/plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
  <script type="text/javascript" src="/js/jquery.md5.js"></script>

</head>
<body>
<div style="height: 400px;display: block;">

</div>

<div id="dialog_add" class="easyui-dialog" title="用户登录"
     closed="false" buttons="#dlg-buttons" style="height: 180px;margin: 0 auto;">
  <form id="fm" method="post" novalidate>
    <div class="u8_form_row" style="margin-top: 15px">
      <label style="width: 50px">用户名：</label>
      <input id = "username" type="text" class="easyui-textbox" name="username" maxlength="255"  />
    </div>

    <div class="u8_form_row" >
      <label style="width: 50px">密　码：</label>
      <input id="pwd" type="password" class="easyui-textbox" name="password" maxlength="255" />
    </div>

  </form>
</div>
<div id="dlg-buttons">
  <a href="javascript:void(0)" class="easyui-linkbutton c6" onclick="login();" style="width:90px">登　录</a>
</div>

<script type="text/javascript">

  function login(){

    var username = $("#username").val();
    var pwd = $("#pwd").val();
    pwd = $.md5(pwd);

    $.post('/admin/doLogin', {username:username, password:pwd}, function(result){
      if (result.state == 1) {

        location.href="/admin/index"

      }else{
        $.messager.show({
          title:'操作提示',
          msg:result.msg
        })
      }



    }, 'json');



  }


</script>

</body>
</html>
