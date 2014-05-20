<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script type="text/javascript">
	 $(function () {
	        $('#twitterToken').on('submit', function (e) {
	          $('#loading_bar').show();
	          $.ajax({
	            type: 'post',
	            url: 'twitterToken',
	            data: $('#twitterToken').serialize(),
	            success: function (responseJson) {
	            	 if (responseJson == "success") {
		            //     $.each(responseJson, function(index, item) { 
		              //  	 $("#tokenResults").append(item);
		                // });

						$('#alert-success').show();
  	            	    $('#loading_bar').hide();
		              	 location.reload();

 	            	} else if (responseJson == "error") {
		               //  $.each(responseJson, function(index, item) {
		                //	 $("#tokenResults").append(item);
		               // });
						$('#alert-danger').show();
  	            	    $('#loading_bar').hide();
	            	}

	            	 //$('#results').html(responseText);
	            	 //$('#tokenResults').load("discussResults.jsp");
	            	 //$('#tokenResults').load('../dicussResults.jsp#tokenResults');

/* 	            	 var container = document.getElementById("#tokenResults");
				     var content = container.innerHTML;
				     container.innerHTML= content; */
		             //$("#tokenResults").innerHTML = ("NewFile.html ");
				     //document.getElementById("tokenResults").innerHTML='<object type="text/html" data="NewFile.html" >bchgf</object>';

			         //$("#tokenResults").load("discussResults.jsp");

	            	 //$('#tokenResults').show();

	            }
	          });
	          e.preventDefault();
	        });
	      });
</script>

<script>
  function onLoad()
  {
   $('#loading_bar').hide();
   $('#results_table').hide();
   $('#alert-success').hide();
   $('#alert-danger').hide();
  }
  window.onload=onLoad;
</script>		
<img src="images/ajax-loader.gif" id="loading_bar" align="middle">

<div class="alert alert-danger" id="alert-danger" align="center">
	<strong><c:out value="error"></c:out></strong>
</div>

<div class="alert alert-success" id="alert-success" align="center">
	<strong><c:out value="success"></c:out></strong>
</div>


