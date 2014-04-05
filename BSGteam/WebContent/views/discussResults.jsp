<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
	 $(function () {
	        $('form').on('submit', function (e) {
	          $('#loading_bar').show();
	          $.ajax({
	            type: 'post',
	            url: 'twitter',
	            data: $('form').serialize(),
	            success: function (responseText) {
	            	 $('#results').html(responseText);
	            	 $('#results_table').show();
	            	 $('#loading_bar').hide();
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
  }
  window.onload=onLoad;
</script>
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
						<div class="form-group">
							<label for="lat" class="col-lg-2 control-label">Latitude:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="lat"
									pattern="([-/+]?[0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)|([0-9]+)"
									data-validation-pattern-message="Must write real number"
									id="lat" placeholder="Latitude">
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

<!-- ============ Results Table ============ -->
<img src="images/ajax-loader.gif" id="loading_bar" align="middle">
<div id="results_table">
		<div class="row">
			<div class="well bs-component">
				<h1>List of Results</h1>
				<div class="row">
					<div class="col-md-10">
						<table
							class="table table-hover table-responsive table-bordered table-condensed">
							<thead>
								<tr>
									<th>Screen name
									<th>Location
									<th>Description
									<th>Tweet</th>
									<th>Tweet2???</th>
									<th>Retweets</th>
								</tr>
							</thead>
							<tbody class="table-hover" id="results">
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
</div>
