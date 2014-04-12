<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
	 $(function () {
	        $('#nearvenue').on('submit', function (e) {
	          $('#loading_nearVenue').show();
	          $.ajax({
	            type: 'post',
	            url: 'nearvenue',
	            data: $('form').serialize(),
	            success: function (responseText) {
	            	 $('#nearVenueResults').html(responseText);
	            	 $('#nearVenue_table').show();
	            	 $('#loading_nearVenue').hide();
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
				<form action="nearvenue" id="nearvenue" method="post" class="form-horizontal">
					<fieldset>
						<legend>Find other venues around a specified venue</legend>
						<div class="form-group">
							<label for="venueID" class="col-lg-2 control-label">Venue
								ID</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="venueID"
									id="venueID" placeholder="Venue id" required>
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

<!-- ============ nearVenueResults Table ============ -->
<img src="images/ajax-loader.gif" id="loading_nearVenue" align="middle">
<div id="nearVenue_table">
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
							<tbody class="table-hover" id="nearVenueResults">
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
</div>

