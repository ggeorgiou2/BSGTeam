<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	if (session.getAttribute("twitterToken") != null) {
%>
<!-- ============ Forms ============ -->
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-12">
			<div class="well bs-component">
				<form action="location" method="post" class="form-horizontal">
					<fieldset>
						<legend>Discover what users have been discussing in a
							particular location</legend>
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
							<label for="radius" class="col-lg-2 control-label">Radius
								(in km)</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="radius"
									pattern="([0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)|([0-9]+)"
									data-validation-pattern-message="Must write real number"
									id="radius" placeholder="Radius" required>
							</div>
						</div>
						<div class="form-group">
							<label for="days" class="col-lg-2 control-label">Days</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="days" id="days"
									pattern="([1-9]([0-9]+)?)"
									data-validation-pattern-message="Must write real number"
									placeholder="Enter number of days (between 6-9 days)" required>
							</div>
						</div>
						<div class="form-group">
							<label for="keywords" class="col-lg-2 control-label">Number
								of Keywords</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="keywords"
									pattern="([1-9]([0-9]+)?)"
									data-validation-pattern-message="Must write real number"
									id="keywords" placeholder="Number of keywords" required>
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
	} else if (session.getAttribute("twitterToken") == null) {
%>
<jsp:include page="twitterToken.jsp" />
<%
	}
%>

<!-- ============ Results Table ============ -->
<div>
	<c:if test="${not empty words_result}">
		<script>
			setTimeout(function() {
				$('#mytab a[href=#LocationKeywords]').tab('show');
				window.location.href = '#discussionLocation';
			});
		</script>
	</c:if>
	<c:if test="${not empty words}">

		<div class="row" id="discussLocation">
			<div>

				<div class="row">
					<div class="col-md-10 col-md-push-1">
						<h1 align="center">List of Frequent Keywords</h1>
						<table class="table table-hover table-responsive table-condensed">
							<thead>
								<tr>
									<th>Word</th>
									<th>Frequency</th>
								</tr>
							</thead>
							<tbody class="table-hover">
								<c:forEach var="word" items="${words}">
									<tr>
										<td><c:out value="${word.key}" /></td>
										<td><c:out value="${word.value}" /></td>

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

