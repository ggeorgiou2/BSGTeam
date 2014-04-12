<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
	 $(function () {
	        $('#venue').on('submit', function (e) {
	          $('#venue_loading').show();
	          $.ajax({
	            type: 'post',
	            url: 'venue',
	            data: $('#venue').serialize(),
	            success: function (responseText) {
	            	 $('#venueResults').html(responseText);
	            	 $('#venueTable').show();
	            	 $('#venue_loading').hide();
	            }
	          });
	          e.preventDefault();
	        });
	      });
</script>
<!-- <script>
  function onLoad()
  {
   $('#venue_loading').hide();
   $('#venueTable').hide();
  }
  window.onload=onLoad;
</script>
<!-- ============ Forms ============ -->
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-12">
			<div class="well bs-component">
				<form action="venue" id="venue" method="post" class="form-horizontal">
					<fieldset>
						<legend>Search for venues within a particular location</legend>
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

<!-- ============ venueResults Table ============ -->
<img src="images/ajax-loader.gif" id="venue_loading" align="middle">
<div id="venueTable">
		<div class="row">
			<div class="well bs-component">
				<h1>List of Venues</h1>
				<div class="row">
					<div class="col-md-10">
						<table
							class="table table-hover table-responsive table-bordered table-condensed">
							<thead>
								<tr>
									<th>Name (Click to view Images)
									<th>Address (Click to view on map)
									<th>URL
									<th>Categories
									<th>Description
								</tr>
							</thead>
							<tbody class="table-hover" id="venueResults">
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
</div>