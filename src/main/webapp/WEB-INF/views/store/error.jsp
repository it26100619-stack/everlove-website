<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="Oops | "/><!DOCTYPE html><html><head><%@ include file="/WEB-INF/views/common/head.jspf" %></head><body class="site">
<c:set var="navClass" value="always"/><%@ include file="site-nav.jspf" %>
<section class="pagehead" style="min-height:75vh"><div class="container text-center py-5"><h1 style="font-size:5rem">${pageContext.errorData.statusCode}</h1>
<h3>We could not show that page</h3><p class="text-muted">The page may have moved, or something went wrong on our side. Please try again.</p><a class="btn-main" href="${ctx}/site">Back to home</a></div></section>
<%@ include file="site-foot.jspf" %></body></html>
