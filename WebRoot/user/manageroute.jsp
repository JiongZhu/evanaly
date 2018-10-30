<%--
  Created by IntelliJ IDEA.
  User: wx6_2
  Date: 2017/6/14
  Time: 14:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/taglibs.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <link href="../css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="../css/jquery.mCustomScrollbar.min.css" type="text/css"
          rel="stylesheet">
    <link href="../css/pagination.css" type="text/css" rel="stylesheet">
    <link href="../css/global.css" type="text/css" rel="stylesheet">

    <title>系统管理--路线管理</title>
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
                                            src="../imgs/head_logo.png"> </a>
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
                </ul>

                <ul class="nav navbar-nav navbar-user" id="logined">
                    <li>
                        <a class="user-name" href="info.jsp">用户名</a>
                        <div class="nav-user bottom">
                            <div class="arrow"></div>
                            <ul class="nav">
                                <li>
                                    <a href="changeps.jsp">修改密码</a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a href="/employee/logout">退出登陆</a>
                                </li>
                            </ul>
                        </div>
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

                <li>
                    <a href="manageperson.jsp"> <i class="nav-ico info-ico">&nbsp;</i>
                        <span class="left-menu-content">人员管理</span> </a>
                </li>
                <li>
                    <a href="managecity.jsp"> <i class="nav-ico city-ico">&nbsp;</i>
                        <span class="left-menu-content">城市管理</span> </a>
                </li>
                <li class="active">
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
                        <div class="panel-item">
                            <p>
                                路线列表
                            </p>
                            <div class="manage-search">
                                <div class="pull-left">
                                    <ul class="manage-control">
                                        <li>
                                            <i class="cCheckbox"></i>
                                            <span>全选</span>
                                        </li>
                                        <li id="delete-all">
                                            <i class="manage-ico ico-del">&nbsp;</i>
                                            <span>删除</span>
                                        </li>
                                    </ul>
                                </div>
                                <div class="pull-right">
                                    <a class="btn btn-primary" data-target="#route-addoredit"
                                       data-toggle="modal"> <i class="manage-ico ico-add"></i> <span>新增</span>
                                    </a>
                                </div>
                                <div class="pull-right">
                                    <form class="form-inline">
                                        <div class="form-group">
                                            <div class="col-md-12">
                                                <input class="form-control" type="text"
                                                       id="search-fromCity" name="" placeholder="出发地">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-12">
                                                <div class="input-group">
                                                    <input class="form-control search-content" type="text"
                                                           id="search-toCity" placeholder="目的地">
                                                    <span class="input-group-addon"> <a
                                                            class="manage-ico ico-search search-btn btn"
                                                            id="search-btn"></a> </span>
                                                    <span class="input-group-addon"> <input
                                                            type="reset" class="manage-ico ico-del  btn" value="">
														</span>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
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
                                        出发地
                                    </td>
                                    <td>
                                        目的地
                                    </td>
                                    <td>
                                        正常人流量
                                    </td>
                                    <td>
                                        紧张人流量
                                    </td>
                                    <td>
                                        饱和人流量
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


