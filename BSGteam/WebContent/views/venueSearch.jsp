<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="bs-docs-section"
	xmlns:intelligentWeb="https://sites.google.com/site/sheffieldbash/home/web2.rdfs/"
	xmlns:foaf="http://xmlns.com/foaf/0.1/">
	<c:if test="${not empty venue_results}">
		<script>
			setTimeout(function() {
				$('#mytab a[href=#Venues]').tab('show');
				window.location.href = '#venueResults'
			});
		</script>
		<div class="row">
			<div class="col-lg-8 col-md-10 col-md-push-2">
				<h1 align="center">List of Venues</h1>
				<div class="row">
					<div class="col-md-10 col-md-push-1">
						<table
							class="table table-hover table-responsive table-bordered table-condensed">
							<thead>
								<tr>
									<th><b>Visitor Name</b>
									<th><b>Venue Name</b>
									<th><b>Url</b>
									<th><b>Address</b>
									<th><b>Description</b>
									<th><b>Time checkin</b>
									<th><b>Categories</b>
									<th><b>Pictures</b>
								</tr>
							</thead>
							<tbody class="table-hover">
								<c:forEach var="venue" items="${venue_results}">
									<tr about="c:out value=${venue.uri}">
										
										<td property="intelligentWeb:visitorName"><c:out
												value="${user.visitorName}" /></td>
										<td property="intelligentWeb:venueName"><c:out
												value="${user.venueName}" /></td>
										<td property="intelligentWeb:url"><c:out
												value="${user.url}" /></td>
										<td property="intelligentWeb:address"><c:out
												value="${user.address}" /></td>
										<td property="intelligentWeb:description"><c:out
												value="${user.description}" /></td>
										<td property="intelligentWeb:checkinTime"><c:out
												value="${user.checkinTime}" /></td>
										<td property="intelligentWeb:venueCategory">
											<c:forEach var="category"
												items="${venue.value.categories}">
												<c:out value="${category.name}"></c:out>
											</c:forEach>
										</td>
										<td property="intelligentWeb:venuePhoto">
											<c:forEach var="photo"
												items="${venue.Photos}">
												<c:out value="${photo}"></c:out>
											</c:forEach>
										</td>
										
									</tr>
								</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
	</c:if>
</div>