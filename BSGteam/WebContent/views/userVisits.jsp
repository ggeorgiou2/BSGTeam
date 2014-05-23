<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- ============ Forms ============ -->
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-12">
			<div class="well bs-component">
				<form action="visits" method="post" class="form-horizontal">
					<fieldset>
						<legend>Find out what points of interest a user has
							visited (or is visiting)</legend>
						<div class="form-group">
							<label for="user" class="col-lg-2 control-label">UserID</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="userID"
									id="userID" placeholder="User's Twitter ID" required>
							</div>
						</div>
						<div class="form-group">
							<label for="days" class="col-lg-2 control-label">Days</label>
							<div class="col-lg-10">
								<input type="number" class="form-control" name="days" id="days"
									placeholder="Number of days (between 6-9 days)" required>
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
	<c:if test="${not empty userVisits2}">
		<script>
			setTimeout(function() {
				$('#mytab a[href=#UserVisits]').tab('show');
				window.location.href = '#userVisitsResults';
			});
		</script>
		<c:forEach var="venue" items="${userVisits2}">
			<h1>
				<c:out value="${venue}" />
			</h1>
		</c:forEach>
	</c:if>

	<c:if test="${not empty userVisits_result}">
		<script>
			setTimeout(function() {
				$('#mytab a[href=#UserVisits]').tab('show');
				window.location.href = '#userVisitsResults';
			});
		</script>
	</c:if>
	<c:if test="${not empty userVisits}">

		<div class="row" id="userVisitsResults">
			<div>

				<div class="row">
					<div class="col-md-10 col-md-push-1">
						<h1 align="center">
							List of Venues that @
							<c:out value="${user}"></c:out>
							visited recently
						</h1>
						<table class="table table-hover table-responsive table-condensed">
							<thead>
								<tr>
									<th>Name (Click to view Images)
									<th>Address 
									<th>URL
									<th>Categories
									<th>Description
									<th>Time
								</tr>
							</thead>
							<tbody class="table-hover">
								<c:forEach var="venue" items="${userVisits}">
									<tr>
										<td><a
											href='venue?id=<c:out value="${venue.value.id}"/>
											&lat=<c:out value="${venue.value.location.lat}"/>
											&lng=<c:out value="${venue.value.location.lng }"/>'><c:out
													value="${venue.value.name}" /></a></td>
										<td><c:if test="${empty venue.value.location.address}">
												<c:out
													value="${venue.value.location.lat}, ${venue.value.location.lng}"></c:out>
											</c:if> <c:if test="${not empty venue.value.location.address}">
												<c:out value="${venue.value.location.address}" />
											</c:if> </a></td>
										<td><a href='<c:out value="${venue.value.url}"/>'><c:out
													value="${venue.value.url}" /></a></td>
										<td><c:forEach var="category"
												items="${venue.value.categories}">
												<c:out value="${category.name}"></c:out>
											</c:forEach></td>
										<td><c:out value="${venue.value.stats.usersCount}"></c:out>
											users have been here</td>
										<td><c:out value="${venue.key}"></c:out></td>

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

