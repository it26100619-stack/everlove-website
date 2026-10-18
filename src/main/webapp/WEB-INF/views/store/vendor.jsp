<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="Vendor | "/><!DOCTYPE html><html><head><%@ include file="/WEB-INF/views/common/head.jspf" %></head><body class="site">
<c:set var="navClass" value="always"/><%@ include file="site-nav.jspf" %>
<section class="pagehead"><div class="container"><a class="small plain" href="${ctx}/catalog">&larr; All vendors</a>
<h1 class="mt-2"><c:out value="${vendor.name}"/></h1>
<div class="text-muted"><span class="tag"><c:out value="${vendor.category}"/></span> &nbsp; <i class="bi bi-geo-alt"></i> <c:out value="${vendor.city}"/>
<c:if test="${not empty avg[vendor.id]}"> &nbsp; <span class="star">&#9733;</span> <fmt:formatNumber value="${avg[vendor.id]}" maxFractionDigits="1"/> (${cnt[vendor.id]} reviews)</c:if></div></div></section>
<section class="container py-4"><div class="row g-4 mb-5">
<div class="col-lg-8"><div class="imgw rounded-4 mb-4"><img class="w-100" style="height:380px;object-fit:cover" src="${ctx}/photo?id=${vendor.id}&cat=${vendor.category.toLowerCase()}" alt=""></div>
<h4>About</h4><p><c:out value="${about}"/></p>
<h4 class="mt-4">Reviews</h4>
<c:forEach items="${reviewList}" var="r"><div class="box mb-3"><div class="star">${r[0]}</div><p class="mb-1">&ldquo;<c:out value="${r[1]}"/>&rdquo;</p><small class="text-muted"><c:out value="${r[2]}"/></small></div></c:forEach>
<c:if test="${empty reviewList}"><p class="text-muted">No reviews yet. Couples can review a vendor after their booking is confirmed.</p></c:if></div>
<div class="col-lg-4"><div class="box" style="position:sticky;top:90px"><small class="text-muted">From</small>
<div class="fs-3 fw-semibold">LKR <fmt:formatNumber value="${vendor.basePrice}" groupingUsed="true" maxFractionDigits="0"/></div>
<p class="text-muted">${vendor.pricing}<c:if test="${vendor.pricing == 'Per guest'}"> (total depends on your guest count)</c:if></p>
<a class="btn-main w-100 text-center py-3" href="${ctx}/book?vendor=${vendor.id}">Book this vendor</a>
<p class="text-muted small mt-3 mb-0"><i class="bi bi-shield-check"></i> No payment now. You can cancel any time in My bookings.</p></div></div></div></section>
<%@ include file="site-foot.jspf" %></body></html>
