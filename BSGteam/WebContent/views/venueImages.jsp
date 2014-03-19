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
				<c:if test="${empty images}">
   					<p>No image for this venue</p>
				</c:if>
				<c:if test="${not empty images}">
					<h1>Image(s) for this venue</h1>
					<div class="row">
						<c:forEach var="image" items="${images}">
							<p>
								<img src="<c:out value="${image.url}" /> " height="480" width="auto">
							</p>
						</c:forEach>
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