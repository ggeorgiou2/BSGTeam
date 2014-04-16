<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<%@ include file="header.html"%>


<script>
  function onLoad()
  {
   $('#loading_bar').hide();
   $('#results_table').hide();
   $('#venueLoading').hide();
  }
  window.onload=onLoad;
</script>
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-7 col-md-10 col-md-push-3">
			<h1>Additional Features</h1>
			<div class="bs-component">
				<ul class="nav nav-pills" style="margin-bottom: 15px;" id="mytab">
					<li class="active"><a href="#twitter" data-toggle="tab"> find User</a></li>
					<li><a href="#Venues" data-toggle="tab">Find More options</a></li>
				</ul>
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane fade active in" id="twitter">
						<jsp:include page="twitterSearch.jsp" />
					</div>	
					
					<div class="tab-pane fade" id="Venues">
						<jsp:include page="venueSearch.jsp" />
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<footer>
	<%@ include file="footer.html"%>
</footer>
</body>
</html>