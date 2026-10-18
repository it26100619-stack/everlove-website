<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="Vendors | "/><!DOCTYPE html><html><head><%@ include file="/WEB-INF/views/common/head.jspf" %></head><body class="site">
<c:set var="navClass" value="always"/><%@ include file="site-nav.jspf" %>
<section class="pagehead"><div class="container"><p class="eyebrow">STEP 1</p><h1>Choose your vendor</h1><p class="text-muted">Pick a category or search, then press <b>Book now</b> on the vendor you like.</p></div></section>
<section class="container py-4">
<div class="d-flex flex-wrap gap-2 align-items-center justify-content-between mb-4">
<div class="d-flex flex-wrap gap-2"><a class="pill ${empty category ? 'on' : ''}" href="${ctx}/catalog">All</a>
<c:forEach items="${['Photographer','Caterer','Florist','Venue','DJ','Decorator']}" var="n"><a class="pill ${category == n ? 'on' : ''}" href="${ctx}/catalog?category=${n}">${n}</a></c:forEach></div>
<form class="d-flex gap-2" method="get"><input type="hidden" name="category" value="<c:out value='${category}'/>"><input class="form-control" name="q" value="<c:out value='${q}'/>" placeholder="Search vendors or city"><button class="btn-main">Search</button></form></div>
<div class="row g-4 mb-5"><c:forEach items="${items}" var="i"><div class="col-md-6 col-lg-4 rv"><div class="vcard"><div class="imgw"><img src="${ctx}/photo?id=${i.id}&cat=${i.category.toLowerCase()}" alt=""></div>
<div class="p-3"><span class="tag"><c:out value="${i.category}"/></span>
<h5 class="mt-2 mb-1"><a class="plain" href="${ctx}/vendor?id=${i.id}"><c:out value="${i.name}"/></a></h5>
<div class="text-muted small mb-2"><i class="bi bi-geo-alt"></i> <c:out value="${i.city}"/>
<c:if test="${not empty avg[i.id]}"> &nbsp; <span class="star">&#9733;</span> <fmt:formatNumber value="${avg[i.id]}" maxFractionDigits="1"/> (${cnt[i.id]})</c:if></div>
<div class="d-flex justify-content-between align-items-center"><div><small class="text-muted">From</small><div class="fw-semibold">LKR <fmt:formatNumber value="${i.basePrice}" groupingUsed="true" maxFractionDigits="0"/> <small class="text-muted fw-normal">${i.pricing}</small></div></div>
<a class="btn-main btn-sm-main" href="${ctx}/book?vendor=${i.id}">Book now</a></div></div></div></div>
</c:forEach></div>
<c:if test="${empty items}"><div class="text-center text-muted py-5"><i class="bi bi-emoji-frown fs-1"></i><p>No vendors found. Try another category or search.</p></div></c:if></section>
<%@ include file="site-foot.jspf" %></body></html>
