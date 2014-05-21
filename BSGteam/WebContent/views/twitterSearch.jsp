<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="bs-docs-section"
	xmlns:intelligentWeb="https://sites.google.com/site/sheffieldbash/home/web2.rdfs/"
	xmlns:foaf="http://xmlns.com/foaf/0.1/">
	<c:if test="${not empty user_results}">
		<script>
			setTimeout(function() {
				$('#mytab a[href=#twitter]').tab('show');
				window.location.href = '#twitterResults'
			});
		</script>
		<div class="row">
			<div class="col-lg-8 col-md-10 col-md-push-2">
				<h1 align="center">List of Users</h1>
				<div class="row">
					<div class="col-md-10 col-md-push-1">
						<table
							class="table table-hover table-responsive table-bordered table-condensed">
							<thead>
								<tr>
									<th><b>Name</b>
									<th><b>Twitter Id</b>
									<th><b>Location</b>
									<th><b>Description</b>
								</tr>
							</thead>
							<tbody class="table-hover">
								<c:forEach var="user" items="${user_results}">
									<tr about="/">
										<td property="foaf:name"><c:out value="${user.userName}" /></td>
										<td><c:out value="${user.id}" /></td>
										<td property="intelligentWeb:location"><c:out
												value="${user.location}" /></td>
										<td property="intelligentWeb:description"><c:out
												value="${user.description}" /></td>
									</tr>
								</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
	</c:if>
</div>