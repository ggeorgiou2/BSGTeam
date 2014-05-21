<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="header.jsp"%>
<div class="col-lg-8 col-md-10 col-md-push-2">
	<div class="bs-docs-section">
		<h1>Tracking User: ${user}</h1>
		<form action='streams' method='post'>
			<button type='submit'>Stop Streaming</button>
		</form>
		<div class="row">
			<div class="well bs-component">
				<c:forEach var="stream" items="${streams}">
					<br><c:out value="${stream.venue}" />
					<c:out value="${stream.date}" /><br/>
				</c:forEach>
			</div>
		</div>
		<footer>
			<%@ include file="footer.html"%>
		</footer>
		</body>
		</html>