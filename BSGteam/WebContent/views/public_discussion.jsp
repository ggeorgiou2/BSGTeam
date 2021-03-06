<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	if (session.getAttribute("twitterToken") != null) {
%>

<script>
window.onload = function(){
   document.getElementById("long").value = "";
   document.getElementById("lat").value = "";
  }
</script>

<!-- ============ Forms ============ -->
<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-12">
			<div class="well bs-component">
				<form action="twitter" method="post" class="form-horizontal">
					<fieldset>
						<legend>Input keywords or hashtags to find recent
							discussions on those topics. You can also limit this to a
							geographical location</legend>
						<div class="form-group">
							<label for="tweetData" class="col-lg-2 control-label">Topic:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="tweetData"
									id="tweetData" placeholder="Topic" required>
							</div>
						</div>
						 <div id="mapCanvas" style="width: 100%; height: 250px;"></div>
						<div id="markerStatus">
							<i>Click and drag the marker.</i>
						</div>
						<div id="address"></div>	
						<div class="form-group">
							<label for="lat" class="col-lg-2 control-label">Latitude:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control"
									pattern="([-/+]?[0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)|([0-9]+)"
									data-validation-pattern-message="Must write real number"
									id="lat" name="lat" placeholder="Latitude">
							</div>
						</div>
						<div class="form-group">
							<label for="long" class="col-lg-2 control-label">Longitude:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="long"
									pattern="([-/+]?[0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)|([0-9]+)"
									data-validation-pattern-message="Must write real number"
									id="long" placeholder="Longitude">
							</div>
						</div>
						<div class="form-group">
							<label for="area" class="col-lg-2 control-label">Radius:
								(in km):</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="area"
									pattern="([0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)|([0-9]+)"
									data-validation-pattern-message="Must write real number"
									id="area" placeholder="Area">
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
<%
	} else if (session.getAttribute("twitterToken") == null) {
%>
<jsp:include page="twitterToken.jsp" />
<%
	}
%>

<!-- ============ Results Table ============ -->
<div>
	<c:if test="${not empty statuses_result}">
		<script>
			setTimeout(function() {
				window.location.href = '#results';
			});
		</script>
	</c:if>
	<c:if test="${not empty statuses}">

		<div class="row" id="results">
			<div>
				<div class="row">
					<div class="col-md-10 col-md-push-1">
						<h1 align="center">List of Results</h1>
						<table
							class="table table-hover table-responsive table-bordered table-condensed">
							<thead>
								<tr>
									<th>Screen name</th>
									<th>Location</th>
									<th>Description</th>
									<th>Tweet</th>
									<th>Date</th>
									<th>Retweets</th>
								</tr>
							</thead>
							<tbody class="table-hover">
								<c:forEach var="status" items="${statuses}">
									<tr>
										<td><a
											href='timelines?screenname=<c:out value="${status.user.screenName}"/>'>@<c:out
													value="${status.user.screenName}" /></a>
											<p>
												<img src="<c:out value="${status.user.profileImageURL}" />">
											</p></td>
										<td><c:out value="${status.user.location}" /></td>
										<td><c:out value="${status.user.description}" /></td>
										<td><c:out value="${status.text}" /></td>
										<td><c:out value="${status.createdAt}" /></td>
										<td><c:choose>
												<c:when test="${fn:contains(status.text, 'RT @')}">
												No retweets
											</c:when>
												<c:when test="${status.retweetCount > 0 }">
													<a href='timelines?tweetID=<c:out value="${status.id}"/>'>
														Get Retweeters</a>
												</c:when>
												<c:otherwise>
												No retweets
											</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>
								<%-- <c:forEach var="queryInterface" items="${timelines}">
								<tr>
									<td>@<c:out value="${timeline.user.screenName}" /></td>
									<td>@<c:out value="${timeline.text}" /></td>
								</tr>
							</c:forEach> --%>

							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</c:if>
</div>
