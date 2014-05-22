<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	if (session.getAttribute("foursquareToken") != null) {
%>

<!-- ============ Forms ============ -->
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-12">
			<div class="well bs-component">
				<form action="venue" method="post" class="form-horizontal">
					<fieldset>
						<legend>Search for venues and points of interest within a
							particular location</legend>
							 <div id="mapCanvas" style="width: 100%; height: 250px;"></div>
						<div id="markerStatus">
							<i>Click and drag the marker.</i>
						</div>
						<div id="address"></div>	
						<div class="form-group">
							<label for="lat" class="col-lg-2 control-label">Latitude</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="lat"
									pattern="([-/+]?[0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)|([0-9]+)"
									data-validation-pattern-message="Must write real number"
									id="lat" placeholder="Latitude" required>
							</div>
						</div>
						<div class="form-group">
							<label for="long" class="col-lg-2 control-label">Longitude</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="long"
									pattern="([-/+]?[0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)|([0-9]+)"
									data-validation-pattern-message="Must write real number"
									id="long" placeholder="Longitude" required>
							</div>
						</div>
						<div class="form-group">
							<div class="col-lg-10 col-lg-offset-2">
								<button type="submit" class="btn btn-primary">Submit</button>
								<input type="reset" class="btn btn-default" value="Reset" />
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</div>
<%
	} else if (session.getAttribute("foursquareToken") == null) {
%>
<jsp:include page="foursquareToken.jsp" />

<%
	}
%>

<!-- ============ Results Table ============ -->
<div>
	<c:if test="${not empty venues_result}">
		<script>
			setTimeout(function() {
				$('#mytab a[href=#Venues]').tab('show');
				window.location.href = '#venueResults';
			});
		</script>
	</c:if>
	<c:if test="${not empty venues}">
		<div class="row" id="venueResults">
			<div>
				<div class="row">
					<div class="col-md-10 col-md-push-1">
						<h1 align="center">List of Venues</h1>
						<table class="table table-hover table-responsive table-condensed">
							<thead>
								<tr>
									<th>Name (Click to view Images)</th>
									<th>Location</th>
									<th>URL</th>
									<th>Categories</th>
									<th>Description</th>
								</tr>
							</thead>
							<tbody class="table-hover">
								<c:forEach var="venue" items="${venues}">
									<tr>
										<td><a
											href='venue?id=<c:out value="${venue.id}"/>
											&lat=<c:out value="${venue.location.lat}"/>
											&lng=<c:out value="${venue.location.lng }"/>'><c:out
													value="${venue.name}" /></a></td>
										<td><c:if test="${empty venue.location.address}">
												<c:out value="${venue.location.lat}, ${venue.location.lng}"></c:out>
											</c:if> <c:if test="${not empty venue.location.address}">
												<c:out value="${venue.location.address}" />
											</c:if></td>
										<td><a href='<c:out value="${venue.url}"/>'><c:out
													value="${venue.url}" /></a></td>
										<td><c:forEach var="category" items="${venue.categories}">
												<c:out value="${category.name}"></c:out>
											</c:forEach></td>
										<td><c:out value="${venue.stats.usersCount}"></c:out>
											users have been here</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</c:if>
</div>