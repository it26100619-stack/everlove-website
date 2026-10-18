<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html><head>
<%@ include file="/WEB-INF/views/common/head.jspf" %></head><body>
<%@ include file="/WEB-INF/views/common/nav.jspf" %>
<div class="container py-4" style="max-width:680px"><h2 class="mb-3">Add Vendor</h2>
<form method="post" action="${pageContext.request.contextPath}/vendors" class="panel">
<div class="mb-3"><label class="form-label">Name</label><input class="form-control" name="f1" type="text"  required></div>
<div class="mb-3"><label class="form-label">Category</label><select class="form-select" name="f2" required><option>Photographer</option><option>Caterer</option><option>Florist</option><option>Venue</option><option>DJ</option><option>Decorator</option></select></div>
<div class="mb-3"><label class="form-label">City</label><input class="form-control" name="f3" type="text"  required></div>
<div class="mb-3"><label class="form-label">Base price (LKR)</label><input class="form-control" name="f4" type="number" step="any" min="0" required></div>
<button class="btn btn-rose">Save</button> <a class="btn btn-link" href="${pageContext.request.contextPath}/vendors">Cancel</a></form></div></body></html>
