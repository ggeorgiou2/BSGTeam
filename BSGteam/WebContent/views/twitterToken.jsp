			<h1>Hello, twitterToken!</h1>
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-12">
			<div class="well bs-component">
				<form action="twitterToken" id="twitterToken" method="post" class="form-horizontal">
					<fieldset>
						<legend>Please insert your Twitter Authentication Token</legend>
						<div class="form-group">
							<label for="token_access" class="col-lg-2 control-label">Token access:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="token_access"
									id="token_access" placeholder="Token access" required>
							</div>
						</div>
						<div class="form-group">
							<label for="token_secret" class="col-lg-2 control-label">Token secret:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="token_secret"
									id="token_secret" placeholder="Token secret">
							</div>
						</div>
						<div class="form-group">
							<label for="customer_key" class="col-lg-2 control-label">Customer key:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="customer_key"
									id="customer_key" placeholder="Customer key">
							</div>
						</div>
						<div class="form-group">
							<label for="customer_secret" class="col-lg-2 control-label">Customer secret:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="customer_secret"
									id="customer_secret" placeholder="Customer Secret">
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
