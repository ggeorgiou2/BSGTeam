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
					<h1>Venue Images</h1>
					<div class="row">
						<div id="carousel-example-generic" class="carousel slide"
							data-ride="carousel">
							<!-- Wrapper for slides -->
							<div class="carousel-inner">
								<c:forEach var="image" varStatus="loop" items="${images}">
									<c:choose>
										<c:when test="${loop.count > 1}">
											<div class="item">
										</c:when>
										<c:otherwise>
											<div class="item active">
										</c:otherwise>
									</c:choose>
									<img src="<c:out value="${image.url}"></c:out>" />
									<div class="carousel-caption">
										<c:out value="${loop.count}"></c:out>
									</div>
							</div>

							</c:forEach></div>
							<!-- Controls -->
							<a class="left carousel-control" href="#carousel-example-generic"
								data-slide="prev"> <span
								class="glyphicon glyphicon-chevron-left"></span>
							</a> <a class="right carousel-control"
								href="#carousel-example-generic" data-slide="next"> <span
								class="glyphicon glyphicon-chevron-right"></span>
							</a>
						</div>
					</div>
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