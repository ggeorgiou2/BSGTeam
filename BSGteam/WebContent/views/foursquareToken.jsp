			<h1>Hello, GuestUser!</h1>
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-12">
			<div class="well bs-component">
				<form action=foursquareToken id="foursquareToken" method="post" class="form-horizontal">
					<fieldset>
						<legend>Please insert your Foursquare Token</legend>
						<div class="form-group">
							<label for="fsAPI" class="col-lg-2 control-label">fsAPI:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="fsAPI"
									id="fsAPI" placeholder="fsAPI" required>
							</div>
						</div>
						<div class="form-group">
							<label for="authToken" class="col-lg-2 control-label">token_secret:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="authToken"
									id="authToken" placeholder="authToken">
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
