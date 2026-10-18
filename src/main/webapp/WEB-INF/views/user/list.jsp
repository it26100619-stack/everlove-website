<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html><head>
<%@ include file="/WEB-INF/views/common/head.jspf" %></head><body>
<%@ include file="/WEB-INF/views/common/nav.jspf" %>
<div class="container py-4">
<div class="d-flex justify-content-between align-items-center mb-3 gap-3 flex-wrap"><h2 class="m-0">Couples</h2>
<form class="d-flex gap-2" method="get"><input name="q" value="<c:out value='${q}'/>" class="form-control" placeholder="Search"><button class="btn btn-outline-dark">Search</button></form>
<a class="btn btn-rose" href="${pageContext.request.contextPath}/users?action=new">+ Add</a></div>
<div class="panel table-responsive"><table class="table align-middle m-0">
<thead><tr><th>Name</th><th>Email</th><th>Phone</th><th>Role</th><th></th></tr></thead>
<c:forEach items="${items}" var="i"><tr><td><c:out value="${i.name}"/></td><td><c:out value="${i.email}"/></td><td><c:out value="${i.phone}"/></td><td><c:out value="${i.role}"/></td><td class="text-nowrap"><a href="${pageContext.request.contextPath}/users?action=edit&id=${i.id}">Edit</a> &nbsp;|&nbsp; <form class="d-inline" method="post" action="${pageContext.request.contextPath}/users" onsubmit="return confirm(\'Delete this record?\')"><input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="${i.id}"><button class="btn btn-link text-danger p-0 align-baseline">Delete</button></form></td></tr></c:forEach>
<c:if test="${empty items}"><tr><td colspan="5" class="text-center text-muted py-4">No records found</td></tr></c:if>
</table></div></div></body></html>
