<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<%@ include file="header.jsp"%>

<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-6 col-md-10 col-md-push-3">
			<div class="bs-component">
				<a href="javascript:history.back()"><button type="button"
						class="btn btn-default">Back</button></a>
				<c:if test="${empty timelines}">
   					Empty timeline.
				</c:if>
				<c:if test="${not empty timelines}">
					<h1 align="center">
						User timeline for @
						<c:out value="${user}"></c:out>

					</h1>

					<div class="row">
						<table class="table table-striped table table-hover">
							<thead>
								<tr>
									<th>Tweets</th>
									<th>Time</th>
									<th>Retweeted</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="timeline" items="${timelines}">
									<tr>
										<td><img
											src="<c:out value="${timeline.user.profileImageURL}" />">
											<c:out value="${timeline.text}" /></td>
										<td><c:out value="${timeline.createdAt}" /></td>
										<td><c:choose>
												<c:when test="${fn:contains(timeline.text, 'RT @')}">
												No retweets
											</c:when>
												<c:when test="${timeline.retweetCount > 0 }">
													<a href='timelines?tweetID=<c:out value="${timeline.id}"/>'>
														Get Retweeters</a>
												</c:when>
												<c:otherwise>
												No retweets
											</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="row" id="userVisitsResults">
						<div>
							<div class="row">
								<h1 align="center">
									List of Venues that @
									<c:out value="${user}"></c:out>
									visited recently
								</h1>
								<table
									class="table table-hover table-responsive table-condensed">
									<thead>
										<tr>
											<th>Name (Click to view Images)</th>
											<th>Address (Click to view on map)</th>
											<th>URL</th>
											<th>Categories</th>
											<th>Description</th>
											<th>Time</th>
										</tr>
									</thead>
									<tbody class="table-hover">
										<c:forEach var="venue" items="${userVisits}">
											<tr>
												<td><a
													href='venue?id=<c:out value="${venue.value.id}"/>
											&lat=<c:out value="${venue.value.location.lat}"/>
											&lng=<c:out value="${venue.value.location.lng }"/>'><c:out
															value="${venue.value.name}" /></a></td>
												<td><c:if test="${empty venue.value.location.address}">
														<c:out
															value="${venue.value.location.lat}, ${venue.value.location.lng}"></c:out>
													</c:if> <c:if test="${not empty venue.value.location.address}">
														<c:out value="${venue.value.location.address}" />
													</c:if> </a></td>
												<td><a href='<c:out value="${venue.value.url}"/>'><c:out
															value="${venue.value.url}" /></a></td>
												<td><c:forEach var="category"
														items="${venue.value.categories}">
														<c:out value="${category.name}"></c:out>
													</c:forEach></td>
												<td><c:out value="${venue.value.stats.usersCount}"></c:out>
													users have been here</td>
												<td><c:out value="${venue.key}"></c:out></td>

											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<h1>
						List of users that @
						<c:out value="${user}"></c:out>
						has been in contact with recently
					</h1>
					<h2>Retweeted:</h2>
					<div class="row">
						<table
							class="table table-bordered table-striped table table-hover">

							<tbody>
								<c:forEach var="contact" items="${allcontacts}">
									<tr>
										<td>@<c:out value="${contact}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<c:if test="${not empty allcontacts2}">
					<h2>Was retweeted by</h2>
					<div class="row">
						<table
							class="table table-bordered table-striped table table-hover">

							<tbody>
								<c:forEach var="contact" items="${allcontacts2}">
									<tr>
										<td>@<c:out value="${contact}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					</c:if>
				</c:if>
			</div>
		</div>
	</div>
</div>
<footer>
	<%@ include file="footer.html"%>
</footer>
</body>
</html>