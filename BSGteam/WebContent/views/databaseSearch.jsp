<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<%@ include file="header.html"%>
<script>
var url = document.location.toString();
$(window).load(function(){
	$('#mytab a[href=#'+url.split('#')[1]+']').tab('show');
});
</script>
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-6 col-md-10 col-md-push-3">
			<!--<h2 id="nav-tabs">Tabs</h2>-->
			<div class="bs-component">
				<ul class="nav nav-tabs" style="margin-bottom: 15px;" id="mytab">
					<li class="active"><a href="#twitter" data-toggle="tab">Twitter</a></li>
					<li><a href="#Venues" data-toggle="tab">Venues</a></li>			
				</ul>
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane fade active in" id="twitter">
						<p>
							<!-- Forms================================================== -->
						<div class="bs-docs-section">
							<div class="row">
								<div class="col-lg-12">
									<div class="well bs-component">
										<form action="databaseSearch" method="post" class="form-horizontal">
											<fieldset>
												<legend>Twitter Form</legend>
												<div class="form-group">
													<label for="tweetData" class="col-lg-2 control-label">Topic:</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="pid"
															id="tweetData" placeholder="Topic" required>
													</div>
												</div>
												<div class="form-group">
													<div class="col-lg-10 col-lg-offset-2">
														<button type="submit" class="btn btn-primary">Submit</button>
														<input type="reset" class="btn btn-default" value="reset" />
													</div>
												</div>
											</fieldset>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="Venues">
						<p>
							<!-- Forms================================================== -->
						<div class="bs-docs-section">
							<div class="row">
								<div class="col-lg-12">
									<div class="well bs-component">
										<form action="venueSearch" method="post" class="form-horizontal">
											<fieldset>
												<legend>Venue Form</legend>
												<div class="form-group">
													<label for="tweetData" class="col-lg-2 control-label">Topic:</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="pid"
															id="tweetData" placeholder="Topic" required>
													</div>
												</div>
												<div class="form-group">
													<div class="col-lg-10 col-lg-offset-2">
														<button type="submit" class="btn btn-primary">Submit</button>
														<input type="reset" class="btn btn-default" value="reset" />
													</div>
												</div>
											</fieldset>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div id="twitterResults">
 			<jsp:include page="twitterSearch.jsp" />
		</div>
	</div>
	<div class="row">
		<div id="venueResults">
			<jsp:include page="venueSearch.jsp" />
		</div>
	</div>
</div>
<footer>
	<%@ include file="footer.html"%>
</footer>
</body>
</html>