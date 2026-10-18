<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html><html><head>
<%@ include file="/WEB-INF/views/common/head.jspf" %></head><body>
<%@ include file="/WEB-INF/views/common/nav.jspf" %>
<div class="container py-4">
<div class="d-flex justify-content-between align-items-center mb-3 gap-3 flex-wrap"><h2 class="m-0">Reviews and Ratings</h2>
<form class="d-flex gap-2" method="get"><input name="q" value="<c:out value='${q}'/>" class="form-control" placeholder="Search"><button class="btn btn-outline-dark">Search</button></form>
<a class="btn btn-rose" href="${pageContext.request.contextPath}/reviews?action=new">+ Add</a></div>
<div class="panel table-responsive"><table class="table align-middle m-0">
<thead><tr><th>Vendor</th><th>Couple</th><th>Rating</th><th>Vendor average</th><th>Comment</th><th></th></tr></thead>
<c:forEach items="${items}" var="i"><tr><td><c:out value="${i.vendorId}"/></td><td><c:out value="${i.coupleId}"/></td><td><c:out value="${i.rating}"/> / 5</td><td><fmt:formatNumber value="${avg[i.vendorId]}" maxFractionDigits="1"/></td><td><c:out value="${i.comment}"/></td><td class="text-nowrap"><a href="${pageContext.request.contextPath}/reviews?action=edit&id=${i.id}">Edit</a> &nbsp;|&nbsp; <form class="d-inline" method="post" action="${pageContext.request.contextPath}/reviews" onsubmit="return confirm(\'Delete this record?\')"><input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="${i.id}"><button class="btn btn-link text-danger p-0 align-baseline">Delete</button></form></td></tr></c:forEach>
<c:if test="${empty items}"><tr><td colspan="6" class="text-center text-muted py-4">No records found</td></tr></c:if>
</table></div></div></body></html>
