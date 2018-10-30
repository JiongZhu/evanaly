<%--
  Created by IntelliJ IDEA.
  User: wx6_2
  Date: 2017/6/14
  Time: 15:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/taglibs.jsp" %>

<html>
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="/imgs/favicon.ico" type="image/x-icon"/>

    <link href="css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="css/part.css" type="text/css" rel="stylesheet">

    <title>忘记密码</title>
</head>
<body>

<main class="w-main bg height-fluid">

    <div class="container-fluid">
        <div class="container">
            <div class="row">
                <div class="col-md-8 col-xs-12"><a class="login-title" href="index.jsp">基于社会事件的民航客流量分析系统</a></div>
            </div>
            <div class="row">
                <div class="col-md-2 col-xs-3 pull-right"><h4>忘记密码</h4></div>
            </div>
        </div>
        <div class="container">
            <div class="panel panel-default changeps-box">
                <div class="panel-body">
                    <div class="forgetps-form">
                        <form class="form-horizontal" action="forgetPS" method="post" id="forgetps">
                            <div class="show" id="firstStep">
                                <div class="form-group">
                                    <label class="col-sm-3 col-xs-3 control-label">手&nbsp;机&nbsp;号&nbsp;码</label>
                                    <div class="col-sm-9 col-xs-9">
                                        <input class="form-control" name="phone" type="tel" placeholder="手机号码">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 col-xs-3 control-label">短信验证码</label>
                                    <div class="col-sm-5 col-xs-4">
                                        <input class="form-control" id="code" type="text" placeholder="短信验证码">
                                    </div>
                                    <div class="col-sm-4 col-xs-5">
                                        <button class="btn btn-block btn-default" id="sendCode" type="button">发送验证码</button>
                                    </div>
                                </div>
                                <span class="help-block" id="first-helpblock"></span>
                                <div class="form-group">
                                    <div class="col-sm-8 col-sm-offset-2 col-xs-8 col-xs-offset-2">
                                        <button class="btn btn-block btn-primary" id="next" type="button">下一步</button>
                                    </div>
                                </div>
                            </div>
                            <div class="hidden" id="secondStep">
                                <div class="form-group">
                                    <label class="col-sm-3 col-xs-3 control-label">新&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;码</label>
                                    <div class="col-sm-9 col-xs-9">
                                        <input class="form-control" type="password" name="password" placeholder="新密码">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 col-xs-3 control-label">确&nbsp;认&nbsp;密&nbsp;码</label>
                                    <div class="col-sm-9 col-xs-9">
                                        <input class="form-control" type="password" id="confirmps" placeholder="确认密码">
                                    </div>
                                </div>
                                <span class="help-block" id="second-helpblock"></span>
                                <div class="form-group">
                                    <div class="col-sm-6 col-sm-offset-3 col-xs-6 col-xs-offset-3">
                                        <button class="btn btn-block btn-primary" id="confirm" type="submit">立即登录</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<footer class="footer">
    <div class="container">
        <div class="col-md-4 col-xs-4">
            <img class="footer-logo" src="imgs/logo_footer.png">
        </div>
        <div class="col-md-8 col-xs-8">
            <p class="footer-detail"><a href="index.jsp ">"臭皮匠"项目组</a> 版权所有Copyright   2017-2019  All Rights Reserved.</div></p>
    </div>
</footer>

<script type="text/javascript" src="js/jquery-2.2.4.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/rgb.js"></script>
<script type="text/javascript" src="js/forgetps.js"></script>

</body>
</html>

