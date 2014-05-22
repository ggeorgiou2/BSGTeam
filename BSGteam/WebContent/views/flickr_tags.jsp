<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!-- ============ Forms ============ -->
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-12">
			<div class="well bs-component">
				<div id="wrapper">
					<div id="left_sidebar">
						<form action="" method="post" class="form-horizontal my-class">
							<fieldset>
								<legend>Search for pictures by keywords</legend>
								<div class="form-group">
									<label for="tag" class="col-lg-2 control-label">Keywords:</label>
									<div class="col-lg-10">
										<input id="keywords" class="form-control"
											placeholder="Enter keywords" required>
									</div>
								</div>
								<div class="form-group">
									<label for="tag" class="col-lg-2 control-label">Instagram API Key:</label>
									<div class="col-lg-10">
										<input id="instagram_api" class="form-control"
											placeholder="Enter Instagram API Key">
									</div>
								</div>
								<div class="form-group">
									<div class="col-lg-10 col-lg-offset-2">
										<button id="submit" class="btn btn-primary">Search</button>
										<input type="reset" class="btn btn-default" value="Reset" />
									</div>
								</div>

							</fieldset>
						</form>
					</div>
				</div>
				<div id="instafeed"></div> 
			</div>
		</div>
	</div>
</div>

<!-- ============ Results Table ============ -->
<div>
<script src="js/instafeed.min.js"></script>
<script	src="js/flicker.js"></script>

	<div class="clear">
		<!-- empty -->
	</div>

</div>