<%--
  Created by IntelliJ IDEA.
  User: wx6_2
  Date: 2017/9/1
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/taglibs.jsp" %>

<html>
<head>
    <link rel="icon" href="/imgs/favicon.ico" type="image/x-icon"/>

    <meta charset="UTF-8">

    <link href="css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="css/part.css" type="text/css" rel="stylesheet">

    <title>航班预警图</title>
</head>
<body>
<header class="w-header">
    <nav class="navbar navbar-transparent navbar-fixed-top">
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
            <div class="col-xs-3 navbar-collapse collapse pull-right" id="navbar-collapse">
                <ul class="nav navbar-nav navbar-menu">
                    <li>
                        <a href="sprider.jsp">事件抓取</a>
                    </li>
                    <li><a href="analysis.jsp">事件分析</a></li>
                    <li><a href="traffic.jsp">客流量影响</a></li>
                    <li>
                        <a href="weather.jsp">
                            <span>天气情况</span>
                        </a>
                    </li>
                    <li class="active">
                        <a href="flight.jsp">
                            <i class="nav-ico earth-ico">&nbsp;</i>
                            <span class="left-menu-content">航班预警图</span>
                        </a>
                    </li>
                    <li id="manage-menu">
                        <a data-toggle="dropdown">
                            系统管理
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="user/manageevent.jsp">事件管理</a></li>
                            <li><a href="user/manageweather.jsp">天气源管理</a></li>
                            <li><a href="user/manageperson.jsp">人员管理</a></li>
                            <li><a href="user/managecity.jsp">城市管理</a></li>
                            <li><a href="user/manageroute.jsp">路线管理</a></li>
                        </ul>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right navbar-login" id="unlogin">
                    <li>
                        <a href="login.jsp">登录</a>
                    </li>
                </ul>
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

<div class="w-slide" id="carousel-example-generic" data-ride="slide">
    <div class="w-slide-inner">
        <div class="w-slide-item active">
            <div id="line" style="width: 100%; height: 100%"></div>
        </div>
        <div class="w-slide-item">
            <div id="scatter" style="width: 100%; height: 100%"></div>
        </div>

    </div>

    <ol class="w-slide-controller">
        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
        <li data-target="#carousel-example-generic" data-slide-to="1"></li>
    </ol>
</div>

<footer></footer>

<script src="js/jquery-2.2.4.min.js" type="text/javascript"></script>
<script src="js/jquery.cookie.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<script src="js/rgb.js" type="text/javascript"></script>
<script src="js/echarts.min.js" type="text/javascript"></script>
<script src="js/flight.js" type="text/javascript"></script>

</body>
</html>

