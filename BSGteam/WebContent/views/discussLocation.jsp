<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
	 $(function () {
	        $('#location').on('submit', function (e) {
	          $('#DisLocLoading').show();
	          $.ajax({
	            type: 'post',
	            url: 'location',
	            data: $('#location').serialize(),
	            success: function (responseText) {
	            	 $('#DisLocResults').html(responseText);
	            	 $('#DisLocTable').show();
	            	 $('#DisLocLoading').hide();
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
				<form action="location" id="location" method="post" class="form-horizontal">
					<fieldset>
						<legend>Discover what users have been discussing in a particular location1</legend>
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
								<input type="number" class="form-control" name="days" id="days"
									placeholder="Enter number of days" required>
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
<img src="images/ajax-loader.gif" id="DisLocLoading" align="middle">
<div id="DisLocTable">
		<div class="row">
			<div class="well bs-component">
				<h1>List of Frequent Keywords</h1>
				<div class="row">
					<div class="col-md-10">
						<table
							class="table table-hover table-responsive table-bordered table-condensed">
							<thead>
								<tr>
									<th>Word
									<th>Frequency
								</tr>
							</thead>
							<tbody class="table-hover" id="DisLocResults">
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
</div>

