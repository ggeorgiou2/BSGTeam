			<h1>Hello, foursquareToken!</h1>
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-12">
			<div class="well bs-component">
				<form action=foursquareToken id="foursquareToken" method="post" class="form-horizontal">
					<fieldset>
						<legend>Please insert your Foursquare Token</legend>
						<div class="form-group">
							<label for="clientID" class="col-lg-2 control-label">clientID:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="clientID"
									id="clientID" placeholder="clientID" required>
							</div>
						</div>
						<div class="form-group">
							<label for="clinetSec" class="col-lg-2 control-label">clinetSec:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="clinetSec"
									id="clinetSec" placeholder="clinetSec">
							</div>
						</div>
						<div class="form-group">
							<label for="redirectURL" class="col-lg-2 control-label">redirectURL:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="redirectURL"
									id="redirectURL" placeholder="redirectURL" required>
							</div>
						</div>
						<div class="form-group">
							<label for="accessToken" class="col-lg-2 control-label">accessToken:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="accessToken"
									id="accessToken" placeholder="accessToken">
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
