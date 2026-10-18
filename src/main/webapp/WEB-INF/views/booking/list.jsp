<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html><html><head>
<%@ include file="/WEB-INF/views/common/head.jspf" %></head><body>
<%@ include file="/WEB-INF/views/common/nav.jspf" %>
<div class="container py-4">
<div class="d-flex justify-content-between align-items-center mb-3 gap-3 flex-wrap"><h2 class="m-0">Bookings</h2>
<form class="d-flex gap-2" method="get"><input name="q" value="<c:out value='${q}'/>" class="form-control" placeholder="Search"><button class="btn btn-outline-dark">Search</button></form>
<a class="btn btn-rose" href="${pageContext.request.contextPath}/bookings?action=new">+ Add</a></div>
<div class="panel table-responsive"><table class="table align-middle m-0">
<thead><tr><th>Couple</th><th>Vendor</th><th>Date</th><th>Guests</th><th>Total (LKR)</th><th>Status</th><th></th></tr></thead>
<c:forEach items="${items}" var="i"><tr><td><c:out value="${i.coupleId}"/></td><td><c:out value="${i.vendorId}"/></td><td><c:out value="${i.date}"/></td><td><c:out value="${i.guests}"/></td><td><fmt:formatNumber value="${i.total}" groupingUsed="true" minFractionDigits="2"/></td><td><span class="badge-s badge-${i.status}"><c:out value="${i.status}"/></span></td><td class="text-nowrap"><form class="d-inline" method="post" action="${pageContext.request.contextPath}/bookings"><input type="hidden" name="action" value="status"><input type="hidden" name="id" value="${i.id}"><input type="hidden" name="value" value="Confirmed"><button class="btn btn-link p-0 align-baseline">Confirm</button></form> &nbsp;|&nbsp; <form class="d-inline" method="post" action="${pageContext.request.contextPath}/bookings"><input type="hidden" name="action" value="status"><input type="hidden" name="id" value="${i.id}"><input type="hidden" name="value" value="Cancelled"><button class="btn btn-link p-0 align-baseline">Cancel</button></form> &nbsp;|&nbsp; <a href="${pageContext.request.contextPath}/bookings?action=edit&id=${i.id}">Edit</a> &nbsp;|&nbsp; <form class="d-inline" method="post" action="${pageContext.request.contextPath}/bookings" onsubmit="return confirm(\'Delete this record?\')"><input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="${i.id}"><button class="btn btn-link text-danger p-0 align-baseline">Delete</button></form></td></tr></c:forEach>
<c:if test="${empty items}"><tr><td colspan="7" class="text-center text-muted py-4">No records found</td></tr></c:if>
</table></div></div></body></html>
