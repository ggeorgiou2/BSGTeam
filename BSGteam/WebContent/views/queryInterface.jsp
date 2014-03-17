<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<%@ include file="header.html"%>
<script>
var url = document.location.toString();
$(window).load(function(){
	$('#mytab a[href=#'+url.split('#')[1]+']').tab('show');
});
</script>
<%-- <c:if test="${empty kokos">
    var1 is empty or null.
	</c:if>
	<c:if test="${not empty kokos">		
	</c:if> --%>

<!-- 
<div class="alert alert-dismissable alert-warning">
	<button type="button" class="close" data-dismiss="alert">&times;</button>
	<h4>Warning!</h4>
	<p>fdsf</p>
</div>
 -->
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-6 col-md-10 col-md-push-3">
			<!--<h2 id="nav-tabs">Tabs</h2>-->
			<div class="bs-component">
				<ul class="nav nav-tabs" style="margin-bottom: 15px;" id="mytab">
					<li class="active"><a href="#Discussion" data-toggle="tab">Discussion</a></li>
					<li><a href="#Venues" data-toggle="tab">Venues</a></li>
					<li><a href="#NearVenues" data-toggle="tab">Near Venues</a></li>
					<li><a href="#UserVisits" data-toggle="tab">User Visits</a></li>
					<li><a href="#LocationKeywords" data-toggle="tab">Discussion by Location</a></li>
					<li><a href="#TrackUsers" data-toggle="tab">Track Users</a></li>
					
					
				</ul>
				<!-- <div class="container">
					<input type="text" placeholder="click to show datepicker"
						id="datepicker">
				</div> -->
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane fade active in" id="Discussion">
						<p>
							<!-- Forms================================================== -->
						<div class="bs-docs-section">
							<div class="row">
								<div class="col-lg-12">
									<div class="well bs-component">
										<form action="twitter" method="post" class="form-horizontal">
											<fieldset>
												<legend>Discussion Form</legend>
												<div class="form-group">
													<label for="tweetData" class="col-lg-2 control-label">Topic:</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="tweetData"
															id="tweetData" placeholder="Topic" required>
													</div>
												</div>
												<div class="form-group">
													<label for="lat" class="col-lg-2 control-label">Latitude:</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="lat"
															id="lat" placeholder="Latitude">
													</div>
												</div>
												<div class="form-group">
													<label for="long" class="col-lg-2 control-label">Longitude:</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="long"
															id="long" placeholder="Longitude">
													</div>
												</div>
												<div class="form-group">
													<label for="area" class="col-lg-2 control-label">Area:
														(in km):</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="area"
															id="area" placeholder="Area">
													</div>
												</div>
												<div class="form-group">
													<label for="select" class="col-lg-2 control-label">Selects</label>
													<div class="col-lg-10">
														<br> <select multiple="" class="form-control">
															<option>Sheffield</option>
															<option>London (51.5286416,-0.1015986)</option>
															<option>Manchester</option>
															<option>Leeds</option>
															<option>York</option>
														</select>
													</div>
												</div>
												<div class="form-group">
													<div class="col-lg-10 col-lg-offset-2">
														<button type="submit" class="btn btn-primary">Submit</button>
														<button class="btn btn-default">Reset</button>
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
							<script>
								$('#sandbox-container input').datepicker({});
							</script>
							<!-- Forms================================================== -->
						<div class="bs-docs-section">
							<div class="row">
								<div class="col-lg-12">
									<div class="well bs-component">
										<form action="venue" method="post" class="form-horizontal">
											<fieldset>
												<legend>Venues Form</legend>
												<div class="form-group">
													<label for="lat" class="col-lg-2 control-label">Latitude</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="lat"
															id="lat" placeholder="Latitude" required>
													</div>
												</div>
												<div class="form-group">
													<label for="long" class="col-lg-2 control-label">Longitude</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="long"
															id="long" placeholder="Longitude" required>
													</div>
												</div>
												<div class="form-group">
													<label for="select" class="col-lg-2 control-label">Name
														of Location</label>
													<div class="col-lg-10">
														<br> <select multiple="" class="form-control">
															<option>Sheffield</option>
															<option>London</option>
															<option>Manchester</option>
															<option>Leeds</option>
															<option>York</option>
														</select>
													</div>
												</div>
												<div class="form-group">
													<div class="col-lg-10 col-lg-offset-2">
														<button type="submit" class="btn btn-primary">Submit</button>
														<button class="btn btn-default">Reset</button>
													</div>
												</div>
											</fieldset>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="LocationKeywords">
						<p>
							<script>
								$('#sandbox-container input').datepicker({});
							</script>
							<!-- Forms================================================== -->
						<div class="bs-docs-section">
							<div class="row">
								<div class="col-lg-12">
									<div class="well bs-component">
										<form action="location" method="post" class="form-horizontal">
											<fieldset>
												<legend>Track Discussions in a location</legend>
												<div class="form-group">
													<label for="lat" class="col-lg-2 control-label">Latitude</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="lat"
															id="lat" placeholder="Latitude" required>
													</div>
												</div>
												<div class="form-group">
													<label for="long" class="col-lg-2 control-label">Longitude</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="long"
															id="long" placeholder="Longitude" required>
													</div>
												</div>
												<div class="form-group">
													<label for="radius" class="col-lg-2 control-label">Radius</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="radius"
															id="radius" placeholder="radius" required>
													</div>
												</div>
												<div class="form-group">
													<label for="days" class="col-lg-2 control-label">Days</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="days"
															id="days" placeholder="Enter number of days" required>
													</div>
												</div>
												<div class="form-group">
													<label for="keywords" class="col-lg-2 control-label">Number of Keywords</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="keywords"
															id="keywords" placeholder="Number of keywords" required>
													</div>
												</div>
												
												
												<div class="form-group">
													<div class="col-lg-10 col-lg-offset-2">
														<button type="submit" class="btn btn-primary">Submit</button>
														<button class="btn btn-default">Reset</button>
													</div>
												</div>
											</fieldset>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="NearVenues">
						<p>
						<div class="bs-docs-section">
							<div class="row">
								<div class="col-lg-12">
									<div class="well bs-component">
										<form action="nearvenue" method="post" class="form-horizontal">
											<fieldset>
												<legend>Near Venues Form</legend>
												<div class="form-group">
													<label for="venueID" class="col-lg-2 control-label">Venue
														ID</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="venueID"
															id="venueID" placeholder="venue id" required>
													</div>
												</div>
												<div class="form-group">
													<div class="col-lg-10 col-lg-offset-2">
														<button type="submit" class="btn btn-primary">Submit</button>
														<button class="btn btn-default">Reset</button>
													</div>
												</div>
											</fieldset>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="UserVisits">
						<p>
						<div class="bs-docs-section">
							<div class="row">
								<div class="col-lg-12">
									<div class="well bs-component">
										<form action="visits" method="post" class="form-horizontal">
											<fieldset>
												<legend>User Visits</legend>
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
														<input type="text" class="form-control" name="days"
															id="days" placeholder="Number of days" required>
													</div>
												</div>
												<div class="form-group">
													<div class="col-lg-10 col-lg-offset-2">
														<button type="submit" class="btn btn-primary">Submit</button>
														<button class="btn btn-default">Reset</button>
													</div>
												</div>
											</fieldset>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="TrackUsers">
						<p>
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
															id="userIDs" placeholder="Users' Twitter IDs (separated by commas)" required>
													</div>
												</div>
												<div class="form-group">
													<label for="keywords" class="col-lg-2 control-label">Number of Keywords</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="keywords"
															id="keywords" placeholder="Number of keywords" required>
													</div>
												</div>
												<div class="form-group">
													<label for="days" class="col-lg-2 control-label">Days</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="days"
															id="days" placeholder="Number of days" required>
													</div>
												</div>
												<div class="form-group">
													<div class="col-lg-10 col-lg-offset-2">
														<button type="submit" class="btn btn-primary">Submit</button>
														<button class="btn btn-default">Reset</button>
													</div>
												</div>
											</fieldset>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="timeline">
						<p>
							<!-- Forms================================================== -->
						<div class="bs-docs-section">
							<div class="row">
								<div class="col-lg-12">
									<div class="well bs-component">
										<div class="row">
											<div class="col-lg-12">
												<div class="well bs-component">
													<%-- <jsp:include page="timeline.jsp" /> --%>
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
		</div>
	</div>
	<div class="row">
		<div id="results">
			<jsp:include page="results.jsp" />
		</div>
	</div>
	<div class="row">
		<div id="venueResults">
			<jsp:include page="venues.jsp" />
		</div>
	</div>
	<div class="row">
		<div id="nearVenueResults">
			<jsp:include page="nearvenues.jsp" />
		</div>
	</div>
	<div class="row">
		<div id="userVisitsResults">
			<jsp:include page="userVisits.jsp" />
		</div>
	</div>
	<div class="row">
		<div id="discussLocation">
			<jsp:include page="discussLocation.jsp" />
		</div>
	</div>
</div>
<footer>
	<%@ include file="footer.html"%>
</footer>
</body>
</html>