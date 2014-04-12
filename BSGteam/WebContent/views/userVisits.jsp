<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<script type="text/javascript">
	 $(function () {
	        $('#visits123').on('submit', function (e) {
	          $('#visitsLoading').show();
	          $.ajax({
	            type: 'post',
	            url: 'visits',
	            data: $('#visits123').serialize(),
	            success: function (responseText) {
	            	 $('#visitsResults').html(responseText);
	            	 $('#visitsTable').show();
	            	 $('#visitsLoading').hide();
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
				<form action="visits123" id="visits123" method="post" class="form-horizontal">
					<fieldset>
						<legend>Find out what points of interest a user has visited (or is visiting)</legend>
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
<img src="images/ajax-loader.gif" id="visitsLoading" align="middle">
<div id="visitsTable">
		<div class="row" id="userVisitsResults">
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
							<tbody class="table-hover" id="visitsResults">
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
</div>

