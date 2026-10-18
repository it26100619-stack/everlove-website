<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html><head>
<%@ include file="/WEB-INF/views/common/head.jspf" %></head><body>
<%@ include file="/WEB-INF/views/common/nav.jspf" %>
<div class="container py-4" style="max-width:680px"><h2 class="mb-3">Edit Vendor</h2>
<form method="post" action="${pageContext.request.contextPath}/vendors" class="panel"><input type="hidden" name="id" value="<c:out value='${item.id}'/>">
<div class="mb-3"><label class="form-label">Name</label><input class="form-control" name="f1" type="text"  required value="<c:out value='${item.name}'/>"></div>
<div class="mb-3"><label class="form-label">Category</label><select class="form-select" name="f2" required><option ${item.category == 'Photographer' ? 'selected' : ''}>Photographer</option><option ${item.category == 'Caterer' ? 'selected' : ''}>Caterer</option><option ${item.category == 'Florist' ? 'selected' : ''}>Florist</option><option ${item.category == 'Venue' ? 'selected' : ''}>Venue</option><option ${item.category == 'DJ' ? 'selected' : ''}>DJ</option><option ${item.category == 'Decorator' ? 'selected' : ''}>Decorator</option></select></div>
<div class="mb-3"><label class="form-label">City</label><input class="form-control" name="f3" type="text"  required value="<c:out value='${item.city}'/>"></div>
<div class="mb-3"><label class="form-label">Base price (LKR)</label><input class="form-control" name="f4" type="number" step="any" min="0" required value="<c:out value='${item.basePrice}'/>"></div>
<button class="btn btn-rose">Save</button> <a class="btn btn-link" href="${pageContext.request.contextPath}/vendors">Cancel</a></form>
<form method="post" action="${ctx}/upload-photo" enctype="multipart/form-data" class="panel mt-3">
<h5>Vendor photo</h5>
<img class="d-block mb-3" style="height:150px;border-radius:12px" src="${ctx}/photo?id=${item.id}&cat=${item.category.toLowerCase()}" alt="">
<input type="hidden" name="id" value="<c:out value='${item.id}'/>">
<input class="form-control mb-3" type="file" name="photo" accept="image/png,image/jpeg,image/webp" required>
<button class="btn btn-rose">Upload photo</button> <small class="text-muted">JPG, PNG or WEBP, up to 5 MB</small></form></div></body></html>
