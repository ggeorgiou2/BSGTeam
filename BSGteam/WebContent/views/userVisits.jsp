<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="col-lg-6 col-md-10 col-md-push-3">
<!-- 	<div class="row"> -->
<%-- 		<c:if test="${empty venues}"> --%>
<!--     		No Venues to display -->
<%-- 		</c:if> --%>
<!-- 	</div> -->
	<c:if test="${not empty userVisits2}">
	<script>
			setTimeout(function() {
				$('#mytab a[href=#UserVisits]').tab('show');
				window.location.href = '#userVisitsResults';
			});
		</script>
	<c:forEach var="venue" items="${userVisits2}">
		<h1><c:out value="${venue}" />	</h1>
	</c:forEach>	
	</c:if>
	
	<c:if test="${not empty userVisits}">
		<script>
			setTimeout(function() {
				$('#mytab a[href=#UserVisits]').tab('show');
				window.location.href = '#userVisitsResults';
			});
		</script>
		<div class="row">
			<div class="well bs-component">
				<h1>List of Venues</h1>
				<div class="row">
					<div class="col-md-10">
						<table class="table table-hover table-responsive table-bordered table-condensed">
							<thead>
								<tr>
									<th>Venue name
									<th>Address
									<th>URL
									<th>Description
								</tr>
							</thead>
							<tbody class="table-hover">
								<c:forEach var="venue" items="${userVisits}">
									<tr>
										<td><c:out value="${venue.name}" /></td>
										<td><c:out value="${venue.location.address}" /></td>
										<td><c:out value="${venue.url}" /></td>
										<td><c:out value="${venue.id}" /></td>
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

