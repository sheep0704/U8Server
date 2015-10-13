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
  <div id="games">

  </div>

  <div id="easyui_toolbar" region="north" border="false"
        style="border-bottom: 1px solid #ddd; height: 32px; padding: 2px 5px; background: #fafafa;">
    <div style="float: left;">
      <a class="easyui-linkbutton" plain="true" icon="icon-add" onclick="javascript:showAddDialog();">新增</a>
    </div>

    <div class="datagrid-btn-separator"></div>

    <div style="float: left;">
      <a class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="javascript:showEditDialog();">编辑</a>
    </div>

    <div class="datagrid-btn-separator"></div>

    <div style="float: left;">
      <a class="easyui-linkbutton" plain="true" icon="icon-filter" onclick="javascript:showDetailDialog();">详细信息</a>
    </div>

    <div class="datagrid-btn-separator"></div>

    <div style="float: left;">
      <a class="easyui-linkbutton" plain="true"
         icon="icon-remove" onclick="javascript:deleteGame();">删除</a>
    </div>

    <div id="tb" style="float: right;">
      <input id="search_box" class="easyui-searchbox" style="width: 250px"  data-options="searcher:doSearch,prompt:'请输入查询词',menu:'#search_menu'" />
      <div id="search_menu" style="width:120px">
        <div data-options="name:'game_name'">游戏名称</div>
        <div data-options="name:'app_id'">AppID</div>
      </div>
    </div>

  </div>

  <div id="dialog_add" class="easyui-dialog u8_form"
       closed="true" buttons="#dlg-buttons" style="height: 250px">
    <div class="ftitle">游戏信息</div>
    <form id="fm" method="post" novalidate>
      <input type="hidden" name="appID" />
      <div class="u8_form_row">
        <label >游戏名称：</label>
        <input type="text" class="easyui-textbox" name="name" maxlength="255" required="false" />
      </div>

      <div class="u8_form_row">
        <label >支付回调地址：</label>
        <input type="text" class="easyui-textbox" name="payCallback" maxlength="1024" novalidate />
      </div>

      <div class="u8_form_row">
        <label>支付回调(调试)：</label>
        <input type="text" class="easyui-textbox" name="payCallbackDebug" maxlength="1024" novalidate />
      </div>

    </form>
  </div>
  <div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveGame()" style="width:90px">保 存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog_add').dialog('close')" style="width:90px">取 消</a>
  </div>


  <div id="dialog_detail" class="easyui-dialog u8_form"
       closed="true" buttons="#dlg-buttons-edit" >
    <div class="ftitle">游戏详细信息</div>
    <form id="fm_edit" method="post" novalidate>

      <div class="u8_form_row">
        <label >游戏名称：</label>
        <input type="text" class="easyui-textbox" name="name" readonly="readonly" novalidate/>
      </div>

      <div class="u8_form_row">
        <label >AppID：</label>
        <input type="text" class="easyui-textbox" name="appID" readonly="readonly" novalidate/>
      </div>

      <div class="u8_form_row">
        <label >AppKey：</label>
        <input type="text" class="easyui-textbox" name="appkey" readonly="readonly" novalidate/>
      </div>

      <div class="u8_form_row">
        <label >PublicKey：</label>
        <input type="text" class="easyui-textbox" name="appRSAUPubKey" readonly="readonly" novalidate/>
      </div>

      <div class="u8_form_row">
        <label >PrivateKey：</label>
        <input type="text" class="easyui-textbox" name="appRSAPriKey" readonly="readonly" novalidate/>
      </div>

      <div class="u8_form_row">
        <label >支付回调地址：</label>
        <input type="text" class="easyui-textbox" name="payCallback" readonly="readonly" novalidate/>
      </div>

      <div class="u8_form_row">
        <label>支付回调(调试)：</label>
        <input type="text" class="easyui-textbox" name="payCallbackDebug" readonly="readonly" novalidate/>
      </div>

    </form>
  </div>
  <div id="dlg-buttons-edit">

    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:$('#dialog_detail').dialog('close')" style="width:90px">确 定</a>
  </div>

  <script type="text/javascript">

    var url;
    function showAddDialog(){
      $("#dialog_add").window({
        top:($(window).height() - 150) * 0.5,
        left:($(window).width() - 400) * 0.5
      });

      $("#dialog_add").dialog('open').dialog('setTitle', '添加游戏');

      $('#fm').form('clear');

      url = '/admin/games/saveGame';

    }

    function showEditDialog(){

      $("#dialog_add").window({
        top:($(window).height() - 300) * 0.5,
        left:($(window).width() - 400) * 0.5
      });


      var row = $('#games').datagrid('getSelected');
      if(row){

        $("#dialog_add").dialog('open').dialog('setTitle', '编辑游戏');
        $('#fm').form('load', row);
        url = '/admin/games/saveGame';

      }else{
        $.messager.show({
          title:'操作提示',
          msg:'请选择一条记录'
        })
      }
    }

    function showDetailDialog(){

      $("#dialog_detail").window({
        top:($(window).height() - 300) * 0.5,
        left:($(window).width() - 400) * 0.5
      });


      var row = $('#games').datagrid('getSelected');
      if(row){

        $("#dialog_detail").dialog('open').dialog('setTitle', '游戏信息');
        $('#fm_edit').form('load', row);

      }else{
        $.messager.show({
          title:'操作提示',
          msg:'请选择一条记录'
        })
      }
    }

    function deleteGame(){
      var row = $('#games').datagrid('getSelected');
      if(row){
        $.messager.confirm(
          '操作确认',
          '确定要删除该渠道商吗？(操作不可恢复)',
          function(r){
            if(r){
              $.post('/admin/games/removeGame', {currAppID:row.appID}, function(result){
                if (result.state == 1) {
                  $('#dialog_add').dialog('close');
                  $("#games").datagrid('reload');
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

    function saveGame(){

      $('#fm').form('submit', {
        url:url,
        onSubmit:function(){
          return $(this).form('validate');
        },
        success:function(result){
          var result = eval('('+result+')');

          if (result.state == 1) {
            $('#dialog_add').dialog('close');
            $("#games").datagrid('reload');
          }

          $.messager.show({
            title:'操作提示',
            msg:result.msg
          })
        }
      })

    }



    function doSearch(value, name){
      alert("value:"+value+";name:"+name);
    }

    $("#games").datagrid({
      height:430,
      url:'/admin/games/getAllGames',
      method:'POST',
      idField:'appID',
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
        {field:'appID', title:'AppID', width:40, sortable:true},
        {field:'name', title:'游戏名称', width:40, sortable:true},
        {field:'appkey', title:'AppKey', width:60, sortable:true},
        {field:'appRSAPubKey', title:'PayPublicKey', width:100, sortable:true},
        {field:'appRSAPriKey', title:'PayPrivateKey', width:100, sortable:true},
        {field:'payCallback', title:'支付回调地址', width:80, sortable:true},
        {field:'payCallbackDebug', title:'支付回调调试地址', width:80, sortable:true}
      ]],
      toolbar:'#easyui_toolbar'
    });

  </script>

</body>
</html>
