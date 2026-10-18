<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html><head>
<%@ include file="/WEB-INF/views/common/head.jspf" %></head><body>
<%@ include file="/WEB-INF/views/common/nav.jspf" %>
<div class="container py-4" style="max-width:680px"><h2 class="mb-3">Add Wedding Plan</h2>
<form method="post" action="${pageContext.request.contextPath}/plans" class="panel">
<div class="mb-3"><label class="form-label">Couple ID</label><input class="form-control" name="f1" type="text"  required></div>
<div class="mb-3"><label class="form-label">Wedding date</label><input class="form-control" name="f2" type="date"  required></div>
<div class="mb-3"><label class="form-label">Venue</label><input class="form-control" name="f3" type="text"  required></div>
<div class="mb-3"><label class="form-label">Budget (LKR)</label><input class="form-control" name="f4" type="number" step="any" min="0" required></div>
<div class="mb-3"><label class="form-label">Guests</label><input class="form-control" name="f5" type="number" min="1" required></div>
<button class="btn btn-rose">Save</button> <a class="btn btn-link" href="${pageContext.request.contextPath}/plans">Cancel</a></form></div></body></html>
