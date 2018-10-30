<%--
  Created by IntelliJ IDEA.
  User: wx6_2
  Date: 2017/6/14
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/taglibs.jsp" %>

<html>
<head>
    <link rel="icon" href="/imgs/favicon.ico" type="image/x-icon"/>

    <meta charset="UTF-8">
    <link href="../css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="../css/fileinput.min.css" type="text/css" rel="stylesheet">
    <link href="../css/jquery.mCustomScrollbar.min.css" type="text/css"
          rel="stylesheet">
    <link href="../css/pagination.css" type="text/css" rel="stylesheet">
    <link href="../css/global.css" type="text/css" rel="stylesheet">

    <title>系统管理--人员管理</title>
</head>
<body>
<header class="w-header">
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed"
                        data-toggle="collapse" data-target="#navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a href="../index.jsp"><img class="favicon-ico"
                                            src="../imgs/head_logo.png">
                </a>
            </div>
            <div class="navbar-collapse collapse pull-right" id="navbar-collapse">

                <ul class="nav navbar-nav navbar-menu">
                    <li>
                        <a href="../sprider.jsp">事件抓取</a>
                    </li>
                    <li>
                        <a href="../analysis.jsp">事件分析</a>
                    </li>
                    <li>
                        <a href="../traffic.jsp">客流量影响</a>
                    </li>
                    <li>
                        <a href="../weather.jsp"> <span>天气情况</span> </a>
                    </li>
                    <li>
                        <a href="../flight.jsp"> <span>航班预警图</span> </a>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-user" id="logined">
                    <li>
                        <a data-toggle="dropdown" class="user-name" href="/user/info.jsp">
                            用户名 <span class="caret"></span> </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="/user/changeps.jsp">修改密码</a>
                            </li>
                            <li>
                                <a href="/employee/logout">退出登陆</a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>

