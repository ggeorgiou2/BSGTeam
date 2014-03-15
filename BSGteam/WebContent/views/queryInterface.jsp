<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="header.html"%>

<%-- <c:if test="${empty kokos">
    var1 is empty or null.
</c:if>
		<c:if test="${not empty kokos">		
		</c:if> --%>
			<div class="alert alert-dismissable alert-warning">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<h4>Warning!</h4>
				<p>fdsf</p>
			</div>

<!-- Navs
      ================================================== -->
<div class="bs-docs-section">

	<div class="row">
		<div class="col-lg-6 col-md-10 col-md-push-3">
			<!--             <h2 id="nav-tabs">Tabs</h2>
 -->
			<div class="bs-component">
				<ul class="nav nav-tabs" style="margin-bottom: 15px;">
					<li class="active"><a href="#Discussion" data-toggle="tab">Discussion</a></li>
					<li><a href="#Venues" data-toggle="tab">Venues</a></li>
					<li><a href="#timeline" data-toggle="tab">Timeline</a></li>
					<li><a href="#results" data-toggle="tab">Results</a></li>
					</li>
				</ul>
				<div class="container">
            
                <input  type="text" placeholder="click to show datepicker"  id="datepicker">
        </div>
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane fade active in" id="Discussion">
						<p>

							<!-- Forms
      ================================================== -->
						<div class="bs-docs-section">
							<div class="row">
								<div class="col-lg-12">
									<div class="well bs-component">
										<form action="twitter" method="post" class="form-horizontal">
											<fieldset>
												<legend>Discussion Form jsp</legend>
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
															id="lat" placeholder="Latitude" required>
													</div>
												</div>
												<div class="form-group">
													<label for="long" class="col-lg-2 control-label">Longitude:</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="long"
															id="long" placeholder="Longitude" required>
													</div>
												</div>
												<div class="form-group">
													<label for="area" class="col-lg-2 control-label">Area:
														(in km):</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" name="area"
															id="area" placeholder="Area" required>
													</div>
												</div>
												<div class="form-group">
													<label for="select" class="col-lg-2 control-label">Selects</label>
													<div class="col-lg-10">
														<br> <select multiple="" class="form-control">
															<option>Sheffield</option>
															<option>London (51.5286416,-0.1015986)</option>
															<option>Manchester</option>
															<option>Leeds</option>
															<option>York</option>
														</select>
													</div>
												</div>
												<div class="form-group">
													<div class="col-lg-10 col-lg-offset-2">
														<button type="submit" class="btn btn-primary">Submit</button>
														<button class="btn btn-default">Reset</button>
													</div>
												</div>
											</fieldset>
										</form>
									</div>
								</div>
							</div>
						</div>
						</p>
					</div>
					<div class="tab-pane fade" id="Venues">
						<p>
							<script>
								$('#sandbox-container input').datepicker({});
							</script>
							<!-- Forms
      ================================================== -->
						<div class="bs-docs-section">
							<div class="row">
								<div class="col-lg-12">
									<div class="well bs-component">
										<form class="form-horizontal">
											<fieldset>
												<legend>Venues Form</legend>
												<div class="form-group">
													<label for="inputEmail" class="col-lg-2 control-label">Email</label>
													<div class="col-lg-10">
														<input type="text" class="form-control" id="inputEmail"
															placeholder="Email">
													</div>
												</div>
												<div class="form-group">
													<label for="inputPassword" class="col-lg-2 control-label">Password</label>
													<div class="col-lg-10">
														<input type="password" class="form-control"
															id="inputPassword" placeholder="Password">
													</div>
												</div>
												<div class="form-group">
													<label for="select" class="col-lg-2 control-label">Selects</label>
													<div class="col-lg-10">
														<br> <select multiple="" class="form-control">
															<option>Sheffield</option>
															<option>London</option>
															<option>Manchester</option>
															<option>Leeds</option>
															<option>York</option>
														</select>
													</div>
												</div>
												<div class="form-group">
													<div class="col-lg-10 col-lg-offset-2">
														<button type="submit" class="btn btn-primary">Submit</button>
														<button class="btn btn-default">Reset</button>
													</div>
												</div>
											</fieldset>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="results123">
						<p>

							<!-- Forms
      ================================================== -->
						<div class="bs-docs-section">
							<div class="row">
								<div class="col-lg-12">
									<div class="well bs-component">
										<div class="row">
											<div class="col-lg-12">
												<div class="well bs-component">
													<jsp:include page="timeline.jsp" />

												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="timeline">
						<p>
							<!-- Forms
      ================================================== -->
						<div class="bs-docs-section">
							<div class="row">
								<div class="col-lg-12">
									<div class="well bs-component"></div>
								</div>
							</div>
						</div>
						</p>
					</div>
				</div>
			</div>
			<div id="results" >
			<jsp:include page="results.jsp" />
			</div>
			<%@ include file="footer.html"%>
</html>