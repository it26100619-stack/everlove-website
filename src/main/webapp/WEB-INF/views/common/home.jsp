<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html><head>
<%@ include file="/WEB-INF/views/common/head.jspf" %></head><body>
<%@ include file="/WEB-INF/views/common/nav.jspf" %>
<div class="hero"><div class="container"><h1>Wedding Planning and Vendor Booking</h1>
<p>Welcome, <c:out value="${sessionScope.user.name}"/> (${sessionScope.user.role})</p></div></div>
<div class="container py-4">
<c:if test="${param.denied == '1'}"><div class="alert alert-warning">That page is for admins only.</div></c:if>
<div class="row g-3 mb-4">
<div class="col-md-4"><div class="tile"><div class="num">${nBookings}</div>Bookings worth LKR ${revenue}</div></div>
<div class="col-md-4"><div class="tile"><div class="num">${nVendors}</div>Vendors listed</div></div>
<div class="col-md-4"><div class="tile"><div class="num">${nPlans}</div>Wedding plans</div></div></div>
<div class="panel mb-4"><div class="d-flex justify-content-between align-items-center"><h4 class="m-0">Pending bookings (${pending.size()})</h4><a href="${ctx}/bookings">All bookings</a></div>
<c:if test="${empty pending}"><p class="text-muted mt-3 mb-0">Nothing is waiting. All booking requests have been handled.</p></c:if>
<c:if test="${not empty pending}"><div class="table-responsive mt-3"><table class="table align-middle m-0"><thead><tr><th>Ref</th><th>Vendor</th><th>Wedding date</th><th>Guests</th><th>Total (LKR)</th><th></th></tr></thead>
<c:forEach items="${pending}" var="r"><tr><td>${r[0]}</td><td><c:out value="${r[1]}"/></td><td>${r[2]}</td><td>${r[3]}</td><td>${r[4]}</td><td class="text-nowrap"><form class="d-inline" method="post" action="${ctx}/bookings"><input type="hidden" name="action" value="status"><input type="hidden" name="back" value="home"><input type="hidden" name="id" value="${r[0]}"><input type="hidden" name="value" value="Confirmed"><button class="btn btn-sm btn-rose">Confirm</button></form> <form class="d-inline" method="post" action="${ctx}/bookings"><input type="hidden" name="action" value="status"><input type="hidden" name="back" value="home"><input type="hidden" name="id" value="${r[0]}"><input type="hidden" name="value" value="Cancelled"><button class="btn btn-sm btn-outline-danger">Decline</button></form></td></tr></c:forEach></table></div></c:if></div>
<div class="row g-3">
<div class="col-md-4"><a class="tile" href="${ctx}/vendors"><h3>Vendors</h3>Photographers, caterers, florists and more</a></div>
<div class="col-md-4"><a class="tile" href="${ctx}/bookings"><h3>Bookings</h3>Book vendors with automatic pricing</a></div>
<div class="col-md-4"><a class="tile" href="${ctx}/plans"><h3>Wedding Plans</h3>Date, venue, budget and guests</a></div>
<div class="col-md-4"><a class="tile" href="${ctx}/reviews"><h3>Reviews (${nReviews})</h3>Rate and review vendors</a></div>
<c:if test="${sessionScope.user.admin}">
<div class="col-md-4"><a class="tile" href="${ctx}/users"><h3>Couples (${nUsers})</h3>Manage couple accounts</a></div>
<div class="col-md-4"><a class="tile" href="${ctx}/admins"><h3>Admins (${nAdmins})</h3>Admin accounts and permissions</a></div></c:if>
</div></div>
<script>
var nums = document.querySelectorAll('.num');
for (var k = 0; k < nums.length; k++) {
  (function (el) {
    var n = parseInt(el.textContent, 10), i = 0;
    if (isNaN(n) || n < 1) return;
    el.textContent = '0';
    var t = setInterval(function () { i++; el.textContent = i; if (i >= n) clearInterval(t); }, Math.max(30, 600 / n));
  })(nums[k]);
}
</script></body></html>
