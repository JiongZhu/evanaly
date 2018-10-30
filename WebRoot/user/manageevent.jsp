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
    <link rel="icon" href="/imgs/favicon.ico" type="image/x-icon"/>

    <meta charset="UTF-8">
    <link href="../css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="../css/jquery.mCustomScrollbar.min.css" type="text/css"
          rel="stylesheet">
    <link href="../css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet">
    <link href="../css/pagination.css" type="text/css" rel="stylesheet">
    <link href="../css/global.css" type="text/css" rel="stylesheet">

    <title>系统管理--事件管理</title>
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

                <li class="active">
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
                <li>
                    <a href="manageroute.jsp"> <i class="nav-ico m_pf-ico">&nbsp;</i>
                        <span class="left-menu-content">路线管理</span> </a>
                </li>
            </ul>
            <!-- /.left-nav-ul end.-->
        </div>
        <div
                class="content mCustomScrollbar scroll-content col-md-10 col-xs-10">
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
                                事件列表
                            </p>
                            <div class="manage-search">
                                <form class="form-inline">
                                    <div class="form-group">
                                        <input class="form-control" type="text" id="search-name"
                                               placeholder="事件名称">
                                    </div>
                                    <div class="form-group">
                                        <div class="input-group">
                                            <input class="form-control" type="text" id="search-city"
                                                   placeholder="举办城市" readonly>
                                            <span data-toggle="modal" data-target="#selectcity"
                                                  class="input-group-addon" data-multiple="true"><i
                                                    class="manage-ico ico-pos btn">&nbsp;</i>
												</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="date form_datetime input-group calendar"
                                             data-date-format="yyyy-mm-dd" id="search-startDatePicker">
                                            <input class="form-control" size="10" type="text"
                                                   id="search-startDate" placeholder="时间" readonly>
                                            <span class="input-group-addon"><span
                                                    class="glyphicon glyphicon-remove"></span>
												</span>
                                        </div>
                                    </div>
                                    <span>至</span>
                                    <div class="form-group">
                                        <div class="date form_datetime input-group calendar"
                                             data-date-format="yyyy-mm-dd" id="search-endDatePicker">
                                            <input class="form-control" size="10" type="text"
                                                   id="search-endDate" placeholder="时间" readonly>
                                            <span class="input-group-addon"><span
                                                    class="glyphicon glyphicon-remove"></span>
												</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <input class="form-control" type="text" id="search-frequency"
                                               placeholder="一年内频率">
                                    </div>
                                    <div class="form-group">
                                        <select class="form-control" id="search-sponsorLevel">
                                            <option selected value="0">
                                                主办方级别
                                            </option>
                                            <option value="4">
                                                国际性组织
                                            </option>
                                            <option value="1">
                                                国家政府
                                            </option>
                                            <option value="2">
                                                省政府
                                            </option>
                                            <option value="3">
                                                地方政府
                                            </option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <select class="form-control" id="search-sponsorType">
                                            <option selected value="0">
                                                主办方类型
                                            </option>
                                            <option value="4">
                                                国际行业协会
                                            </option>
                                            <option value="3">
                                                国内行业协会
                                            </option>
                                            <option value="2">
                                                国际民间协会
                                            </option>
                                            <option value="1">
                                                国内民间协会
                                            </option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <select class="form-control" id="search-affectAge">
                                            <option selected value="0">
                                                影响年龄阶段
                                            </option>
                                            <option value="4">
                                                老年
                                            </option>
                                            <option value="3">
                                                成年
                                            </option>
                                            <option value="2">
                                                青年
                                            </option>
                                            <option value="1">
                                                儿童
                                            </option>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <select class="form-control" id="search-fixedParticipation">
                                            <option selected value="0">
                                                有固定参与人群
                                            </option>
                                            <option value="1">
                                                是
                                            </option>
                                            <option value="2">
                                                否
                                            </option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <select class="form-control" id="search-affectCommunity">
                                            <option selected value="0">
                                                影响社会群体
                                            </option>
                                            <option value="1">
                                                商务人群
                                            </option>
                                            <option value="2">
                                                社会大众
                                            </option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <select class="form-control" id="search-affectRange">
                                            <option selected value="0">
                                                最大影响范围
                                            </option>
                                            <option value="4">
                                                全球
                                            </option>
                                            <option value="5">
                                                洲际
                                            </option>
                                            <option value="1">
                                                全国
                                            </option>
                                            <option value="2">
                                                全省
                                            </option>
                                            <option value="3">
                                                全市
                                            </option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <select class="form-control" id="search-type">
                                            <option selected value="0">
                                                事件类型
                                            </option>
                                            <option value="1">
                                                展会
                                            </option>
                                            <option value="2">
                                                演唱会
                                            </option>
                                            <option value="3">
                                                体育赛事
                                            </option>
                                            <option value="4">
                                                会议
                                            </option>
                                            <option value="5">
                                                地方性节假日
                                            </option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <select class="form-control" id="search-heat">
                                            <option selected value="0">
                                                事件热度
                                            </option>
                                            <option value="3">
                                                高
                                            </option>
                                            <option value="2">
                                                中
                                            </option>
                                            <option value="1">
                                                低
                                            </option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <select class="form-control" id="search-historicalLevel">
                                            <option selected value="0">
                                                事件历史悠久程度
                                            </option>
                                            <option value="3">
                                                高
                                            </option>
                                            <option value="2">
                                                中
                                            </option>
                                            <option value="1">
                                                低
                                            </option>
                                        </select>
                                    </div>
                                </form>
                                <div class="pull-left">
                                    <ul class="item-control clearfix">
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
                                <div class="pull-right">
                                    <a class="btn btn-primary" id="search-btn"> <i
                                            class="manage-ico ico-search">&nbsp;</i> <span>查询</span> </a>
                                </div>
                            </div>
                        </div>
                        <div class="panel-item event-list">
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
                                        事件名称
                                    </td>
                                    <td>
                                        举办城市
                                    </td>
                                    <td>
                                        开始日期
                                    </td>
                                    <td>
                                        结束日期
                                    </td>
                                    <td>
                                        事件类型
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
                        <div class="cPager panel-item" id="cPager">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<div class="modal fade" id="event-detail" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
                <h4 class="modal-title">
                    <span id="event-name"></span>
                </h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-sm-4 col-sm-offset-1 col-xs-5">
                            <p>
                                举办城市：
                            </p>
                        </div>
                        <div class="col-sm-6 col-xs-6">
                            <p id="event-city"></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-sm-offset-1 col-xs-5">
                            <p>
                                开始日期：
                            </p>
                        </div>
                        <div class="col-sm-6 col-xs-6">
                            <p id="event-startDate"></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-sm-offset-1 col-xs-5">
                            <p>
                                结束日期：
                            </p>
                        </div>
                        <div class="col-sm-6 col-xs-6">
                            <p id="event-endDate"></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-sm-offset-1 col-xs-5">
                            <p>
                                主办方级别：
                            </p>
                        </div>
                        <div class="col-sm-6 col-xs-6">
                            <p id="event-sponsorLevel"></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-sm-offset-1 col-xs-5">
                            <p>
                                主办方类型：
                            </p>
                        </div>
                        <div class="col-sm-6 col-xs-6">
                            <p id="event-sponsorType"></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-sm-offset-1 col-xs-5">
                            <p>
                                影响年龄阶段：
                            </p>
                        </div>
                        <div class="col-sm-6 col-xs-6">
                            <p id="event-affectAge"></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-sm-offset-1 col-xs-5">
                            <p>
                                是否有固定参与人群：
                            </p>
                        </div>
                        <div class="col-sm-6 col-xs-6">
                            <p id="event-fixedParticipation"></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-sm-offset-1 col-xs-5">
                            <p>
                                影响社会群体：
                            </p>
                        </div>
                        <div class="col-sm-6 col-xs-6">
                            <p id="event-affectCommunity"></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-sm-offset-1 col-xs-5">
                            <p>
                                最大影响范围：
                            </p>
                        </div>
                        <div class="col-sm-6 col-xs-6">
                            <p id="event-affectRange"></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-sm-offset-1 col-xs-5">
                            <p>
                                事件类型：
                            </p>
                        </div>
                        <div class="col-sm-6 col-xs-6">
                            <p id="event-type"></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-sm-offset-1 col-xs-5">
                            <p>
                                事件热度：
                            </p>
                        </div>
                        <div class="col-sm-6 col-xs-6">
                            <p id="event-heat"></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-sm-offset-1 col-xs-5">
                            <p>
                                事件历史悠久程度：
                            </p>
                        </div>
                        <div class="col-sm-6 col-xs-6">
                            <p id="event-historicalLevel"></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-sm-offset-1 col-xs-5">
                            <p>
                                事件一年内频率：
                            </p>
                        </div>
                        <div class="col-sm-6 col-xs-6">
                            <p id="event-frequency"></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade event-edit" id="event-edit"
     data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
                <h4 class="modal-title">
                    事件编辑
                </h4>
            </div>
            <div class="modal-body mCustomScrollbar scroll-content">
                <div class="container-fluid">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <input class="form-control" type="hidden" id="edit-id">
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-4 control-label">
                                事件名称：
                            </label>
                            <div class="col-md-8 col-xs-8">
                                <input class="form-control" type="text" id="edit-name"
                                       placeholder="事件名称">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-4 control-label">
                                举办城市：
                            </label>
                            <div class="col-md-8 col-xs-8">
                                <div class="input-group">
                                    <input class="form-control" type="text" id="edit-city"
                                           placeholder="举办城市" readonly>
                                    <span data-toggle="modal" data-target="#selectcity"
                                          data-multiple="false" id="edit-cityico"
                                          class="input-group-addon"><i
                                            class="manage-ico ico-pos btn">&nbsp;</i>
											</span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-4 control-label">
                                开始日期：
                            </label>
                            <div class="col-md-8 col-xs-8">
                                <div class="date form_datetime input-group calendar"
                                     data-date-format="yyyy-mm-dd" id="edit-startDatePicker">
                                    <input class="form-control" size="10" type="text"
                                           id="edit-startDate" readonly>
                                    <span class="input-group-addon"><span
                                            class="glyphicon glyphicon-remove"></span>
											</span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-4 control-label">
                                结束日期：
                            </label>
                            <div class="col-md-8 col-xs-8">
                                <div
                                        class="input-append date form_datetime input-group calendar"
                                        data-date-format="yyyy-mm-dd" id="edit-endDatePicker">
                                    <input class="form-control" id="edit-endDate" size="10"
                                           type="text" readonly>
                                    <span class="input-group-addon"><span
                                            class="glyphicon glyphicon-remove"></span>
											</span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-4 control-label">
                                主办方级别：
                            </label>
                            <div class="col-md-8 col-xs-8">
                                <select class="form-control" id="edit-sponsorLevel">
                                    <option value="0">
                                        主办方级别
                                    </option>
                                    <option value="4">
                                        国际性组织
                                    </option>
                                    <option value="1">
                                        国家政府
                                    </option>
                                    <option value="2">
                                        省政府
                                    </option>
                                    <option value="3">
                                        地方政府
                                    </option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-4 control-label">
                                主办方类型：
                            </label>
                            <div class="col-md-8 col-xs-8">
                                <select class="form-control" id="edit-sponsorType">
                                    <option value="0">
                                        主办方类型
                                    </option>
                                    <option value="4">
                                        国际行业协会
                                    </option>
                                    <option value="3">
                                        国内行业协会
                                    </option>
                                    <option value="2">
                                        国际民间协会
                                    </option>
                                    <option value="1">
                                        国内民间协会
                                    </option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-4 control-label">
                                影响年龄阶段：
                            </label>
                            <div class="col-md-8 col-xs-8">
                                <select class="form-control" id="edit-affectAge">
                                    <option value="0">
                                        影响年龄阶段
                                    </option>
                                    <option value="4">
                                        老年
                                    </option>
                                    <option value="3">
                                        成年
                                    </option>
                                    <option value="2">
                                        青年
                                    </option>
                                    <option value="1">
                                        儿童
                                    </option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-4 control-label">
                                有固定参与人群：
                            </label>
                            <div class="col-md-8 col-xs-8">
                                <select class="form-control" id="edit-fixedParticipation">
                                    <option value="0">
                                        有固定参与人群
                                    </option>
                                    <option value="1">
                                        是
                                    </option>
                                    <option value="2">
                                        否
                                    </option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-4 control-label">
                                影响社会群体：
                            </label>
                            <div class="col-md-8 col-xs-8">
                                <select class="form-control" id="edit-affectCommunity">
                                    <option value="0">
                                        影响社会群体
                                    </option>
                                    <option value="1">
                                        商务人群
                                    </option>
                                    <option value="2">
                                        社会大众
                                    </option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-4 control-label">
                                最大影响范围：
                            </label>
                            <div class="col-md-8 col-xs-8">
                                <select class="form-control" id="edit-affectRange">
                                    <option value="0">
                                        最大影响范围
                                    </option>
                                    <option value="4">
                                        全球
                                    </option>
                                    <option value="5">
                                        洲际
                                    </option>
                                    <option value="1">
                                        全国
                                    </option>
                                    <option value="2">
                                        全省
                                    </option>
                                    <option value="3">
                                        全市
                                    </option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-4 control-label">
                                事件类型：
                            </label>
                            <div class="col-md-8 col-xs-8">
                                <select class="form-control" id="edit-type">
                                    <option value="0">
                                        事件类型
                                    </option>
                                    <option value="1">
                                        展会
                                    </option>
                                    <option value="2">
                                        演唱会
                                    </option>
                                    <option value="3">
                                        体育赛事
                                    </option>
                                    <option value="4">
                                        会议
                                    </option>
                                    <option value="5">
                                        地方性节假日
                                    </option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-4 control-label">
                                事件热度：
                            </label>
                            <div class="col-md-8 col-xs-8">
                                <select class="form-control" id="edit-heat">
                                    <option value="0">
                                        事件热度
                                    </option>
                                    <option value="3">
                                        高
                                    </option>
                                    <option value="2">
                                        中
                                    </option>
                                    <option value="1">
                                        低
                                    </option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-4 control-label">
                                历史悠久程度：
                            </label>
                            <div class="col-md-8 col-xs-8">
                                <select class="form-control" id="edit-historicalLevel">
                                    <option value="0">
                                        历史悠久程度
                                    </option>
                                    <option value="3">
                                        高
                                    </option>
                                    <option value="2">
                                        中
                                    </option>
                                    <option value="1">
                                        低
                                    </option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-4 control-label">
                                一年内频率：
                            </label>
                            <div class="col-md-8 col-xs-8">
                                <input class="form-control" type="text" id="edit-frequency"
                                       placeholder="一年内频率">
                            </div>
                        </div>
                        <span class="help-block"></span>

                        <div class="form-group">
                            <div class="col-md-4 col-md-offset-1 col-xs-4 col-xs-offset-1">
                                <button class="btn btn-block btn-default" type="button"
                                        data-dismiss="modal">
                                    取消
                                </button>
                            </div>
                            <div class="col-md-4 col-md-offset-2 col-xs-4 col-xs-offset-2">
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
                    <div class="col-md-4 col-md-offset-2 col-xs-4 col-xs-offset-1">
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
<script src="../js/bootstrap-datetimepicker.min.js"></script>
<script src="../js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="../js/pinyin_dict_firstletter.js"></script>
<script src="../js/pinyinUtil.js"></script>
<script src="../js/rgb.js"></script>
<script src="../js/manageevent.js"></script>

</body>
</html>

