<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript">
	$(function() {
		$('#databaseSearch').on('submit', function(e) {
			$('#loading_bar').show();
			$.ajax({
				type : 'post',
				url : 'databaseSearch',
				data : $('form').serialize(),
				success : function(responseText) {
					$('#results').html(responseText);
					$('#results_table').show();
					$('#loading_bar').hide();
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
				<form action="databaseSearch" id="databaseSearch" method="post"
					class="form-horizontal">
					<fieldset>
						<legend>Search for saved users and tweets</legend>
						<div class="form-group">
							<label for="tweetData" class="col-lg-2 control-label">Twitter
								Name:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="twitterID"
									id="twitterID"
									placeholder="Enter Twitter ID or type * to view all" required>
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
<div class="col-lg-7 col-md-10 col-md-push-3">
	<img src="images/ajax-loader.gif" id="loading_bar" align="middle">
	<div id="results_table">
		<div class="row">
			<div class="well bs-component">
				<h1>List of Saved User Tweets</h1>
				<div class="row">
					<div class="col-md-10">
						<table
							class="table table-hover table-responsive table-bordered table-condensed">
							<thead>
								<tr>
									<th><b>Picture</b>
									<th><b>Twitter Username</b>
									<th><b>Location</b>
									<th><b>Description</b>
									<th><b>Tweet</b>
									<th><b>ReTweet</b>
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
</div>