<%
	if (session.getAttribute("twitterToken") != null) {
%>
<a href='twitterToken?action=logout'>Logout</a>
<!-- ============ Forms ============ -->
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-12">
			<div class="well bs-component">
				<form action="twitter" method="post" class="form-horizontal">
					<fieldset>
						<legend>Input keywords or hashtags to find recent
							discussions on those topics. You can also limit this to a
							geographical location</legend>
						<div class="form-group">
							<label for="tweetData" class="col-lg-2 control-label">Topic:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="tweetData"
									id="tweetData" placeholder="Topic" required>
							</div>
						</div>

						<script type="text/javascript"
							src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places"></script>
						<script type="text/javascript">
							var geocoder = new google.maps.Geocoder();

							function geocodePosition(pos) {
								geocoder
										.geocode(
												{
													latLng : pos
												},
												function(responses) {
													if (responses
															&& responses.length > 0) {
														updateMarkerAddress(responses[0].formatted_address);
													} else {
														updateMarkerAddress('Cannot determine address at this location.');
													}
												});
							}

							function updateMarkerStatus(str) {
								document.getElementById('markerStatus').innerHTML = str;
							}

							function updateMarkerPosition(latLng) {
								document.getElementById('lat').value = latLng
										.lat();
								document.getElementById('long').value = latLng
										.lng();
							}

							function updateMarkerAddress(str) {
								document.getElementById('address').innerHTML = str;
							}

							function initialize() {

								var markers = [];
								var map = new google.maps.Map(document
										.getElementById('mapCanvas'), {
									mapTypeId : google.maps.MapTypeId.ROADMAP
								});

								var defaultBounds = new google.maps.LatLngBounds(
										new google.maps.LatLng(-33.8902,
												151.1759),
										new google.maps.LatLng(-33.8474,
												151.2631));
								map.fitBounds(defaultBounds);

								// Create the search box and link it to the UI element.
								var input = /** @type {HTMLInputElement} */
								(document.getElementById('pac-input'));
								map.controls[google.maps.ControlPosition.TOP_LEFT]
										.push(input);

								var searchBox = new google.maps.places.SearchBox(
								/** @type {HTMLInputElement} */
								(input));

								// Listen for the event fired when the user selects an item from the
								// pick list. Retrieve the matching places for that item.
								google.maps.event
										.addListener(
												searchBox,
												'places_changed',
												function() {
													var places = searchBox
															.getPlaces();

													for ( var i = 0, marker; marker = markers[i]; i++) {
														marker.setMap(null);
													}

													// For each place, get the icon, place name, and location.
													markers = [];
													var bounds = new google.maps.LatLngBounds();
													for ( var i = 0, place; place = places[i]; i++) {
														var image = {
															url : place.icon,
															size : new google.maps.Size(
																	71, 71),
															origin : new google.maps.Point(
																	0, 0),
															anchor : new google.maps.Point(
																	17, 34),
															scaledSize : new google.maps.Size(
																	25, 25)
														};
														  var latLng = new google.maps.LatLng(-34.397, 150.644);

														// Create a marker for each place.
														var marker = new google.maps.Marker(
																{
																	map : map,
																	icon : image,
																	title : place.name,
																	position : latLng,
																	draggable : true

																});

														markers.push(marker);

														bounds
																.extend(place.geometry.location);
													}

													map.fitBounds(bounds);
												});

								// Bias the SearchBox results towards places that are within the bounds of the
								// current map's viewport.
								google.maps.event.addListener(map,
										'bounds_changed', function() {
											var bounds = map.getBounds();
											searchBox.setBounds(bounds);
										});

								//var latLng = place.geometry.location;
								/* var map = new google.maps.Map(document
										.getElementById('mapCanvas'), {
									zoom : 8,
									center : latLng,
									mapTypeId : google.maps.MapTypeId.ROADMAP
								});
								 var marker = new google.maps.Marker({
									position : latLng,
									title : 'Point A',
									map : map,
									draggable : true
								});*/

								// Update current position info.
								updateMarkerPosition(latLng);
								geocodePosition(latLng);

								// Add dragging event listeners.
								google.maps.event.addListener(marker,
										'dragstart', function() {
											updateMarkerAddress('Dragging...');
											alert(place.geometry.location + " aaa " + latLng);
										});

								google.maps.event.addListener(marker, 'drag',
										function() {
											updateMarkerStatus('Dragging...');
											updateMarkerPosition(marker
													.getPosition());
											alert(place.geometry.location + " aaa " + latLng);
										});

								google.maps.event.addListener(marker,
										'dragend', function() {
											updateMarkerStatus('Drag ended');
											geocodePosition(marker
													.getPosition());
											alert(place.geometry.location + " aaa " + latLng);
										});
								
								alert(place.geometry.location + " aaa " + latLng);
							}

							// Onload handler to fire off the app.
							google.maps.event.addDomListener(window, 'load',
									initialize);
							
						</script>
						<style>
#mapCanvas {
	width: 800px;
	height: 400px;
	float: left;
}
</style>
						<input id="pac-input" class="controls" type="text"
							placeholder="Search Box">
						<div id="mapCanvas"></div>

						<p>
							<b>Marker status:</b> <div id="markerStatus">
							<i>Click and drag the marker.</i>
						</div>

						<div id="address"></div>
  </p>


						<div class="form-group">
							<label for="lat" class="col-lg-2 control-label">Latitude:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control"
									pattern="([-/+]?[0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)|([0-9]+)"
									data-validation-pattern-message="Must write real number"
									id="lat" name="lat" placeholder="Latitude">
							</div>
						</div>
						<div class="form-group">
							<label for="long" class="col-lg-2 control-label">Longitude:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="long"
									pattern="([-/+]?[0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)|([0-9]+)"
									data-validation-pattern-message="Must write real number"
									id="long" placeholder="Longitude">
							</div>
						</div>
						<div class="form-group">
							<label for="area" class="col-lg-2 control-label">Radius:
								(in km):</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="area"
									pattern="([0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)|([0-9]+)"
									data-validation-pattern-message="Must write real number"
									id="area" placeholder="Area">
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
	<c:if test="${not empty statuses_result}">
		<script>
			setTimeout(function() {
				window.location.href = '#results';
			});
		</script>
	</c:if>
	<c:if test="${not empty statuses}">

		<div class="row" id="results">
			<div>
				<div class="row">
					<div class="col-md-10 col-md-push-1">
						<h1 align="center">List of Results</h1>
						<table
							class="table table-hover table-responsive table-bordered table-condensed">
							<thead>
								<tr>
									<th>Screen name</th>
									<th>Location</th>
									<th>Description</th>
									<th>Tweet</th>
									<th>Date</th>
									<th>Retweets</th>
								</tr>
							</thead>
							<tbody class="table-hover">
								<c:forEach var="status" items="${statuses}">
									<tr>
										<td><a
											href='timelines?screenname=<c:out value="${status.user.screenName}"/>'>@<c:out
													value="${status.user.screenName}" /></a>
											<p>
												<img src="<c:out value="${status.user.profileImageURL}" />">
											</p></td>
										<td><c:out value="${status.user.location}" /></td>
										<td><c:out value="${status.user.description}" /></td>
										<td><c:out value="${status.text}" /></td>
										<td><c:out value="${status.createdAt}" /></td>
										<td><c:choose>
												<c:when test="${fn:contains(status.text, 'RT @')}">
												No retweets
											</c:when>
												<c:when test="${status.retweetCount > 0 }">
													<a href='timelines?tweetID=<c:out value="${status.id}"/>'>
														Get Retweeters</a>
												</c:when>
												<c:otherwise>
												No retweets
											</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>
								<%-- <c:forEach var="queryInterface" items="${timelines}">
								<tr>
									<td>@<c:out value="${timeline.user.screenName}" /></td>
									<td>@<c:out value="${timeline.text}" /></td>
								</tr>
							</c:forEach> --%>

							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</c:if>
</div>
