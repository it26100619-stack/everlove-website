<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="My bookings | "/><!DOCTYPE html><html><head><%@ include file="/WEB-INF/views/common/head.jspf" %></head><body class="site">
<c:set var="navClass" value="always"/><%@ include file="site-nav.jspf" %>
<section class="pagehead"><div class="container"><div class="stepper"><div class="done">1. Choose vendor</div><div class="done">2. Date and guests</div><div class="${empty param.ref ? '' : 'on'}">3. Request sent</div></div><h1>My bookings</h1></div></section>
<section class="container py-4 mb-5">
<c:if test="${not empty param.ref}"><div class="alert alert-success"><b>Booking request sent!</b> Your reference is <b><c:out value="${param.ref}"/></b>. The vendor will confirm your date soon. The status changes from Pending to Confirmed.</div></c:if>
<c:if test="${not empty param.reviewed}"><div class="alert alert-success">Thank you! Your review has been saved.</div></c:if>
<div class="box table-responsive"><table class="table align-middle m-0"><thead><tr><th>Ref</th><th>Vendor</th><th>Wedding date</th><th>Guests</th><th>Total (LKR)</th><th>Status</th><th></th></tr></thead>
<c:forEach items="${rows}" var="r"><tr><td><c:out value="${r[0]}"/></td><td><c:out value="${r[1]}"/></td><td>${r[2]}</td><td>${r[3]}</td><td>${r[4]}</td>
<td><span class="badge-s badge-${r[5]}">${r[5]}</span></td>
<td><c:if test="${r[5] != 'Cancelled'}"><form method="post" action="${ctx}/my-bookings" onsubmit="return confirm('Cancel this booking?')"><input type="hidden" name="id" value="${r[0]}"><button class="btn btn-sm btn-outline-danger">Cancel</button></form></c:if>
<c:if test="${r[5] == 'Confirmed'}"><details class="mt-2"><summary class="small" style="cursor:pointer">${r[7] == 'yes' ? 'Edit your review' : 'Write a review'}</summary>
<form method="post" action="${ctx}/my-bookings" class="mt-2 d-flex gap-2 flex-wrap"><input type="hidden" name="action" value="review"><input type="hidden" name="vendor" value="${r[6]}">
<select class="form-select form-select-sm w-auto" name="rating"><option value="5">5 stars</option><option value="4">4 stars</option><option value="3">3 stars</option><option value="2">2 stars</option><option value="1">1 star</option></select>
<input class="form-control form-control-sm" style="min-width:220px" name="comment" placeholder="Share your experience" required><button class="btn btn-sm btn-main">Send</button></form></details></c:if></td></tr></c:forEach></table>
<c:if test="${empty rows}"><div class="text-center text-muted py-5"><i class="bi bi-calendar-heart fs-1"></i><p>You have no bookings yet.</p><a class="btn-main" href="${ctx}/catalog">Browse vendors</a></div></c:if></div></section>
<%@ include file="site-foot.jspf" %></body></html>
