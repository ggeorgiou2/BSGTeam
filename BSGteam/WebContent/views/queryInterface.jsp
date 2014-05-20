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
			<script type="text/javascript">
				$(function() {
					$('#twitterToken').on('submit', function(e) {
						$('#loading_bar').show();
						$.ajax({
							type : 'post',
							url : 'twitterToken',
							data : $('#twitterToken').serialize(),
							success : function(responseJson) {
								if (responseJson == "success") {
									//     $.each(responseJson, function(index, item) { 
									//  	 $("#tokenResults").append(item);
									// });

									$('#alert-success').show();
									$('#loading_bar').hide();
									//$('#twitterToken').hide();
									// $('#foursquareToken').show();
									location.reload();

								} else if (responseJson == "error") {
									//  $.each(responseJson, function(index, item) {
									//	 $("#tokenResults").append(item);
									// });
									$('#alert-danger').show();
									$('#loading_bar').hide();
								}

							}
						});
						e.preventDefault();
					});
				});
			</script>
			<script>
				function onLoad() {
					$('#loading_bar').hide();
					$('#results_table').hide();
					$('#alert-success').hide();
					$('#alert-danger').hide();
					// $('#foursquareToken').hide();
				}
				window.onload = onLoad;
			</script>
			<img src="images/ajax-loader.gif" id="loading_bar" align="middle">

			<div class="alert alert-danger" id="alert-danger" align="center">
				<strong><c:out value="error"></c:out></strong>
			</div>

			<div class="alert alert-success" id="alert-success" align="center">
				<strong><c:out value="success"></c:out></strong>
			</div>
			<%
				if ((session.getAttribute("twitterToken") != null)
						&& (session.getAttribute("foursquareToken") != null)) {
			%>

			<h1>Hello! Query the social web as much as you like...</h1>
			<div class="bs-component">
				<ul class="nav nav-pills nav-justified"
					style="margin-top: 20px; margin-bottom: 5px;" id="mytab">
					<li class="active"><a href="#Discussion" data-toggle="tab">Public
							Discussions</a></li>
					<li><a href="#TrackUsers" data-toggle="tab">Track Multiple Users</a></li>
					<li><a href="#UserVisits" data-toggle="tab">User Visits</a></li>
					<li><a href="#VenueVisits" data-toggle="tab">Venue Visits</a></li>
					<li><a href="#Venues" data-toggle="tab">Venues within Location</a></li>
					<li><a href="#NearVenues" data-toggle="tab">Venues around Location</a></li>
					<li><a href="#LocationKeywords" data-toggle="tab">Trending
							discussions</a></li>
				</ul>
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane fade active in" id="Discussion">
						<jsp:include page="public_discussion.jsp" />
					</div>
					<div class="tab-pane fade" id="Venues">
						<jsp:include page="venues_in_location.jsp" />
					</div>
					<div class="tab-pane fade" id="LocationKeywords">
						<jsp:include page="keywords_discussion.jsp" />
					</div>
					<div class="tab-pane fade" id="NearVenues">
						<jsp:include page="nearvenues.jsp" />
					</div>
					<div class="tab-pane fade" id="UserVisits">
						<jsp:include page="userVisits.jsp" />
					</div>
					<div class="tab-pane fade" id="VenueVisits">
						<jsp:include page="venueVisits.jsp" />
					</div>
					<div class="tab-pane fade" id="TrackUsers">
						<jsp:include page="multipleUsersDiscuss.jsp" />
					</div>
				</div>
			</div>
			<%
				}
			%>
			<%
				if (session.getAttribute("twitterToken") == null) {
			%>
			<jsp:include page="twitterToken.jsp" />
			<%
				}
			%>
			<%
				if ((session.getAttribute("twitterToken") != null)
						&& (session.getAttribute("foursquareToken") == null)) {
			%>
			<jsp:include page="foursquareToken.jsp" />
			<%
				}
			%>
		</div>
	</div>
</div>
<footer>
	<%@ include file="footer.html"%>
</footer>
</body>
</html>