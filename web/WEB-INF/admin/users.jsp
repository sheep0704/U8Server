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

</head>
<body>
<div id="users">

</div>

<div id="easyui_toolbar" region="north" border="false"
     style="border-bottom: 1px solid #ddd; height: 32px; padding: 2px 5px; background: #fafafa;">
  <%--<div style="float: left;">--%>
    <%--<a class="easyui-linkbutton" plain="true" icon="icon-filter" onclick="javascript:showAddDialog();">详细信息</a>--%>
  <%--</div>--%>

  <%--<div class="datagrid-btn-separator"></div>--%>

  <div style="float: left;">
    <a class="easyui-linkbutton" plain="true"
       icon="icon-remove" onclick="javascript:deleteUser();">删除</a>
  </div>

  <div id="tb" style="float: right;">
    <input id="search_box" class="easyui-searchbox" style="width: 250px"  data-options="searcher:doSearch,prompt:'请输入查询词',menu:'#search_menu'" />
    <div id="search_menu" style="width:120px">
      <div data-options="name:'user_name'">用户名</div>
      <div data-options="name:'user_id'">渠道userID</div>
      <div data-options="name:'game'">所属游戏</div>
      <div data-options="name:'channel'">所属渠道</div>
    </div>
  </div>

</div>


<script type="text/javascript">



  function deleteUser(){
    var row = $('#users').datagrid('getSelected');
    if(row){
      $.messager.confirm(
              '操作确认',
              '确定要删除该用户吗？(操作不可恢复)',
              function(r){
                if(r){
                  $.post('/admin/users/removeUser', {currUserID:row.id}, function(result){
                    if (result.state == 1) {
                      $("#users").datagrid('reload');
                    }

                    $.messager.show({
                      title:'操作提示',
                      msg:result.msg
                    })

                  }, 'json');
                }
              }
      );
    }else{
      $.messager.show({
        title:'操作提示',
        msg:'请选择一条记录'
      })
    }
  }



  function doSearch(value, name){
    alert("value:"+value+";name:"+name);
  }

  $("#users").datagrid({
    height:430,
    url:'/admin/users/getAllUsers',
    method:'POST',
    idField:'id',
    striped:true,
    fitColumns:true,
    singleSelect:true,
    rownumbers:true,
    pagination:true,
    nowrap:true,
    loadMsg:'数据加载中...',
    pageSize:10,
    pageList:[10,20,50,100],
    showFooter:true,
    columns:[[
      {field:'name', title:'名称', width:80, sortable:true},
      {field:'appName', title:'游戏', width:40, sortable:true},
      {field:'channelName', title:'所属渠道', width:40, sortable:true},
      {field:'channelUserID', title:'渠道userID', width:60, sortable:true},
      {field:'channelUserName', title:'渠道用户名', width:50, sortable:true},
      {field:'channelUserNick', title:'用户昵称', width:50, sortable:true},
      {field:'lastLoginTime', title:'最后登录时间', width:70, sortable:true},
      {field:'createTime', title:'注册时间', width:70, sortable:true}
    ]],
    toolbar:'#easyui_toolbar'
  });

</script>

</body>
</html>
