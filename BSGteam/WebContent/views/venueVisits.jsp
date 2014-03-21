<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- ============ Forms ============ -->
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-12">
			<div class="well bs-component">
				<form action="VenueVisits" method="post" class="form-horizontal">
					<fieldset>
						<legend>Location search</legend>
						<div class="form-group">
							<label for="lat" class="col-lg-2 control-label">Latitude:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="lat"
									pattern="([-/+]?[0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)|([0-9]+)"
									data-validation-pattern-message="Must write real number"
									id="lat" placeholder="Latitude" required>
							</div>
						</div>
						<div class="form-group">
							<label for="long" class="col-lg-2 control-label">Longitude:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="long"
									pattern="([-/+]?[0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)|([0-9]+)"
									data-validation-pattern-message="Must write real number"
									id="long" placeholder="Longitude" required>
							</div>
						</div>
						<div class="form-group">
							<label for="days" class="col-lg-2 control-label">Days</label>
							<div class="col-lg-10">
								<input type="number" class="form-control" name="days" id="days"
									placeholder="Number of days" required>
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
	<c:if test="${not empty checkins}">
		<script>
			setTimeout(function() {
				$('#mytab a[href=#VenueVisits]').tab('show');
				window.location.href = '#venueVisitsResults';
			});
		</script>
		<div class="row" id="venueVisitsResults">
			<div class="well bs-component">
				<h1>List of Users visiting venues in this location</h1>
				<div class="row">
					<div class="col-md-10">
						<table
							class="table table-hover table-responsive table-bordered table-condensed">
							<thead>
								<tr>
									<th>Name
									<th>Venue Name
									<th>Address (Click to view on map)
									<th>URL
									<th>Categories
								</tr>
							</thead>
							<tbody class="table-hover">
								<c:forEach var="checkin" items="${checkins}">
									<c:if test="${not empty checkin}">
										<tr>
											<td><c:out value="${checkin.user.firstName}" /> <c:out
													value="${checkin.user.lastName}" /></td>
											<td><a
												href='venue?id=<c:out value="${checkin.venue.id}"/>'><c:out
														value="${checkin.venue.name}" /></a></td>
											<td><a
												href='views/venuemap.html?lat=<c:out value="${checkin.venue.location.lat}"/>
											&lng=<c:out value="${checkin.venue.location.lng }"/>'>
													<c:if test="${empty checkin.venue.location.address}">
														<c:out
															value="${checkin.venue.location.lat}, ${checkin.venue.location.lng}"></c:out>
													</c:if> <c:if test="${not empty checkin.venue.location.address}">
														<c:out value="${checkin.venue.location.address}" />
													</c:if>
											</a></td>
											<td><a href='<c:out value="${checkin.venue.url}"/>'><c:out
														value="${checkin.venue.url}" /></a></td>
											<td><c:forEach var="category"
													items="${checkin.venue.categories}">
													<c:out value="${category.name}"></c:out>
												</c:forEach></td>
											</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</c:if>
</div>

