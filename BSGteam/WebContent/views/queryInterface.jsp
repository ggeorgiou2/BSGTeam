<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<%@ include file="header.html"%>
<script>
	var url = document.location.toString();
	$(window).load(function() {
		$('#mytab a[href=#' + url.split('#')[1] + ']').tab('show');
	});
</script>
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-7 col-md-10 col-md-push-3">
			<h1>Hello! Query the social web as much as you like...</h1>
			<div class="bs-component">
				<ul class="nav nav-pills nav-justified"
					style="margin-top: 20px; margin-bottom: 5px;" id="mytab">
					<li class="active"><a href="#Discussion" data-toggle="tab">Public Discussion</a></li>
					<li><a href="#Venues" data-toggle="tab">Venues</a></li>
					<li><a href="#NearVenues" data-toggle="tab">Near Venues</a></li>
					<li><a href="#UserVisits" data-toggle="tab">User Visits</a></li>
					<li><a href="#VenueVisits" data-toggle="tab">Venue Visits</a></li>
					<li><a href="#LocationKeywords" data-toggle="tab">Trending discussions</a></li>
					<li><a href="#TrackUsers" data-toggle="tab">Track Users</a></li>
				</ul>
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane fade active in" id="Discussion">
						<jsp:include page="discussResults.jsp" />
					</div>
					<div class="tab-pane fade" id="Venues">
						<jsp:include page="venues.jsp" />
					</div>
					<div class="tab-pane fade" id="LocationKeywords">
						<jsp:include page="discussLocation.jsp" />
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
						<div class="bs-docs-section">
							<div class="row">
								<div class="col-lg-12">
									<div class="well bs-component">
										<form action="visits" method="post" class="form-horizontal">
											<fieldset>
												<legend>Track Users' Discussions</legend>
												<div class="form-group">
													<label for="users" class="col-lg-2 control-label">UserIDs</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="userIDs"
															id="userIDs"
															placeholder="Users' Twitter IDs (separated by commas)"
															required>
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
														<input type="number" class="form-control" name="days"
															id="days" placeholder="Number of days" required>
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
</div>
<footer>
	<%@ include file="footer.html"%>
</footer>
</body>
</html>