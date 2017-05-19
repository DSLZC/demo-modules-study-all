<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<jsp:directive.include file="includes/top.jsp" />
  <div id="msg" class="success">
    <h2>
      登录成功
      <input class="btn-submit" value="注销" type="button" onclick="location.href='logout'" style="margin-left: 30px"/>
    </h2>
    <p>您已经成功登录中央认证系统。</p>
    <p>出于安全考虑，一旦您访问过那些需要您提供凭证信息的应用时，请操作完成之后关闭浏览器。</p>
  </div>
<jsp:directive.include file="includes/bottom.jsp" />

