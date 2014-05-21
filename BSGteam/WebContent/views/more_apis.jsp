<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
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
					<li class="active"><a href="#Discussion" data-toggle="tab">Search
							for GeoTagged Photos</a></li>
					<li><a href="#TrackUsers" data-toggle="tab">Search for
							photo</a></li>
				</ul>
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane fade active in" id="Discussion">
						<jsp:include page="flicker_results.jsp" />
					</div>
					<div class="tab-pane fade" id="TrackUsers">
						<jsp:include page="flicker_search.jsp" />
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