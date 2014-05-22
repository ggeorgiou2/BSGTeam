<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="header.jsp"%>
<script>
	var url = document.location.toString();
	$(window).load(function() {
		$('#mytab a[href=#' + url.split('#')[1] + ']').tab('show');
	});
</script>
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-8 col-md-10 col-md-push-2">
			<h1>Hello, query more APIs like Flickr ...</h1>
			<c:if test="${not empty error}">
				<div class="alert alert-danger" align="center">
					<strong><c:out value="${error}"></c:out></strong>
				</div>
			</c:if>
			<c:if test="${not empty success}">
				<div class="alert alert-success" align="center">
					<strong><c:out value="${success}"></c:out></strong>
				</div>
			</c:if>
			<div class="bs-component">
				<ul class="nav nav-pills nav-justified"
					style="margin-top: 20px; margin-bottom: 5px;" id="mytab">
					<li class="active"><a href="#Geotagged" data-toggle="tab">Search for GeoTagged Photos</a></li>
					<li><a href="#Photos" data-toggle="tab">Search for pictures</a></li>		
				</ul>
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane fade  active in" id="Geotagged">
						<jsp:include page="flickr_location.jsp" />
					</div>
					<div class="tab-pane fade" id="Photos">
						<jsp:include page="flickr_tags.jsp" />
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<footer>
	<%@ include file="footer.html"%>
</footer>
</body>
</html>