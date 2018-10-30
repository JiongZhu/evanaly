<%--
  Created by IntelliJ IDEA.
  User: wx6_2
  Date: 2017/6/21
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/taglibs.jsp"%>
<html>
<head>
    <title>错误--权限不足</title>

    <link href="css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="css/pagination.css" type="text/css" rel="stylesheet">
    <link href="css/jquery.mCustomScrollbar.min.css" type="text/css" rel="stylesheet">
    <link href="css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet">
    <link href="css/global.css" type="text/css" rel="stylesheet">
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
                <a href="index.jsp"><img class="favicon-ico" src="imgs/head_logo.png"></a>
            </div>
            <div class="navbar-collapse collapse" id="navbar-collapse">
                <ul class="nav navbar-nav navbar-right navbar-user" id="logined">
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
                <li>
                    <a href="sprider.jsp">
                        <i class="nav-ico ico-sprider">&nbsp;</i>
                        <span class="left-menu-content">事件抓取</span>
                    </a>
                </li>
                <li>
                    <a href="analysis.jsp">
                        <i class="nav-ico ico-analyze">&nbsp;</i>
                        <span class="left-menu-content">事件分析</span>
                    </a>
                </li>
                <li><a href="traffic.jsp">
                    <i class="nav-ico person-ico">&nbsp;</i>
                    <span class="left-menu-content">客流量影响</span></a>
                </li>
                <li>
                    <a href="weather.jsp">
                        <i class="nav-ico weather-ico">&nbsp;</i>
                        <span class="left-menu-content">天气情况</span>
                    </a>
                </li>
                <li>
                    <a href="flight.jsp">
                        <i class="nav-ico earth-ico">&nbsp;</i>
                        <span class="left-menu-content">航班预警图</span>
                    </a>
                </li>
            </ul>
        </div>

        <!-- 菜单内容 -->
        <div class="content col-md-10 col-xs-10">
            <div class="panel panel-default error-box">
                <div class="panel-heading">权限不足</div>
                <div class="panel-body">
                    <h3>对不起，您没有操作该功能权限</h3>
                </div>
            </div>
        </div>
    </div>
</main>
<script src="js/jquery-2.2.4.min.js"></script>
<script src="js/jquery.cookie.js" type="text/javascript"></script>
<script src="js/rgb.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/authorityerror.js"></script>
</body>
</html>
