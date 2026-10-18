<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html><head>
<%@ include file="/WEB-INF/views/common/head.jspf" %></head><body class="login-bg">
<div class="auth panel"><div class="text-center mb-2"><img src="${pageContext.request.contextPath}/images/logo.png" alt="Everlove" style="height:96px"></div><h2 class="mb-3 text-center">&hearts; Sign in</h2>
<c:if test="${param.registered == '1'}"><div class="alert alert-success">Account created. Please sign in.</div></c:if>
<c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
<form method="post" action="${pageContext.request.contextPath}/login">
<div class="mb-3"><label class="form-label">Email</label><input class="form-control" type="email" name="email" required></div>
<div class="mb-3"><label class="form-label">Password</label><input class="form-control" type="password" name="password" required></div>
<button class="btn btn-rose w-100">Sign in</button></form>
<p class="mt-3 mb-0 text-center">New couple? <a href="${pageContext.request.contextPath}/register">Create an account</a></p><p class="text-center mt-2 mb-0"><a href="${pageContext.request.contextPath}/site">&larr; Back to website</a></p></div></body></html>
