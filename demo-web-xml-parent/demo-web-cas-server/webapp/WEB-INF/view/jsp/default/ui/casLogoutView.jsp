<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<jsp:directive.include file="includes/top.jsp" />
  <div id="msg" class="success">
    <h2>
      注销成功
      <input class="btn-submit" value="登录页面" type="button" onclick="location.href='login'" style="margin-left: 30px"/>
    </h2>
    <p>您已经成功退出CAS系统，谢谢使用！</p>
    <p>出于安全考虑，请关闭您的浏览器。</p>
  </div>
<jsp:directive.include file="includes/bottom.jsp" />