<div class="modal fade" id="route-addoredit" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
                <h4 class="modal-title">
                    添加路线
                </h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form class="form-horizontal" id="form-addoredit">
                        <input class="form-control" type="hidden" id="val_id" name="id"
                               readonly>
                        <input class="form-control" type="hidden" id="val_fromCityId"
                               name="fromCity.id" readonly>
                        <input class="form-control" type="hidden" id="val_toCityId"
                               name="toCity.id" readonly>
                        <div class="form-group">
                            <label class="col-md-3 col-md-offset-2 col-xs-4 control-label">
                                出发地：
                            </label>
                            <div class="col-md-6 col-xs-8">
                                <div class="input-group">
                                    <input class="form-control edit-city" type="text" id="val_fromCity"
                                           placeholder="出发地" readonly>
                                    <span data-toggle="modal" data-target="#selectcity"
                                          data-multiple="false" data-src="from" id="edit-cityico"
                                          class="input-group-addon edit-cityico"><i
                                            class="manage-ico ico-pos btn ">&nbsp;</i>
											</span>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 col-md-offset-2 col-xs-4 control-label">
                                目的地：
                            </label>
                            <div class="col-md-6  col-xs-8">
                                <div class="input-group">
                                    <input class="form-control edit-city" type="text" id="val_toCity"
                                           placeholder="目的地" readonly>
                                    <span data-toggle="modal" data-target="#selectcity"
                                          data-multiple="false" id="edit-cityico"
                                          class="input-group-addon edit-cityico"><i
                                            class="manage-ico ico-pos btn">&nbsp;</i>
											</span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-md-offset-2 col-xs-4 control-label">
                                正常人流量：
                            </label>
                            <div class="col-md-6 col-xs-8">
                                <input class="form-control" type="text" name="low" id="val_low"
                                       placeholder="正常人流量">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-md-offset-2 col-xs-4 control-label">
                                紧张人流量：
                            </label>
                            <div class="col-md-6  col-xs-8">
                                <input class="form-control" type="text" name="mid" id="val_mid"
                                       placeholder="紧张人流量">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-md-offset-2 col-xs-4 control-label">
                                饱和人流量：
                            </label>
                            <div class="col-md-6 col-xs-8">
                                <input class="form-control" type="text" name="high" id="val_high"
                                       placeholder="饱和人流量">
                            </div>
                        </div>
                        <span class="help-block" id="help-block"></span>
                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-2 col-xs-3 col-xs-offset-2">
                                <button class="btn btn-block btn-default" type="button"
                                        data-dismiss="modal">
                                    取消
                                </button>
                            </div>
                            <div class="col-md-3 col-md-offset-3 col-xs-3 col-xs-offset-3">
                                <input type="button" value="确定"
                                       class="btn btn-block btn-primary" id="btn-addoredit"/>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade modal-selectcity" id="selectcity"
     data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
                <h4 class="modal-title">
                    选择城市
                </h4>
            </div>
            <div class="modal-body">
                <div class="city-selected clearfix">
                </div>
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="panel-item">
                            <ul class="nav nav-stacked">
                                <li class="active">
                                    <a href="#city-first" data-toggle="tab" id="firstpanel-link">ABCD</a>
                                </li>
                                <li>
                                    <a href="#city-second" data-toggle="tab"
                                       id="secondpanel-link">EFGH</a>
                                </li>
                                <li>
                                    <a href="#city-third" data-toggle="tab" id="thirdpanel-link">IJKL</a>
                                </li>
                                <li>
                                    <a href="#city-fourth" data-toggle="tab"
                                       id="fourthpanel-link">MNOP</a>
                                </li>
                                <li>
                                    <a href="#city-fifth" data-toggle="tab" id="fifthpanel-link">QRST</a>
                                </li>
                                <li>
                                    <a href="#city-sixth" data-toggle="tab" id="sixthpanel-link">UVW</a>
                                </li>
                                <li>
                                    <a href="#city-seventh" data-toggle="tab"
                                       id="seventhpanel-link">XYZ</a>
                                </li>
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
                    <div class="col-md-4 col-md-offset-1 col-xs-4 col-xs-offset-1">
                        <button class="btn btn-block btn-default" data-dismiss="modal">
                            取消
                        </button>
                    </div>
                    <div class="col-md-4 col-md-offset-2 col-xs-4 col-xs-offset-2">
                        <button class="btn btn-block btn-primary" type="button"
                                id="city-confirm">
                            确定
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="../js/jquery-2.2.4.min.js"></script>
<script src="../js/jquery.cookie.js" type="text/javascript"></script>
<script src="../js/jquery.pagination.js"></script>
<script src="../js/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/pinyin_dict_firstletter.js"></script>
<script src="../js/pinyinUtil.js"></script>
<script src="../js/rgb.js" type="text/javascript"></script>
<script src="../js/manageroute.js"></script>

</body>
</html>
