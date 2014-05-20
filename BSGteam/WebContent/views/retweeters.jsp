<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<%@ include file="header.jsp"%>

<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-6 col-md-10 col-md-push-3">
			<div class="bs-component">
				<a href="javascript:history.back()"><button type="button"
						class="btn btn-default">Back</button></a>
				<c:if test="${empty retweeters}">
   					No Retweets
				</c:if>
				<c:if test="${not empty retweeters}">
					<h1>
						List of recent users who retweeted: </h1>
						<h2><c:out value="${tweet}"/>
					</h2>
					<div class="row">
						<table
							class="table table-bordered table-striped table table-hover">
							
							<tbody>
								<c:forEach var="retweet" items="${retweeters}">
									<tr>
										<td>@<c:out value="${retweet.user.screenName}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
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