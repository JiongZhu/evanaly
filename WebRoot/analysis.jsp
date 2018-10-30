<%--
  Created by IntelliJ IDEA.
  User: wx6_2
  Date: 2017/6/14
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/taglibs.jsp"%>

<html>
<head>
    <link rel="icon" href="/imgs/favicon.ico" type="image/x-icon"/>

    <meta charset="UTF-8">
    <link href="css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="css/jquery.mCustomScrollbar.min.css" type="text/css" rel="stylesheet">
    <link href="css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet">
    <link href="css/global.css" type="text/css" rel="stylesheet">

    <title>事件分析</title>
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
                <li class="active">
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
                <li id="manage-menu">
                    <a href="#second-manage" data-toggle="collapse">
                        <i class="nav-ico ico-set">&nbsp;</i>
                        <span class="left-menu-content">系统管理</span>
                    </a>
                    <ol class="w-second-nav collapse in" id="second-manage">
                        <li>
                            <a href="user/manageevent.jsp">
                                <i class="nav-ico ico-item"></i>
                                <span>事件管理</span>
                            </a>
                        </li>
                        <li>
                            <a href="user/manageweather.jsp">
                                <i class="nav-ico ico-item"></i>
                                <span>天气源管理</span>
                            </a>
                        </li>
                        <li>
                            <a href="user/manageperson.jsp">
                                <i class="nav-ico ico-item"></i>
                                <span>人员管理</span>
                            </a>
                        </li>
                         <li>
							<a href="user/managecity.jsp">
                                <i class="nav-ico ico-item"></i>
                                <span>城市管理</span>
                            </a>
                        </li><li>
							<a href="user/manageroute.jsp">
                                <i class="nav-ico ico-item"></i>
                                <span>路线管理</span>
                            </a>
                        </li>
                    </ol>
                </li>
            </ul>
        </div>

        <!-- 菜单内容 -->
        <div class="content mCustomScrollbar scroll-content col-md-10 col-xs-10">
            <div class="panel panel-default analyze-box">
                <div class="panel-heading">未来一周事件分析</div>
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
                        <p>事件类型分析</p>
                        <div class="row">
                            <div class="col-md-12 col-xs-12">
                                <div class="chart" id="type"></div>
                            </div>
                            <div class="col-md-7 col-xs-12">
                                <div class="chart" id="type-pie">

                                </div>
                            </div>
                            <div class="col-md-5 col-xs-12">
                                <div class="chart" id="type-radar">

                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="panel-item">
                        <p>热门城市</p>
                        <div class="row">
                            <div class="col-md-12 col-xs-12">
                                <div class="chart" id="city"></div>
                            </div>
                            <div class="col-md-7 col-xs-12">
                                <div class="chart" id="city-pie">

                                </div>
                            </div>
                            <div class="col-md-5 col-xs-12">
                                <div class="chart" id="city-radar">

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel-item">
                        <p>时间分布</p>
                        <div class="row">
                            <div class="col-md-12 col-xs-12">
                                <div class="chart" id="date"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="panel panel-default analyze-box">
                <div class="panel-heading">未来一月事件分析</div>
                <div class="panel-body">
                    <div class="panel-item">
                        <p>事件类型分析</p>
                        <div class="row">
                            <div class="col-md-12 col-xs-12">
                                <div class="chart" id="monthType"></div>
                            </div>
                            <div class="col-md-7 col-xs-12">
                                <div class="chart" id="monthType-pie">

                                </div>
                            </div>
                            <div class="col-md-5 col-xs-12">
                                <div class="chart" id="monthType-radar">

                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="panel-item">
                        <p>热门城市</p>
                        <div class="row">
                            <div class="col-md-12 col-xs-12">
                                <div class="chart" id="monthCity"></div>
                            </div>
                            <div class="col-md-7 col-xs-12">
                                <div class="chart" id="monthCity-pie">

                                </div>
                            </div>
                            <div class="col-md-5 col-xs-12">
                                <div class="chart" id="monthCity-radar">

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel-item">
                        <p>时间分布</p>
                        <div class="row">
                            <div class="col-md-12 col-xs-12">
                                <div class="chart" id="monthDate"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>


<script src="js/jquery-2.2.4.min.js"></script>
<script src="js/jquery.cookie.js"></script>
<script src="js/rgb.js"></script>
<script src="js/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/echarts.min.js"></script>
<script src="js/analysis.js"></script>

</body>
</html>

