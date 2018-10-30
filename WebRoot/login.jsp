<%--
  Created by IntelliJ IDEA.
  User: wx6_2
  Date: 2017/6/14
  Time: 8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/taglibs.jsp"%>

<html>
<head>
    <link rel="icon" href="/imgs/favicon.ico" type="image/x-icon"/>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="css/part.css" type="text/css" rel="stylesheet">

    <title>登录</title>
</head>
<body>

<main class="w-main bg height-fluid">
    <div class="container-fluid">
        <div class="container">
            <div class="col-md-8"><a class="login-title" href="index.jsp">基于社会事件的民航客流量分析系统</a></div>
        </div>
        <div class="container">
            <div class="col-md-4 col-xs-12 pull-right login-box">
                <div class="col-sm-12 col-xs-12 login-nav">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#login-no" data-toggle="tab">账号登录</a></li>
                        <li><a href="#login-phone" data-toggle="tab">手机登录</a></li>
                        <li><a href="#qrcode-login" data-toggle="tab">扫码登录</a></li>
                    </ul>
                </div>
                <div class="tab-content">
                    <div class="tab-pane active" id="login-no">
                        <div class="col-sm-12 col-xs-12">
                            <form class="form-horizontal">
                                <div class="form-group">
                                    <div class="col-sm-12 col-xs-12">
                                        <input class="form-control" name="no" id="employee-no" type="text" placeholder="工号">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-12 col-xs-12">
                                        <input class="form-control" name="password" type="password" placeholder="密码">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-7 col-xs-7">
                                        <input class="form-control" name="validate" type="text" placeholder="验证码">
                                    </div>
                                    <div class="col-sm-5 col-xs-5">
                                        <img class="validate-img" src="validateImage" id="validate-img">
                                    </div>
                                </div>
                                <span class="help-block" id="nologin-helpblock"></span>
                                <div class="form-group">
                                    <div class="col-sm-4 col-xs-6">
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox" name="remember"> 记住我
                                            </label>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 col-sm-offset-3 col-xs-6 forget">
                                        <a href="forgetps.jsp">忘记密码？</a>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <button class="btn btn-block btn-primary" id="nologin-btn" type="button">登录</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="tab-pane" id="login-phone">
                        <div class="col-sm-12 col-xs-12">
                            <form class="form-horizontal">
                                <div class="form-group">
                                    <div class="col-sm-12 col-xs-12">
                                        <input class="form-control" name="phone" type="text" placeholder="手机号">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-6 col-xs-6">
                                        <input class="form-control" name="code" type="text" placeholder="短信验证码">
                                    </div>
                                    <div class="col-sm-6 col-xs-6">
                                        <button class="btn btn-block btn-default" type="button" id="sendCode">发送验证码</button>
                                    </div>
                                </div>
                                <span class="help-block" id="phonelogin-helpblock"></span>
                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <button class="btn btn-block btn-primary" id="phonelogin-btn" type="button">登录</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div class="tab-pane" id="qrcode-login">
                        <div class="col-sm-12 col-xs-12">
                            <div class="qrcode"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<footer class="footer">
    <div class="container">
        <div class="col-md-4 col-xs-3">
            <img class="footer-logo" src="imgs/logo_footer.png">
        </div>
        <div class="col-md-8 col-xs-9">
            <p class="footer-detail"><a href="index.jsp ">"臭皮匠"项目组</a> 版权所有Copyright   2017-2019  All Rights Reserved.</div></p>
    </div>
</footer>

<script type="text/javascript" src="js/jquery-2.2.4.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/rgb.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script type="text/javascript" src="js/jquery.qrcode.js"></script>
<script type="text/javascript" src="js/qrcode.js"></script>
<script type="text/javascript" src="js/login.js"></script>

</body>
</html>

