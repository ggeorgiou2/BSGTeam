<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- ============ Forms ============ -->
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-12">
			<div class="well bs-component">
				<form action="discussions" method="post" class="form-horizontal">
					<fieldset>
						<legend>Track discussion of multiple Users</legend>
						<div class="form-group">
							<label for="users" class="col-lg-2 control-label">UserIDs</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="userIDs"
									id="userIDs"
									placeholder="Users' Twitter IDs (separated by commas)" required>
							</div>
						</div>
						<div class="form-group">
							<label for="keywords" class="col-lg-2 control-label">Number
								of Keywords</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="keywords"
									id="keywords" placeholder="Number of keywords" required>
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
	<c:if test="${not empty finalList_result}">
		<script>
			setTimeout(function() {
				$('#mytab a[href=#TrackUsers]').tab('show');
				window.location.href = '#multipleUsers';
			});
		</script>
	</c:if>
	<c:if test="${not empty finalList}">
		<div class="row" id="multipleUsers">
			<div class="row">
				<div class="col-md-10 col-md-push-1">
					<h1 align="center">List of Frequent Keywords</h1>

					<table class="table table-hover table-responsive table-condensed">

						<thead>
							<tr>
								<th>Keywords</th>
								<c:forEach var="user" items="${users}">
									<th><c:out value="${user}"></c:out>
								</c:forEach>
								<th>Total</th>
							</tr>
						</thead>
						<tbody class="table-hover">
							<c:forEach var="word" items="${totalList}">
								<tr>
									<td><c:out value="${word.key}" /></td>
									<c:forEach var="list" items="${finalList}">
										<c:forEach var="map" items="${list}">
											<c:if test="${map.key == word.key}">
												<td><c:out value="${map.value}" /></td>
											</c:if>
										</c:forEach>
									</c:forEach>
									<td><c:out value="${word.value}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</c:if>
</div>