<main class="w-main">
    <div class="container-fluid">
        <div class="left-menu col-md-2 col-xs-2">
            <ul class="nav nav-stacked">
                <li>
                    <a class="left-menu-title"> <i class="nav-ico menu-ico">&nbsp;</i>
                        <span class="left-menu-content">菜单</span> </a>
                </li>

                <li>
                    <a href="manageevent.jsp"> <i class="nav-ico event-ico">&nbsp;</i>
                        <span class="left-menu-content">事件管理</span> </a>
                </li>

                <li>
                    <a href="manageweather.jsp"> <i class="nav-ico weather-ico">&nbsp;</i>
                        <span class="left-menu-content">天气源管理</span> </a>
                </li>

                <li class="active">
                    <a href="manageperson.jsp"> <i class="nav-ico info-ico">&nbsp;</i>
                        <span class="left-menu-content">人员管理</span> </a>
                </li>
                <li>
                    <a href="managecity.jsp"> <i class="nav-ico city-ico">&nbsp;</i>
                        <span class="left-menu-content">城市管理</span> </a>
                </li>
                <li>
                    <a href="manageroute.jsp"> <i class="nav-ico m_pf-ico">&nbsp;</i>
                        <span class="left-menu-content">路线管理</span> </a>
                </li>
            </ul>
            <!-- /.left-nav-ul end.-->
        </div>
        <div class="content mCustomScrollbar col-md-10 col-xs-10">
            <div class="container-fluid">
                <div class="panel panel-manage">
                    <div class="panel-body">
                        <div class="span loading">
                            <div class="loader-inner ball-spin-fade-loader">
                                <div></div>
                                <div></div>
                                <div></div>
                                <div></div>
                                <div></div>
                                <div></div>
                                <div></div>
                                <div></div>
                            </div>
                        </div>
                        <div class="panel-item">
                            <p>
                                人员列表
                            </p>
                            <div class="manage-search">


                                <div class="pull-right">
                                    <a class="btn btn-primary" data-target="#person-add"
                                       data-toggle="modal"> <i class="manage-ico ico-add"></i> <span>新增</span>
                                    </a>
                                    <a href="/download" class="btn btn-default">下载样本</a>
                                    <a class="btn btn-default" data-target="#upload-download"
                                       data-toggle="modal"> <i class="manage-ico ico-import"></i>
                                        <span>导入</span> </a>
                                </div>
                                <div class="pull-right">
                                    <form class="form-inline">
                                        <div class="form-group">
                                            <div class="col-md-12 col-xs-12">
                                                <div class="input-group">
                                                    <input class="form-control" type="text"
                                                           id="search-content" placeholder="输入用户名">
                                                    <span class="input-group-addon"> <a
                                                            class="manage-ico ico-search" id="search-btn"></a> </span>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="pull-left">
                                    <ul class="manage-control">
                                        <li>
                                            <i class="cCheckbox"></i>
                                            <span>全选</span>
                                        </li>
                                        <li id="delete-all">
                                            <i class="manage-ico ico-del btn">&nbsp;</i>
                                            <span>删除</span>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="panel-item person-list">
                            <table class="table">
                                <thead>
                                <tr>
                                    <td>
                                        选择
                                    </td>
                                    <td>
                                        序号
                                    </td>
                                    <td>
                                        工号
                                    </td>
                                    <td>
                                        姓名
                                    </td>
                                    <td>
                                        权限
                                    </td>
                                    <td>
                                        电话
                                    </td>
                                    <td>
                                        邮箱
                                    </td>
                                    <td>
                                        操作
                                    </td>
                                </tr>
                                </thead>
                                <tbody id="content">

                                </tbody>
                            </table>
                        </div>
                        <div class="cPager panel-item" id="cPager"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<div class="modal fade" id="person-detail" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
                <h4 class="modal-title">
                    人员详情
                </h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-2 col-md-offset-3 col-xs-3">
                            <p>
                                工号：
                            </p>
                        </div>
                        <div class="col-md-4 col-xs-9">
                            <p id="person-no"></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-2 col-md-offset-3 col-xs-3">
                            <p>
                                姓名：
                            </p>
                        </div>
                        <div class="col-md-4 col-xs-9">
                            <p id="person-name"></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-2 col-md-offset-3 col-xs-3">
                            <p>
                                权限：
                            </p>
                        </div>
                        <div class="col-md-4 col-xs-9">
                            <p id="person-authority"></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-2 col-md-offset-3  col-xs-3">
                            <p>
                                电话：
                            </p>
                        </div>
                        <div class="col-md-4 col-xs-9">
                            <p id="person-phone"></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-2 col-md-offset-3 col-xs-3">
                            <p>
                                邮箱：
                            </p>
                        </div>
                        <div class="col-md-4 col-xs-9">
                            <p id="person-email"></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="person-edit" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
                <h4 class="modal-title">
                    人员编辑
                </h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <div class="col-md-6">
                                <input class="form-control" type="hidden" id="edit-id"
                                       name="id" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 col-md-offset-2 control-label">
                                工号：
                            </label>
                            <div class="col-md-6 has-success">
                                <input class="form-control" type="text" id="edit-no" name="no"
                                       readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 col-md-offset-2 control-label">
                                姓名：
                            </label>
                            <div class="col-md-6 has-success">
                                <input class="form-control" id="edit-name" type="text"
                                       name="name">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 col-md-offset-2 control-label">
                                权限：
                            </label>
                            <div class="col-md-6 has-success">
                                <select class="form-control" name="authority"
                                        id="edit-authority">
                                    <option value="0">
                                        管理员
                                    </option>
                                    <option value="1">
                                        员工
                                    </option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 col-md-offset-2 control-label">
                                电话：
                            </label>
                            <div class="col-md-6 has-success">
                                <input class="form-control" id="edit-phone" name="phone"
                                       type="tel">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 col-md-offset-2 control-label">
                                邮箱：
                            </label>
                            <div class="col-md-6 has-success">
                                <input class="form-control" id="edit-email" name="email"
                                       type="email">
                            </div>
                        </div>
                        <span class="help-block"></span>
                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-2 col-xs-3 col-xs-offset-2">
                                <button class="btn btn-block btn-default" type="button"
                                        data-dismiss="modal">
                                    取消
                                </button>
                            </div>
                            <div class="col-md-3 col-md-offset-3 col-xs-3 col-xs-offset-3">
                                <button class="btn btn-block btn-primary" type="button"
                                        name="submit">
                                    确定
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="person-add" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
                <h4 class="modal-title">
                    人员新增
                </h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label class="col-md-2 col-md-offset-2 control-label">
                                工号：
                            </label>
                            <div class="col-md-6">
                                <input class="form-control" name="no" type="text"
                                       placeholder="工号">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 col-md-offset-2 control-label">
                                姓名：
                            </label>
                            <div class="col-md-6">
                                <input class="form-control" name="name" type="text"
                                       placeholder="姓名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 col-md-offset-2 control-label">
                                权限：
                            </label>
                            <div class="col-md-6">
                                <select class="form-control" name="authority">
                                    <option value="-1" selected>
                                        权限
                                    </option>
                                    <option value="0">
                                        管理员
                                    </option>
                                    <option value="1">
                                        员工
                                    </option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 col-md-offset-2 control-label">
                                电话：
                            </label>
                            <div class="col-md-6">
                                <input class="form-control" name="phone" type="tel"
                                       placeholder="电话">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 col-md-offset-2 control-label">
                                邮箱：
                            </label>
                            <div class="col-md-6">
                                <input class="form-control" name="email" type="email"
                                       placeholder="邮箱">
                            </div>
                        </div>
                        <span class="help-block"></span>
                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-2 col-xs-3 col-xs-offset-2">
                                <button class="btn btn-block btn-default" type="button"
                                        data-dismiss="modal">
                                    取消
                                </button>
                            </div>
                            <div class="col-md-3 col-md-offset-3 col-xs-3 col-xs-offset-3">
                                <button class="btn btn-block btn-primary" type="button"
                                        name="submit">
                                    确定
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="upload-download" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
                <h4 class="modal-title">
                    人员新增
                </h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="col-md-12">
                        <form id="upload-form">
                            <input name="file" type="file" multiple id="upload"
                                   class="file-loading">
                        </form>
                    </div>
                </div>
                <div class="container-fluid">
                    <div id="kv-success"
                         class="alert alert-success fade in upload-success">
                        <h5></h5>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="../js/jquery-2.2.4.min.js"></script>
<script src="../js/jquery.cookie.js" type="text/javascript"></script>
<script src="../js/rgb.js" type="text/javascript"></script>
<script src="../js/jquery.pagination.js"></script>
<script src="../js/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/piexif.min.js"></script>
<script src="../js/sortable.min.js"></script>
<script src="../js/purify.min.js"></script>
<script src="../js/fileinput.min.js"></script>
<script src="../js/theme.min.js"></script>
<script src="../js/zh.js"></script>
<script src="../js/manageperson.js"></script>

</body>
</html>

