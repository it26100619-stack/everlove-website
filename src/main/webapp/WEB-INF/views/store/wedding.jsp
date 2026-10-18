<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="My wedding | "/><!DOCTYPE html><html><head><%@ include file="/WEB-INF/views/common/head.jspf" %></head><body class="site">
<c:set var="navClass" value="always"/><%@ include file="site-nav.jspf" %>
<section class="pagehead"><div class="container"><p class="eyebrow">MY WEDDING</p><h1>Your wedding planner</h1><p class="text-muted">Save your date, venue and budget. We track how much you have committed to vendors.</p></div></section>
<section class="container py-4 mb-5">
<c:if test="${not empty param.saved}"><div class="alert alert-success">Your wedding details are saved.</div></c:if>
<c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
<div class="row g-4"><div class="col-lg-6"><div class="box"><h4 class="mb-3">Wedding details</h4>
<form method="post" action="${ctx}/my-wedding">
<div class="mb-3"><label class="form-label">Wedding date</label><input class="form-control" type="date" name="date" value="<c:out value='${plan.date}'/>" required></div>
<div class="mb-3"><label class="form-label">Venue</label><input class="form-control" name="venue" value="<c:out value='${plan.venue}'/>" placeholder="Not decided yet"></div>
<div class="mb-3"><label class="form-label">Total budget (LKR)</label><input class="form-control" type="number" min="0" step="any" name="budget" value="<c:out value='${plan.budget}'/>" required></div>
<div class="mb-3"><label class="form-label">Number of guests</label><input class="form-control" type="number" min="1" name="guests" value="<c:out value='${plan.guests}'/>" required></div>
<button class="btn-main w-100 py-3">Save my wedding details</button></form></div></div>
<div class="col-lg-6"><c:choose><c:when test="${not empty plan}">
<div class="box mb-4 text-center"><c:choose><c:when test="${not empty daysLeft and daysLeft >= 0}"><div class="display-3" style="color:#c0587a;font-family:'Playfair Display',serif">${daysLeft}</div><div class="text-muted">days to go</div></c:when><c:otherwise><div class="text-muted">Choose a future date to see your countdown.</div></c:otherwise></c:choose></div>
<div class="box"><h4 class="mb-3">Budget</h4>
<div class="progress mb-3" style="height:14px"><div class="progress-bar" style="width:${pct}%;background:#c0587a"></div></div>
<div class="d-flex justify-content-between"><span>Committed to vendors</span><b>LKR <fmt:formatNumber value="${committed}" groupingUsed="true" maxFractionDigits="0"/></b></div>
<div class="d-flex justify-content-between"><span>Total budget</span><b>LKR <fmt:formatNumber value="${plan.budget}" groupingUsed="true" maxFractionDigits="0"/></b></div><hr>
<div class="d-flex justify-content-between fs-5"><span>Remaining</span><b style="color:${remaining < 0 ? '#c0392b' : '#1d6b35'}">LKR <fmt:formatNumber value="${remaining}" groupingUsed="true" maxFractionDigits="0"/></b></div>
<c:if test="${remaining < 0}"><p class="text-danger small mt-2 mb-0">Your bookings are above your budget.</p></c:if>
<a class="btn-line mt-3" href="${ctx}/catalog">Find more vendors</a></div></c:when>
<c:otherwise><div class="box text-center text-muted py-5"><i class="bi bi-heart fs-1"></i><p class="mt-2 mb-0">Fill in your details to start your countdown and budget tracker.</p></div></c:otherwise></c:choose></div></div></section>
<%@ include file="site-foot.jspf" %></body></html>
