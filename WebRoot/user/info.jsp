<%--
  Created by IntelliJ IDEA.
  User: wx6_2
  Date: 2017/6/14
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/taglibs.jsp"%>

<html>
<head>
    <link rel="icon" href="/imgs/favicon.ico" type="image/x-icon"/>

    <meta charset="UTF-8">
    <link href="../css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="../css/global.css" type="text/css" rel="stylesheet">

    <title>个人中心-个人资料</title>
</head>
<body>
<header class="w-header">
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a href="../index.jsp"><img class="favicon-ico" src="../imgs/head_logo.png"></a>
            </div>
            <div class="navbar-collapse collapse pull-right" id="navbar-collapse">

                <ul class="nav navbar-nav navbar-menu">
                    <li>
                        <a href="../sprider.jsp">事件抓取</a>
                    </li>
                    <li><a href="../analysis.jsp">事件分析</a></li>
                    <li><a href="../traffic.jsp">客流量影响</a></li>
                    <li>
                        <a href="../weather.jsp">
                            天气情况
                        </a>
                    </li>
                    <li>
                        <a href="/flight.jsp">
                            <span>航班预警图</span>
                        </a>
                    </li>
                    <li id="manage-menu">
                        <a data-toggle="dropdown" href>
                            系统管理
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="manageevent.jsp">事件管理</a></li>
                            <li><a href="manageweather.jsp">天气源管理</a></li>
                            <li><a href="manageperson.jsp">人员管理</a></li>
                        </ul>
                    </li>

                </ul>
                <ul class="nav navbar-nav navbar-user" id="logined">
                    <li>
                        <a data-toggle="dropdown" class="user-name" href="user/info.jsp">
                            用户名
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="user/changeps.jsp">修改密码</a></li>
                            <li><a href="/employee/logout">退出登陆</a></li>
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
                    <a class="left-menu-title">
                        <i class="nav-ico menu-ico">&nbsp;</i>
                        <span class="left-menu-content">菜单</span>
                    </a>
                </li>
                <li class="active">
                    <a href="info.jsp">
                        <i class="nav-ico info-ico">&nbsp;</i>
                        <span class="left-menu-content">个人资料</span>
                    </a>
                </li>
                <li>
                    <a href="changeps.jsp">
                        <i class="nav-ico ps-ico">&nbsp;</i>
                        <span class="left-menu-content">修改密码</span>
                    </a>
                </li>
            </ul>
            <!-- /.left-nav-ul end.-->
        </div>
        <div class="content col-md-10 col-xs-10">
            <div class="container-fluid">
                <div class="panel panel-user">
                    <!-- update employee info -->
                    <div class="panel-body" id="user-update">
                        <div class="panel-item">
                            <p>修改个人资料</p>
                            <form class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-md-2 col-xs-3 control-label has-success" for="employ-no">工号：</label>
                                    <div class="col-md-10 col-xs-9">
                                        <input class="form-control" id="employ-no" type="text" placeholder="工号"
                                               disabled="disabled">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 col-xs-3 control-label has-success" for="employ-name">姓名：</label>
                                    <div class="col-md-10 col-xs-9">
                                        <input class="form-control" id="employ-name" type="text" placeholder="姓名">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 col-xs-3 control-label has-success" for="employ-phone">电话：</label>
                                    <div class="col-md-10 col-xs-9">
                                        <input class="form-control" id="employ-phone" type="tel" placeholder="电话">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 col-xs-3 control-label has-success" for="employ-email">邮箱：</label>
                                    <div class="col-md-10 col-xs-9">
                                        <input class="form-control" id="employ-email" type="email" placeholder="邮箱">
                                    </div>
                                </div>
                                <span class="help-block"></span>
                                <div class="form-group">
                                    <div class="col-md-3 col-md-offset-2 col-xs-5 col-xs-offset-1">
                                        <button class="btn btn-primary btn-block" id="info-confirm" type="button">确定</button>
                                    </div>
                                    <div class="col-md-3 col-md-offset-2 col-xs-5 col-xs-offset-1">
                                        <button class="btn btn-primary btn-block" id="info-cancel" type="button">取消</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- employee info -->
                    <div class="panel-body" id="user-info">
                        <div class="panel-item">
                            <p><span>个人资料</span><a class="pull-right" href="" id="to-update">修改个人资料</a></p>

                            <table class="table">
                                <tbody>
                                <tr>
                                    <td>工号：</td>
                                    <td id="info-no"></td>
                                </tr>
                                <tr>
                                    <td>姓名：</td>
                                    <td id="info-name"></td>
                                </tr>
                                <tr>
                                    <td>电话：</td>
                                    <td id="info-tel"></td>
                                </tr>
                                <tr>
                                    <td>邮箱：</td>
                                    <td id="info-email"></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<script src="../js/jquery-2.2.4.min.js"></script>
<script src="../js/jquery.cookie.js" type="text/javascript"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/rgb.js"></script>
<script src="../js/info.js"></script>

</body>
</html>

