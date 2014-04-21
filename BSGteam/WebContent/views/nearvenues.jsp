<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- ============ Forms ============ -->
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-12">
			<div class="well bs-component">
				<form action="nearvenue" method="post" class="form-horizontal">
					<fieldset>
						<legend>Find other venues around a specified venue</legend>
						<div class="form-group">
							<label for="venueID" class="col-lg-2 control-label">Venue
								ID</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="venueID"
									id="venueID" placeholder="Venue id" required>
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

<!-- ============ Results Table ============ -->
<div>
	<c:if test="${not empty nearVenues}">
		<script>
			setTimeout(function() {
				$('#mytab a[href=#NearVenues]').tab('show');
				window.location.href = '#nearVenueResults';
			});
		</script>
		<div class="row" id="nearVenueResults">
			<div>
				<div class="row">
					<div class="col-md-10 col-md-push-1">
						<h1 align="center">List of Venues nearby</h1>
<table
							class="table table-hover table-responsive table-condensed">
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
								<c:forEach var="venue" items="${nearVenues}">
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

