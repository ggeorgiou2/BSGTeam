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
			<h1>Search triple store for saved queries</h1>
			<div class="bs-component">
				<ul class="nav nav-pills" style="margin-bottom: 15px;" id="mytab">
					<li class="active"><a href="#twitter" data-toggle="tab">Search
							User</a></li>
					<li><a href="#Venues" data-toggle="tab">Search Venue</a></li>
				</ul>
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane fade active in" id="twitter">
						<p>
							<!-- Forms================================================== -->
						<div class="bs-docs-section">
							<div class="row">
								<div class="col-lg-12">
									<div class="well bs-component">
										<form action="userSearch" method="post"
											class="form-horizontal">
											<fieldset>
												<legend>Search for saved users</legend>
												<div class="form-group">
													<label for="tweetData" class="col-lg-2 control-label">User
														Name:</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="twitterID"
															id="twitterID"
															placeholder="Enter User ID"
															required>
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
					</div>
					<div class="tab-pane fade" id="Venues">
						<p>
							<!-- Forms================================================== -->
						<div class="bs-docs-section">
							<div class="row">
								<div class="col-lg-12">
									<div class="well bs-component">
										<form action="venueSearch" method="post"
											class="form-horizontal">
											<fieldset>
												<legend>Search for saved venues</legend>
												<div class="form-group">
													<label for="tweetData" class="col-lg-2 control-label">Venue:</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="venue"
															id="venue"
															placeholder="Enter name of venue"
															required>
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
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div id="twitterResults">
			<jsp:include page="searchUser.jsp" />
		</div>
	</div>
	<div class="row">
		<div id="venueResults">
			<jsp:include page="searchVenue.jsp" />
		</div>
	</div>
</div>
<footer>
	<%@ include file="footer.html"%>
</footer>
</body>
</html>