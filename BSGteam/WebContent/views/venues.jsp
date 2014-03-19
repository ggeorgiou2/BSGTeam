<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="col-lg-6 col-md-10 col-md-push-3">
	<!-- 	<div class="row"> -->
	<%-- 		<c:if test="${empty venues}"> --%>
	<!--     		No Venues to display -->
	<%-- 		</c:if> --%>
	<!-- 	</div> -->
	<c:if test="${not empty venues}">
		<script>
			setTimeout(function() {
				$('#mytab a[href=#Venues]').tab('show');

				window.location.href = '#venueResults'
			});
		</script>
		<div class="row">
			<div class="well bs-component">
				<h1>List of Venues</h1>
				<div class="row">
					<div class="col-md-10">
						<table
							class="table table-hover table-responsive table-bordered table-condensed">
							<thead>
								<tr>
									<th>Venue name
									<th>Address
									<th>URL
								</tr>
							</thead>
							<tbody class="table-hover">
								<c:forEach var="venue" items="${venues}">
									<tr>
										<td><a href='venue?id=<c:out value="${venue.id}"/>'><c:out
													value="${venue.name}" /></a></td>
										<td><c:out value="${venue.location.address}" /></td>
										<td><a href='<c:out value="${venue.url}"/>'><c:out
													value="${venue.url}" /></a></td>
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

