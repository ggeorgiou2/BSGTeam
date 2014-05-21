			<h1>Hello, foursquareToken!</h1>
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-12">
			<div class="well bs-component">
				<form action=foursquareToken id="foursquareToken" method="post" class="form-horizontal">
					<fieldset>
						<legend>Please insert your Foursquare Token</legend>
						<div class="form-group">
							<label for="clientID" class="col-lg-2 control-label">Client ID:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="clientID"
									id="clientID" placeholder="Client ID" required>
							</div>
						</div>
						<div class="form-group">
							<label for="clinetSec" class="col-lg-2 control-label">Client Secret:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="clinetSec"
									id="clinetSec" placeholder="Client Secret">
							</div>
						</div>
						<div class="form-group">
							<label for="redirectURL" class="col-lg-2 control-label">Redirect URL:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="redirectURL"
									id="redirectURL" placeholder="Redirect URL" required>
							</div>
						</div>
						<div class="form-group">
							<label for="accessToken" class="col-lg-2 control-label">Access Token:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="accessToken"
									id="accessToken" placeholder="Access Token">
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
