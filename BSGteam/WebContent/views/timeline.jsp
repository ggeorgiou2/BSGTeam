<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<%@ include file="header.html"%>

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
					<h1>
						List of Results of @<%=request.getAttribute("user")%>
					</h1>
					<div class="row">
						<table
							class="table table-bordered table-striped table table-hover">
							<thead>
								<tr>
									<th>Tweets</th>
									<th>Date</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="timeline" items="${timelines}">
									<tr>
										<td><c:out value="${timeline.text}" /></td>
										<td><c:out value="${timeline.createdAt}"/></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

						<div class="row" id="userVisitsResults">
							<div class="well bs-component">
								<h1>List of Venues</h1>
								<div class="row">
									<div class="col-md-10">
										<table
											class="table table-hover table-responsive table-bordered table-condensed">
											<thead>
												<tr>
													<th>Name (Click to view Images)
													<th>Address (Click to view on map)
													<th>URL
													<th>Categories
													<th>Description
												</tr>
											</thead>
											<tbody class="table-hover">
												<c:forEach var="venue" items="${userVisits}">
													<tr>
														<td><a href='venue?id=<c:out value="${venue.id}"/>'><c:out
																	value="${venue.name}" /></a></td>
														<td><a
															href='views/venuemap.html?lat=<c:out value="${venue.location.lat}"/>
											&lng=<c:out value="${venue.location.lng }"/>'>
																<c:if test="${empty venue.location.address}">
																	<c:out
																		value="${venue.location.lat}, ${venue.location.lng}"></c:out>
																</c:if> <c:if test="${not empty venue.location.address}">
																	<c:out value="${venue.location.address}" />
																</c:if>
														</a></td>
														<td><a href='<c:out value="${venue.url}"/>'><c:out
																	value="${venue.url}" /></a></td>
														<td><c:forEach var="category"
																items="${venue.categories}">
																<c:out value="${category.name}"></c:out>
															</c:forEach></td>
														<td><c:out value="${venue.stats.usersCount}"></c:out>
															users have been here</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
				</c:if>
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