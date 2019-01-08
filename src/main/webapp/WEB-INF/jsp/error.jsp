<%@ include file="/WEB-INF/jsp/common/include_taglibs.jsp"%>
<%@ page isErrorPage="true" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%--============================================================================
说明：当处理请求发生错误时显示的错误页面
============================================================================--%>
<html>
<head>
  <%@ include file="/WEB-INF/jsp/include/meta.jsp"%>
  <title>mcss4dpm：错误提示页面</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="/styles/default.css" type="text/css" >

</head>
<body>
<div align="center" class="error">
    <p/>
    <p/>
    <p/>
    <p/>
    <c:if test="${not empty webError} ">
        错误码：${webError.errorCode}
        <br>
    </c:if>
    <div id="detail" align="left" style="border:1px dashed gray;padding:10px;margin:10px">
    <pre>
      <c:choose>
          <c:when test="${not empty webError} ">
              错误内容：${webError.errorMessage}
              <br>
          </c:when>
          <c:otherwise>
              错误内容：${stackTrace}
          </c:otherwise>
      </c:choose>
    </pre>
    </div>
</div>

</body>
</html>
