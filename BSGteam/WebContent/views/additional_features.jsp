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
			<h1>Hello, here are more additional queries...</h1>
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
					<li class="active"><a href="#Venues" data-toggle="tab">Venues
							within Location</a></li>
					<li><a href="#NearVenues" data-toggle="tab">Venues around
							Location</a></li>
					<li><a href="#LocationKeywords" data-toggle="tab">Trending
							discussions</a></li>
					<li><a href="#twitterTimeline" data-toggle="tab">User's Timeline</a></li>

				</ul>
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane fade active in" id="Venues">
						<jsp:include page="venues_in_location.jsp" />
					</div>
					<div class="tab-pane fade" id="LocationKeywords">
						<jsp:include page="keywords_discussion.jsp" />
					</div>
					<div class="tab-pane fade" id="NearVenues">
						<jsp:include page="nearvenues.jsp" />
					</div>
					<div class="tab-pane fade" id="twitterTimeline">
						<jsp:include page="twitterTimeline.jsp" />
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