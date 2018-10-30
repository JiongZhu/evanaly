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

    <title>系统管理--城市人流量管理</title>
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
                <li class="active">
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
                        <div class="panel-item">
                            <p>
                                城市列表
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
                                    <a class="btn btn-primary" data-target="#city-addoredit"
                                       data-toggle="modal"> <i class="manage-ico ico-add"></i> <span>新增</span>
                                    </a>
                                </div>
                                <div class="pull-right">
                                    <form class="form-inline">
                                        <div class="form-group">
                                            <div class="col-md-12">
                                                <div class="input-group">
                                                    <input class="form-control" type="text"
                                                           id="search-content" placeholder="输入城市名">
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
                                        城市
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


<div class="modal fade" id="humantraffic-add" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
                <h4 class="modal-title">
                    新增城市
                </h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 col-md-offset-2 col-xs-4 control-label">
                                城市：
                            </label>
                            <div class="col-md-6 col-xs-8">
                                <select class="form-control"></select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-xs-4 col-md-offset-2 control-label">
                                姓名：
                            </label>
                            <div class="col-md-6 col-xs-8">
                                <input class="form-control" type="text" placeholder="姓名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-xs-4 col-md-offset-2 control-label">
                                正常人流量：
                            </label>
                            <div class="col-md-6 col-xs-8">
                                <input class="form-control" type="text" placeholder="正常人流量">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-xs-4 col-md-offset-2 control-label">
                                紧张人流量：
                            </label>
                            <div class="col-md-6 col-xs-8">
                                <input class="form-control" type="text" placeholder="紧张人流量">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-xs-4 col-md-offset-2 control-label">
                                饱和人流量：
                            </label>
                            <div class="col-md-6 col-xs-8">
                                <input class="form-control" type="text" placeholder="饱和人流量">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-2 col-xs-3 col-xs-offset-2">
                                <button class="btn btn-block btn-default" type="button"
                                        data-dismiss="modal">
                                    取消
                                </button>
                            </div>
                            <div class="col-md-3 col-md-offset-3 col-xs-3 col-xs-offset-3">
                                <button class="btn btn-block btn-primary">
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

<div class="modal fade" id="city-addoredit" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
                <h4 class="modal-title">
                    城市添加
                </h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form class="form-horizontal" id="form-addoredit">
                        <div class="form-group">
                            <input class="form-control" type="hidden" id="val_id" name="id" readonly>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-xs-4 col-md-offset-2 control-label">
                                城市：
                            </label>
                            <div class="col-md-6 col-xs-8">
                                <input class="form-control" type="text" id="val_name"
                                       placeholder="城市名称">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-xs-4 col-md-offset-2 control-label">
                                正常人流量：
                            </label>
                            <div class="col-md-6 col-xs-8">
                                <input class="form-control" type="text" id="val_low"
                                       placeholder="正常人流量">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-xs-4 col-md-offset-2 control-label">
                                紧张人流量：
                            </label>
                            <div class="col-md-6 col-xs-8">
                                <input class="form-control" type="text" id="val_mid"
                                       placeholder="紧张人流量">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-xs-4 col-md-offset-2 control-label">
                                饱和人流量：
                            </label>
                            <div class="col-md-6 col-xs-8">
                                <input class="form-control" type="text" id="val_high"
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
                                <input type="button" value="确定" class="btn btn-block btn-primary" id="btn-addoredit"/>

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
							<span><a>上海</a><i class="ico-close"></i>
							</span>
                    <span><a>北京</a><i class="ico-close"></i>
							</span>
                    <span><a>深圳</a><i class="ico-close"></i>
							</span>
                </div>
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="panel-item">
                            <ul class="nav nav-stacked">
                                <li class="active">
                                    <a href="#city-first" data-toggle="tab">ABCD</a>
                                </li>
                                <li>
                                    <a href="#city-second" data-toggle="tab">EFGH</a>
                                </li>
                                <li>
                                    <a href="#city-third" data-toggle="tab">JKLM</a>
                                </li>
                                <li>
                                    <a href="#city-fourth" data-toggle="tab">TWX</a>
                                </li>
                                <li>
                                    <a href="#city-fifth" data-toggle="tab">YZ</a>
                                </li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane fade in active" id="city-first">
                                    <div class="city-list clearfix">
                                        <span>A</span>
                                        <ul>
                                            <li class="city-name">
                                                阿克苏
                                            </li>
                                            <li class="city-name">
                                                安庆
                                            </li>
                                            <li class="city-name">
                                                安康
                                            </li>
                                            <li class="city-name">
                                                安陆
                                            </li>
                                            <li class="city-name">
                                                安顺
                                            </li>
                                            <li class="city-name">
                                                鞍山
                                            </li>
                                            <li class="city-name">
                                                鞍山
                                            </li>
                                            <li class="city-name">
                                                鞍山
                                            </li>
                                            <li class="city-name">
                                                鞍山
                                            </li>
                                            <li class="city-name">
                                                安阳
                                            </li>
                                            <li class="city-name">
                                                安全
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>B</span>
                                        <ul>
                                            <li>
                                                阿克苏
                                            </li>
                                            <li>
                                                安庆
                                            </li>
                                            <li>
                                                安康
                                            </li>
                                            <li>
                                                安陆
                                            </li>
                                            <li>
                                                安顺
                                            </li>
                                            <li>
                                                鞍山
                                            </li>
                                            <li>
                                                鞍山
                                            </li>
                                            <li>
                                                鞍山
                                            </li>
                                            <li>
                                                鞍山
                                            </li>
                                            <li>
                                                安阳
                                            </li>
                                            <li>
                                                安全
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="city-list clearfix">
                                        <span>C</span>
                                        <ul>
                                            <li>
                                                阿克苏
                                            </li>
                                            <li>
                                                安庆
                                            </li>
                                            <li>
                                                安康
                                            </li>
                                            <li>
                                                安陆
                                            </li>
                                            <li>
                                                安顺
                                            </li>
                                            <li>
                                                鞍山
                                            </li>
                                            <li>
                                                鞍山
                                            </li>
                                            <li>
                                                鞍山
                                            </li>
                                            <li>
                                                鞍山
                                            </li>
                                            <li>
                                                安阳
                                            </li>
                                            <li>
                                                安全
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="tab-pane fade" id="city-second">
                                    ...
                                </div>
                                <div class="tab-pane fade" id="city-third">
                                    ...
                                </div>
                                <div class="tab-pane fade" id="city-fourth">
                                    ...
                                </div>
                                <div class="tab-pane fade" id="city-fifth">
                                    ...
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
                        <button class="btn btn-block btn-primary">
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
<script src="../js/rgb.js" type="text/javascript"></script>
<script src="../js/managercity.js"></script>

</body>
</html>
