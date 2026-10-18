<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="Book a vendor | "/><!DOCTYPE html><html><head><%@ include file="/WEB-INF/views/common/head.jspf" %></head><body class="site">
<c:set var="navClass" value="always"/><%@ include file="site-nav.jspf" %>
<section class="pagehead"><div class="container"><div class="stepper"><div class="done">1. Choose vendor</div><div class="on">2. Date and guests</div><div>3. Request sent</div></div><h1>Book <c:out value="${vendor.name}"/></h1></div></section>
<section class="container py-4"><div class="row g-4 mb-5">
<div class="col-lg-7"><div class="box"><c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
<form method="post" action="${ctx}/book"><input type="hidden" name="vendor" value="${vendor.id}">
<div class="mb-3"><label class="form-label">Wedding date</label><input class="form-control form-control-lg" type="date" name="date" id="date" value="<c:out value='${date}'/>" required></div>
<div class="mb-3"><label class="form-label">Number of guests</label><input class="form-control form-control-lg" type="number" min="1" name="guests" id="guests" value="<c:out value='${guests}'/>" required></div>
<button class="btn-main w-100 py-3">Request booking</button><p class="text-muted small mt-3 mb-0"><i class="bi bi-shield-check"></i> No payment now. The vendor confirms your date and you can cancel any time in My bookings.</p></form></div></div>
<div class="col-lg-5"><div class="box"><div class="imgw rounded-3 mb-3"><img class="w-100" style="height:170px;object-fit:cover" src="${ctx}/photo?id=${vendor.id}&cat=${vendor.category.toLowerCase()}" alt=""></div>
<span class="tag"><c:out value="${vendor.category}"/></span><h4 class="mt-2"><c:out value="${vendor.name}"/></h4><p class="text-muted"><i class="bi bi-geo-alt"></i> <c:out value="${vendor.city}"/></p>
<div class="d-flex justify-content-between"><span>Price</span><b>LKR <fmt:formatNumber value="${vendor.basePrice}" groupingUsed="true" maxFractionDigits="0"/> (${vendor.pricing})</b></div><hr>
<div class="d-flex justify-content-between fs-5"><span>Estimated total</span><b id="est" data-price="${vendor.basePrice}" data-per="${vendor.pricing == 'Per guest'}">LKR 0</b></div></div></div></div></section>
<%@ include file="site-foot.jspf" %>
<script>
var d = document.getElementById('date'), g = document.getElementById('guests'), e = document.getElementById('est');
var t = new Date(); t.setDate(t.getDate() + 1); d.min = t.toISOString().slice(0, 10);
function calc() { var p = parseFloat(e.getAttribute('data-price')), n = parseInt(g.value, 10) || 0;
  var total = e.getAttribute('data-per') === 'true' ? p * n : p; e.textContent = 'LKR ' + total.toLocaleString(undefined, {maximumFractionDigits: 0}); }
g.addEventListener('input', calc); calc();
</script></body></html>
