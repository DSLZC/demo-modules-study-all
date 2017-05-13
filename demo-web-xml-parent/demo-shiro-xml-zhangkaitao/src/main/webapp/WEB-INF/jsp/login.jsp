<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="renderer" content="webkit|ie-comp|ie-stand" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />

    <link href="${pageContext.request.contextPath}/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/static/h-ui/css/iconfont.css" rel="stylesheet" type="text/css" />

    <title>shiro demo 登录</title>
</head>
<body>
<div class="header"></div>
<div class="loginWraper">
    <div id="loginform" class="loginBox">
        <form class="form form-horizontal" action="login" method="post" id="shiro_login_form">
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
                <div class="formControls col-xs-8">
                    <input id="username" name="username" type="text" placeholder="账户" class="input-text size-L" style="width: 65%"
                           datatype="s2-20" nullmsg="请输入您的账户！" errormsg="账户2-20个字符！"/>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
                <div class="formControls col-xs-8">
                    <input id="password" name="password" type="password" placeholder="密码" class="input-text size-L" style="width: 65%"
                           datatype="*3-18" nullmsg="请输入密码！" errormsg="密码3-18个字符！" />
                </div>
            </div>

            <div class="row cl">
              <div class="formControls col-xs-8 col-xs-offset-3">
                <input class="input-text size-L" type="text" placeholder="验证码" name="captchaCode" style="width:100px;">
                <img src="/captcha/generate" id="jcaptcha_img" onclick="get_captcha_img();"> <a id="kanbuq" href="javascript:get_captcha_img();">换一张</a> </div>
            </div>

            <div class="row cl">
              <div class="formControls col-xs-8 col-xs-offset-3">
                  <label for="online">
                    <input type="checkbox" name="rememberMe" id="online" value="1"/>&nbsp;记住我的登录状态
                  </label>
              </div>
            </div>

            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <input type="submit" class="btn btn-success radius size-L" id="submit_btn" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;"/>
                    <input type="reset" class="btn btn-default radius size-L ml-20" value="&nbsp;重&nbsp;&nbsp;&nbsp;&nbsp;置&nbsp;"/>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="footer">Copyright xxxxxxxxxxxxxx by dongsilin</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/validateform-5.3.2/Validform.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/layer/2.1/layer.js"></script>

<script type="text/javascript">
    $(function () {
        var loading_index;
        var validform_obj = $("#shiro_login_form").Validform({
            ajaxPost: true,
            tiptype: 4,
            btnSubmit: "#submit_btn",
            postonce: true,// 防止重复提交
            beforeSubmit: function(curform){
                loading_index = layer.load();
            },
            callback: function(data){
                validform_obj.resetStatus();// 重置postonce
                layer.close(loading_index);
                if (data.success) {
                    layer.msg('登录成功', {icon: 6, time: 1500}, function(){
                        location.href = data.body;
                    });
                } else {
                    get_captcha_img();
                    layer.msg(data.message, {time: 3000});
                }
            }
        });
    });
    function get_captcha_img() {
        document.getElementById('jcaptcha_img').src = '/captcha/generate?t='+ new Date().getTime();
    }
</script>
</body>
</html>