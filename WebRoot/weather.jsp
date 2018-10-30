<%--
  Created by IntelliJ IDEA.
  User: wx6_2
  Date: 2017/6/22
  Time: 16:06
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

    <title>天气情况</title>
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
                <li class="active">
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
        <div class="content col-md-10 col-xs-10">
            <div class="panel panel-default weather-box">
                <div class="panel-heading">近五天天气状况</div>
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
                            <span class="pull-right" id="date"></span>
                        </p>
                        <h1 class="weather-desc weather-location"><span id="location"></span><span class="glyphicon glyphicon-map-marker ico-location" id="location-switcher"  data-toggle="modal" data-target="#selectcity">&nbsp;</span></h1>
                        <h5 id="des" class="weather-desc"></h5>
                        <h5 id="wind" class="weather-desc"></h5>
                        <h5 id="temperature" class="weather-desc"></h5>
                        <ul class="list-group weather-list">

                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<div class="modal fade modal-selectcity" id="selectcity" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
                <h4 class="modal-title">选择城市</h4>
            </div>
            <div class="modal-body">
                <div class="city-selected clearfix">
                </div>
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="panel-item">
                            <ul class="nav nav-stacked">
                                <li class="active"><a href="#city-first" data-toggle="tab" id="firstpanel-link">ABCD</a></li>
                                <li><a href="#city-second" data-toggle="tab" id="secondpanel-link">EFGH</a></li>
                                <li><a href="#city-third" data-toggle="tab" id="thirdpanel-link">IJKL</a></li>
                                <li><a href="#city-fourth" data-toggle="tab" id="fourthpanel-link">MNOP</a></li>
                                <li><a href="#city-fifth" data-toggle="tab" id="fifthpanel-link">QRST</a></li>
                                <li><a href="#city-sixth" data-toggle="tab" id="sixthpanel-link">UVW</a></li>
                                <li><a href="#city-seventh" data-toggle="tab" id="seventhpanel-link">XYZ</a></li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane fade in active" id="city-first">
                                    <div class="city-list clearfix">
                                        <span>A</span>
                                        <ul>

                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>B</span>
                                        <ul>

                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>C</span>
                                        <ul>

                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>D</span>
                                        <ul>

                                        </ul>
                                    </div>
                                </div>
                                <div class="tab-pane fade" id="city-second">
                                    <div class="city-list clearfix">
                                        <span>E</span>
                                        <ul>

                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>F</span>
                                        <ul>

                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>G</span>
                                        <ul>

                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>H</span>
                                        <ul>

                                        </ul>
                                    </div>
                                </div>
                                <div class="tab-pane fade" id="city-third">
                                    <div class="city-list clearfix">
                                        <span>I</span>
                                        <ul>

                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>J</span>
                                        <ul>

                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>K</span>
                                        <ul>

                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>L</span>
                                        <ul>

                                        </ul>
                                    </div>
                                </div>
                                <div class="tab-pane fade" id="city-fourth">
                                    <div class="city-list clearfix">
                                        <span>M</span>
                                        <ul>

                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>N</span>
                                        <ul>

                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>O</span>
                                        <ul>

                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>P</span>
                                        <ul>

                                        </ul>
                                    </div>


                                </div>
                                <div class="tab-pane fade" id="city-fifth">
                                    <div class="city-list clearfix">
                                        <span>Q</span>
                                        <ul>

                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>R</span>
                                        <ul>

                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>S</span>
                                        <ul>

                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>T</span>
                                        <ul>

                                        </ul>
                                    </div>

                                </div>
                                <div class="tab-pane fade" id="city-sixth">
                                    <div class="city-list clearfix">
                                        <span>U</span>
                                        <ul>

                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>V</span>
                                        <ul>

                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>W</span>
                                        <ul>

                                        </ul>
                                    </div>

                                </div>
                                <div class="tab-pane fade" id="city-seventh">
                                    <div class="city-list clearfix">
                                        <span>X</span>
                                        <ul>

                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>Y</span>
                                        <ul>

                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>Z</span>
                                        <ul>

                                        </ul>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="row">
                    <div class="col-md-4 col-md-offset-1 col-xs-4  col-xs-offset-1">
                        <button class="btn btn-block btn-default" data-dismiss="modal">取消</button>
                    </div>
                    <div class="col-md-4 col-md-offset-2 col-xs-4  col-xs-offset-2">
                        <button class="btn btn-block btn-primary" type="button" id="city-confirm">确定</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="js/jquery-2.2.4.min.js"></script>
<script src="js/jquery.cookie.js"></script>
<script src="js/rgb.js"></script>
<script src="js/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/echarts.min.js"></script>
<script src="js/pinyin_dict_firstletter.js"></script>
<script src="js/pinyinUtil.js"></script>
<script src="js/jquery.cookie.js"></script>
<script src="js/weather.js"></script>

</body>
</html>

