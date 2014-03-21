<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="col-lg-6 col-md-10 col-md-push-3">
<!-- 	<div class="row"> -->
<%-- 		<c:if test="${empty statuses}"> --%>
<!--     		No Results to display -->
<%-- 		</c:if> --%>
<!-- 	</div> -->
	<c:if test="${not empty statuses}">
		<script>
			setTimeout(function() {
				window.location.href = '#results';
			});
		</script>
		<div class="row">
			<div class="well bs-component">
				<h1>List of Results</h1>
				<div class="row">
					<div class="col-md-10">
						<table class="table table-hover table-responsive table-bordered table-condensed">
							<thead>
								<tr>
									<th>Screen name
									<th>Location
									<th>Description
									<th>Tweet</th>
									<th>Retweets</th>
								</tr>
							</thead>
							<tbody class="table-hover">
								<c:forEach var="status" items="${statuses}">
									<tr>
										<td>
										<a
											href='timelines?screenname=<c:out value="${status.user.screenName}"/>'>@<c:out
													value="${status.user.screenName}" /></a>
										<p><img
											src="<c:out value="${status.user.profileImageURL}" />">
										</p>
										</td>
										<td><c:out value="${status.user.location}" /></td>
										<td><c:out value="${status.user.description}" /></td>
										<td><c:out value="${status.text}" /></td>
										<td>
										<c:choose>
										    <c:when test="${fn:contains(status.text, 'RT @')}">
												No retweets
											</c:when>
											<c:when test="${status.retweetCount > 0 }">
											<a href='timelines?tweetID=<c:out value="${status.id}"/>'>
											Get Retweets</a>
											</c:when>
											<c:otherwise>
												No retweets
											</c:otherwise>
										</c:choose>
										</td>
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
