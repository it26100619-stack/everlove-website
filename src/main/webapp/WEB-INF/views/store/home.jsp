<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html><html><head><%@ include file="/WEB-INF/views/common/head.jspf" %></head><body class="site">
<%@ include file="site-nav.jspf" %>
<section class="hero-s"><div class="container"><div class="row"><div class="col-lg-6">
<p class="eyebrow hero-in">WEDDING PLANNING MADE SIMPLE</p>
<h1 class="hero-in" style="animation-delay:.15s">Plan your dream wedding. Book trusted vendors in minutes.</h1>
<p class="lead hero-in" style="animation-delay:.3s">Browse photographers, caterers, florists, venues, DJs and decorators. Pick your date, send a booking request and track everything in one place.</p>
<div class="hero-in" style="animation-delay:.45s"><a class="btn-main me-2" href="${ctx}/catalog">Browse vendors</a><a class="btn-line" href="#how">How it works</a></div>
</div></div></div></section>
<section class="strip"><div class="container"><div class="row text-center g-3">
<div class="col-4"><div class="big" data-count="${nVendors}">0</div>Trusted vendors</div>
<div class="col-4"><div class="big" data-count="${nBookings}">0</div>Bookings made</div>
<div class="col-4"><div class="big" data-count="6">0</div>Service categories</div></div></div></section>
<section class="sec" id="how"><div class="container"><div class="text-center mb-5 rv"><p class="eyebrow">HOW IT WORKS</p><h2>Book your wedding team in 3 easy steps</h2></div>
<div class="row g-4">
<div class="col-md-4 rv"><div class="stp"><div class="ico"><i class="bi bi-search"></i></div><h4>1. Choose a vendor</h4><p>Open <b>Vendors</b>, filter by category or search by name, and pick the one you like.</p></div></div>
<div class="col-md-4 rv" style="transition-delay:.15s"><div class="stp"><div class="ico"><i class="bi bi-calendar-check"></i></div><h4>2. Pick date and guests</h4><p>Click <b>Book now</b>, choose your wedding date and number of guests. You see the price straight away.</p></div></div>
<div class="col-md-4 rv" style="transition-delay:.3s"><div class="stp"><div class="ico"><i class="bi bi-patch-check"></i></div><h4>3. Send your request</h4><p>Press <b>Request booking</b>. Track the status (Pending, Confirmed) in <b>My bookings</b>. No payment is taken online.</p></div></div>
</div></div></section>
<section class="sec" style="background:var(--blush)"><div class="container"><div class="text-center mb-5 rv"><p class="eyebrow">CATEGORIES</p><h2>Everything for your big day</h2></div>
<div class="row g-3">
<c:forEach items="${['Photographer','Caterer','Florist','Venue','DJ','Decorator']}" var="n" varStatus="s"><div class="col-6 col-md-4 col-lg-2 rv" style="transition-delay:${s.index * 0.08}s"><a class="ctile" href="${ctx}/catalog?category=${n}"><img src="${ctx}/images/cat-${n.toLowerCase()}.svg" alt=""><span>${n}</span></a></div></c:forEach>
</div></div></section>
<section class="sec"><div class="container"><div class="d-flex justify-content-between align-items-end mb-4 rv"><div><p class="eyebrow">FEATURED</p><h2 class="m-0">Popular vendors</h2></div><a class="btn-line" href="${ctx}/catalog">See all</a></div>
<div class="row g-4"><c:forEach items="${featured}" var="i"><div class="col-md-6 col-lg-4 rv"><div class="vcard"><div class="imgw"><img src="${ctx}/photo?id=${i.id}&cat=${i.category.toLowerCase()}" alt=""></div>
<div class="p-3"><span class="tag"><c:out value="${i.category}"/></span>
<h5 class="mt-2 mb-1"><a class="plain" href="${ctx}/vendor?id=${i.id}"><c:out value="${i.name}"/></a></h5>
<div class="text-muted small mb-2"><i class="bi bi-geo-alt"></i> <c:out value="${i.city}"/>
<c:if test="${not empty avg[i.id]}"> &nbsp; <span class="star">&#9733;</span> <fmt:formatNumber value="${avg[i.id]}" maxFractionDigits="1"/> (${cnt[i.id]})</c:if></div>
<div class="d-flex justify-content-between align-items-center"><div><small class="text-muted">From</small><div class="fw-semibold">LKR <fmt:formatNumber value="${i.basePrice}" groupingUsed="true" maxFractionDigits="0"/> <small class="text-muted fw-normal">${i.pricing}</small></div></div>
<a class="btn-main btn-sm-main" href="${ctx}/book?vendor=${i.id}">Book now</a></div></div></div></div>
</c:forEach></div></div></section>
<section class="sec" style="background:var(--blush)"><div class="container"><div class="text-center mb-5 rv"><p class="eyebrow">LOVE NOTES</p><h2>What couples say</h2></div>
<div class="row g-4"><c:forEach items="${quotes}" var="q" varStatus="s"><div class="col-md-4 rv" style="transition-delay:${s.index * 0.15}s"><div class="tcard"><div class="star">${q[1]}</div><p>&ldquo;<c:out value="${q[0]}"/>&rdquo;</p><b><c:out value="${q[2]}"/></b><div class="small text-muted"><c:out value="${q[3]}"/></div></div></div></c:forEach></div></div></section>
<section class="cta"><div class="container text-center rv"><h2 class="text-white">Ready to start planning?</h2><p class="text-white-50">Create a free account and send your first booking request today.</p>
<c:choose><c:when test="${empty sessionScope.user}"><a class="btn-main" href="${ctx}/register">Create free account</a></c:when><c:otherwise><a class="btn-main" href="${ctx}/catalog">Browse vendors</a></c:otherwise></c:choose></div></section>
<%@ include file="site-foot.jspf" %></body></html>
