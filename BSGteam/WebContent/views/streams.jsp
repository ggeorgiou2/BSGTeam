<%@ include file="header.jsp"%>
 <div class="col-lg-8 col-md-10 col-md-push-2">
<div class="bs-docs-section">
	<h1>Trucking User: ${user}</h1>
	<form action='streams' method='post'>
		<button type='submit'>Stop Streaming</button>
	</form>
	<div class="row">
			<div class="well bs-component">

				<p>venueDate: ${venueDate}</p>

				<p>streams: ${streams}</p>

				<%-- <c:forEach var="stream" items="${streams}">
					<c:out value="${stream.getVenue()}" />
					<c:out value="${stream.getDate()}" />
				</c:forEach> --%>

			</div>
		</div>
	<footer>
		<%@ include file="footer.html"%>
	</footer>
	</body>
	</html>