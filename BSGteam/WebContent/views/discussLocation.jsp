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
	<c:if test="${not empty words}">
		<script>
			setTimeout(function() {
				$('#mytab a[href=#LocationKeywords]').tab('show');

				window.location.href = '#discussionLocation'
			});
		</script>
		<div class="row">
			<div class="well bs-component">
				<h1>List of Frequent Keywords</h1>
				<div class="row">
					<div class="col-md-10">
						<table class="table table-hover table-responsive table-bordered table-condensed">
							<thead>
								<tr>
									<th>Word
									<th>Frequency
								</tr>
							</thead>
							<tbody class="table-hover">
								<c:forEach var="word" items="${words}">
									<tr>
										<td><c:out value="${word.key}" /></td>
										<td><c:out value="${word.value}" /></td>
										
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

