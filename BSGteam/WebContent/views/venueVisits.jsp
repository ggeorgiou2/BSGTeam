<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
	 $(function () {
	        $('#VenueVisits').on('submit', function (e) {
	          $('#venueVisitsLoading').show();
	          $.ajax({
	            type: 'post',
	            url: 'VenueVisits',
	            data: $('#VenueVisits1').serialize(),
	            success: function (responseText) {
	            $('#venueVisitsResults').html(responseText);
	            $('#venueVisitsTable').show();
	            $('#venueVisitsLoading').hide();
	            }
	          });
	          e.preventDefault();
	        });
	      });
</script>
<!-- ============ Forms ============ -->
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-12">
			<div class="well bs-component">
				<form action="VenueVisits1" id="VenueVisits1" method="post" class="form-horizontal">
					<fieldset>
						<legend>Find out what users are visiting specific location</legend>
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
<img src="images/ajax-loader.gif" id="venueVisitsLoading" align="middle">
		<div class="row" id="venueVisitsTable">
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
							<tbody class="table-hover" id="venueVisitsResults">
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>

