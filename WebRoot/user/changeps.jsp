<%--
  Created by IntelliJ IDEA.
  User: wx6_2
  Date: 2017/6/14
  Time: 14:47
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

    <title>个人中心-修改密码</title>
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
                <li>
                    <a href="info.jsp">
                        <i class="nav-ico info-ico">&nbsp;</i>
                        <span class="left-menu-content">个人资料</span>
                    </a>
                </li>
                <li class="active">
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
                    <!-- change password  -->
                    <div class="panel-body" id="user-changeps">
                        <div class="panel-item">
                            <p>修改密码</p>
                            <form class="form-horizontal" method="post" id="changeps-form" action="/employee/changePS">
                                <div class="form-group">
                                    <label class="col-md-2 col-sm-4 control-label" for="employ-ps">初始密码：</label>
                                    <div class="col-md-10 col-sm-8">
                                        <input class="form-control" id="employ-ps" type="password" placeholder="初始密码">
                                        <input class="form-control" id="employ-no" name="no" type="hidden">
                                        <span class="help-block"></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 col-sm-4 control-label" for="employ-newps">新密码：</label>
                                    <div class="col-md-10 col-sm-8">
                                        <input class="form-control" id="employ-newps" name="password" type="password" placeholder="新密码">
                                        <span class="help-block"></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 col-sm-4 control-label" for="employ-confirmps">确认密码：</label>
                                    <div class="col-md-10 col-sm-8">
                                        <input class="form-control" id="employ-confirmps" type="password" placeholder="确认密码">
                                        <span class="help-block"></span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-md-6 col-md-offset-3">
                                        <button class="btn btn-primary btn-block" id="submit" type="submit">修改密码</button>
                                    </div>
                                </div>
                            </form>
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
<script src="../js/changeps.js"></script>

</body>
</html>

