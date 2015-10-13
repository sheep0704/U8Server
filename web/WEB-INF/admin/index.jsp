<%--
  Created by IntelliJ IDEA.
  User: xiaohei
  Date: 2015/8/22
  Time: 10:07
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
  <title>U8Server后台管理系统|U8SDK</title>

  <link rel="stylesheet" type="text/css" href="/js/plugins/easyui/themes/default/easyui.css">
  <link rel="stylesheet" type="text/css" href="/js/plugins/easyui/themes/icon.css">
  <link rel="stylesheet" type="text/css" href="/js/plugins/easyui/themes/color.css">
  <link rel="stylesheet" type="text/css" href="/css/u8server.css">

  <script type="text/javascript" src="/js/plugins/easyui/jquery.min.js"></script>
  <script type="text/javascript" src="/js/plugins/easyui/jquery.easyui.min.js"></script>
  <script type="text/javascript" src="/js/plugins/easyui/locale/easyui-lang-zh_CN.js"></script>

  <script type="text/javascript">

    /**
     * 创建新选项卡
     * @param tabId    选项卡id
     * @param title    选项卡标题
     * @param url      选项卡远程调用路径
     */
    function addTab(tabId,title,url){
      //如果当前id的tab不存在则创建一个tab
      if($("#"+tabId).html()==null){
        var name = 'iframe_'+tabId;
        $('#centerTab').tabs('add',{
          title: title,
          closable:true,
          cache : false,
          //注：使用iframe即可防止同一个页面出现js和css冲突的问题
          content : '<iframe name="'+name+'"id="'+tabId+'"src="'+url+'" width="100%" height="100%" frameborder="0" scrolling="auto">'+'</iframe>'
        });
      }
    }

  </script>

</head>
<body class="easyui-layout">
  <!-- 正上方panel -->
  <div region="north" style="height:102px" href="/header.jsp">
  </div>
  <!-- 正左边panel -->
  <div region="west" title="菜单栏" split="true" style="width:280px;padding1:1px;overflow:hidden;">
    <div class="easyui-accordion" fit="true" border="false">
      <!-- selected -->

      <div title="渠道管理" selected="true">
        <ul>
          <li><a href="javascript:addTab('tabId_channels','渠道管理','/admin/channels/channelManage');">渠道管理</a></li>
          <li><a href="javascript:addTab('tabId_channelMasters','渠道商管理','/admin/channelMaster/showChannelMasters');">渠道商管理</a></li>
        </ul>
      </div>

      <div title="游戏管理">
        <ul>
          <li><a href="javascript:addTab('tabId_games','游戏管理','/admin/games/showGames');">游戏管理</a></li>
        </ul>
      </div>

      <div title="用户管理">
        <ul>
          <li><a href="javascript:addTab('tabId_users','用户查询','/admin/users/showUsers');">用户查询</a></li>
          <li><a href="javascript:addTab('tabId_usersAnalytics','用户统计','/admin/users/showUserAnalytics');">用户统计</a></li>
        </ul>
      </div>

      <div title="订单管理">
        <ul>
          <li><a href="javascript:addTab('tabId_orders','订单查询','/admin/orders/showOrders');">订单查询</a></li>
          <li><a href="javascript:addTab('tabId_orderAnalytics','订单统计','/admin/orders/showOrderAnalytics');">订单统计</a></li>
        </ul>
      </div>

    </div>
  </div>
  <!-- 正中间panel -->
  <div region="center" title="功能区" >
    <div class="easyui-tabs" id="centerTab" fit="true" border="false">
      <div title="欢迎页" style="padding:20px;overflow:hidden;">
        <div style="margin-top:20px;">
          <h3>你好，欢迎来到【U8Server后台管理系统】</h3>
        </div>
      </div>
    </div>
  </div>
  <!-- 正下方panel -->
  <div region="south" style="height:50px;padding-top: 7px" align="center">
    <label>
      U8SDK——让手游SDK接入更简单<br/>
      官方网站：<a href = "http://www.u8sdk.com">http://www.u8sdk.com</a>
    </label>
  </div>
</body>
</html>
