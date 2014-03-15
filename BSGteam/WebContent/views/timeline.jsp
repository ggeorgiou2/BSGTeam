<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<%@ include file="header.html"%>

<div class="bs-docs-section">
	<div class="col-lg-6 col-md-10 col-md-push-3">
		<div class="bs-component">
<a href="javascript:history.back()"><button type="button" class="btn btn-default">Back</button></a>
			<c:if test="${empty timelines}">
   				var1 is empty or null.
				</c:if>
			<c:if test="${not empty timelines}">
				<h1>List of Results of @<div value="${timeline.user.screenName}"></h1>
				<div class="row">
					<table class="table table-bordered table-striped table table-hover">
						<thead>
							<tr>
								<th>Tweets
							</tr>
						</thead>
						<tbody>
							<c:forEach var="timeline" items="${timelines}">
								<tr>
									<td><c:out value="${timeline.text}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
		</div>
		<%@ include file="footer.html"%>
	</div>
</div>
</body>
</html>