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
<div id="orders">

</div>

<div id="easyui_toolbar" region="north" border="false"
     style="border-bottom: 1px solid #ddd; height: 32px; padding: 2px 5px; background: #fafafa;">
  <%--<div style="float: left;">--%>
  <%--<a class="easyui-linkbutton" plain="true" icon="icon-filter" onclick="javascript:showAddDialog();">详细信息</a>--%>
  <%--</div>--%>

  <%--<div class="datagrid-btn-separator"></div>--%>

  <div style="float: left;">
    <a class="easyui-linkbutton" plain="true"
       icon="icon-remove" onclick="javascript:deleteOrder();">删除</a>
  </div>

  <div id="tb" style="float: right;">
    <input id="search_box" class="easyui-searchbox" style="width: 250px"  data-options="searcher:doSearch,prompt:'请输入查询词',menu:'#search_menu'" />
    <div id="search_menu" style="width:120px">
      <div data-options="name:'order_id'">订单号</div>
      <div data-options="name:'order_channelID'">渠道订单号</div>
      <div data-options="name:'order_username'">用户名</div>
      <div data-options="name:'order_channel'">渠道名称</div>
      <div data-options="name:'order_game'">所属游戏</div>
    </div>
  </div>

</div>


<script type="text/javascript">



  function deleteOrder(){
    var row = $('#orders').datagrid('getSelected');
    if(row){
      $.messager.confirm(
              '操作确认',
              '确定要删除该用户吗？(操作不可恢复)',
              function(r){
                if(r){
                  $.post('/admin/orders/removeOrder', {currOrderID:row.orderID}, function(result){
                    if (result.state == 1) {
                      $("#orders").datagrid('reload');
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

  $("#orders").datagrid({
    height:430,
    url:'/admin/orders/getAllOrders',
    method:'POST',
    idField:'orderID',
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
      {field:'orderID', title:'订单号', width:80, sortable:true},
      {field:'username', title:'用户名', width:40, sortable:true},
      {field:'money', title:'金额(分,RMB)', width:40, sortable:true},
      {field:'state', title:'状态', width:15, sortable:true},
      {field:'channelOrderID', title:'渠道订单号', width:60, sortable:true},
      {field:'channelID', title:'渠道号', width:30, sortable:true},
      {field:'channelName', title:'渠道名称', width:60, sortable:true},
      {field:'appName', title:'所属游戏', width:70, sortable:true},
      {field:'createdTime', title:'下单时间', width:70, sortable:true}
    ]],
    toolbar:'#easyui_toolbar'
  });

</script>

</body>
</html>
