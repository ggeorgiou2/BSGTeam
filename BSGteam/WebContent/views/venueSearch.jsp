<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript">
	$(function() {
		$('#venueSearch').on('submit', function(e) {
			$('#venueLoading').show();
			$.ajax({
				type : 'post',
				url : 'venueSearch',
				data : $('form').serialize(),
				success : function(responseText) {
					$('#venueResults').html(responseText);
					$('#venueTable').show();
					$('#venueLoading').hide();
				}
			});
			e.preventDefault();
		});
	});
</script>

<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-12">
			<div class="well bs-component">
				<form action="venueSearch" id="venueSearch" method="post"
					class="form-horizontal">
					<fieldset>
						<legend>Search for previous queries of user visits</legend>
						<div class="form-group">
							<label for="tweetData" class="col-lg-2 control-label">Venue:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="venue" id="venue"
									placeholder="Enter name of venue or * to view all" required>
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
<img src="images/ajax-loader.gif" id="venueLoading" align="middle">
<div id="venueTable">
	<div class="col-lg-7 col-md-10 col-md-push-3">
		<div class="row">
			<div class="well bs-component">
				<h1>List of Users</h1>
				<div class="row">
					<div class="col-md-10">
						<table
							class="table table-hover table-responsive table-bordered table-condensed">
							<thead>
								<tr>
									<th><b>Users</b>
									<th><b>Venue</b>
								</tr>
							<tbody class="table-hover" id="venueResults">
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>