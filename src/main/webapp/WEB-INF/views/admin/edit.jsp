<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html><head>
<%@ include file="/WEB-INF/views/common/head.jspf" %></head><body>
<%@ include file="/WEB-INF/views/common/nav.jspf" %>
<div class="container py-4" style="max-width:680px"><h2 class="mb-3">Edit Admin</h2>
<form method="post" action="${pageContext.request.contextPath}/admins" class="panel"><input type="hidden" name="id" value="<c:out value='${item.id}'/>">
<div class="mb-3"><label class="form-label">Name</label><input class="form-control" name="f1" type="text"  required value="<c:out value='${item.name}'/>"></div>
<div class="mb-3"><label class="form-label">Email</label><input class="form-control" name="f2" type="email"  required value="<c:out value='${item.email}'/>"></div>
<div class="mb-3"><label class="form-label">Password</label><input class="form-control" name="f3" type="password"  required value="<c:out value='${item.password}'/>"></div>
<div class="mb-3"><label class="form-label">Permissions</label><input class="form-control" name="f4" type="text"  required value="<c:out value='${item.permissions}'/>"></div>
<button class="btn btn-rose">Save</button> <a class="btn btn-link" href="${pageContext.request.contextPath}/admins">Cancel</a></form></div></body></html>
