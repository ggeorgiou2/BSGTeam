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
								</tr>
							</thead>
							<tbody class="table-hover">
								<c:forEach var="venue" items="${venue_results}">
									<tr about="/">
										<td><c:out value="${venue.visitorName}" /></td>
										<td><c:out value="${venue.venueName}" /></td>
										<td><c:out value="${venue.url}" /></td>
										<td><c:out value="${venue.address}" /></td>
									</tr>
								</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
	</c:if>
</div>