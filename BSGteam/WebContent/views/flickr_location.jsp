<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- ============ Forms ============ -->
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-12">
			<div class="well bs-component">
				<form action="" class="form-horizontal your-class" method="post">
					<fieldset>
						<legend>Search for photos within a location</legend>
						<div class="form-group">
							<label for="key" class="col-lg-2 control-label">Flickr
								Api Key:</label>
							<div class="col-lg-10">
								<input id="key" class="form-control"
									placeholder="Enter your Flickr API key" required>
							</div>
						</div>
 						<div id="mapCanvas" style="width: 100%; height: 250px;"></div>
						<div id="markerStatus">
							<i>Click and drag the marker.</i>
						</div>
						<div id="address"></div>	
						<div class="form-group">
							<label for="lat" class="col-lg-2 control-label">Latitude:</label>
							<div class="col-lg-10">
								<input id="lat" class="form-control" placeholder="Latitude"
									required>
							</div>
						</div>

						<div class="form-group">
							<label for="long" class="col-lg-2 control-label">Longitude:</label>
							<div class="col-lg-10">
								<input id="long" class="form-control" placeholder="Longitude"
									required>
							</div>
						</div>

						<div class="form-group">
							<label for="tag" class="col-lg-2 control-label">Tag Word:</label>
							<div class="col-lg-10">
								<input id="tag" class="form-control"
									placeholder="Your search term" required>
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-10 col-lg-offset-2">
								<button id="search" class="btn btn-primary">Search</button>
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


	<script src="js/flickrLocation.js"></script>

	<div class="search" id="map-canvas"></div>
</div>