<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html><head>
<%@ include file="/WEB-INF/views/common/head.jspf" %></head><body>
<%@ include file="/WEB-INF/views/common/nav.jspf" %>
<div class="container py-4" style="max-width:680px"><h2 class="mb-3">Add Review</h2>
<form method="post" action="${pageContext.request.contextPath}/reviews" class="panel">
<div class="mb-3"><label class="form-label">Vendor ID</label><input class="form-control" name="f1" type="text"  required></div>
<div class="mb-3"><label class="form-label">Couple ID</label><input class="form-control" name="f2" type="text"  required></div>
<div class="mb-3"><label class="form-label">Rating (1 to 5)</label><input class="form-control" name="f3" type="number" min="1" max="5" required></div>
<div class="mb-3"><label class="form-label">Comment</label><input class="form-control" name="f4" type="text"  required></div>
<button class="btn btn-rose">Save</button> <a class="btn btn-link" href="${pageContext.request.contextPath}/reviews">Cancel</a></form></div></body></html>
