<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html><head>
<%@ include file="/WEB-INF/views/common/head.jspf" %></head><body>
<%@ include file="/WEB-INF/views/common/nav.jspf" %>
<div class="container py-4" style="max-width:680px"><h2 class="mb-3">Edit Booking</h2>
<form method="post" action="${pageContext.request.contextPath}/bookings" class="panel"><input type="hidden" name="id" value="<c:out value='${item.id}'/>">
<div class="mb-3"><label class="form-label">Couple ID</label><input class="form-control" name="f1" type="text"  required value="<c:out value='${item.coupleId}'/>"></div>
<div class="mb-3"><label class="form-label">Vendor ID</label><input class="form-control" name="f2" type="text"  required value="<c:out value='${item.vendorId}'/>"></div>
<div class="mb-3"><label class="form-label">Wedding date</label><input class="form-control" name="f3" type="date"  required value="<c:out value='${item.date}'/>"></div>
<div class="mb-3"><label class="form-label">Guests</label><input class="form-control" name="f4" type="number" min="1" required value="<c:out value='${item.guests}'/>"></div>
<div class="mb-3"><label class="form-label">Status</label><select class="form-select" name="f6" required><option ${item.status == 'Pending' ? 'selected' : ''}>Pending</option><option ${item.status == 'Confirmed' ? 'selected' : ''}>Confirmed</option><option ${item.status == 'Cancelled' ? 'selected' : ''}>Cancelled</option></select></div>
<button class="btn btn-rose">Save</button> <a class="btn btn-link" href="${pageContext.request.contextPath}/bookings">Cancel</a></form></div></body></html>
