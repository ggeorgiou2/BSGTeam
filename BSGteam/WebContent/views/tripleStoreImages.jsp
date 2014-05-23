<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<%@ include file="header.jsp"%>



<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-9 col-md-12 col-md-push-2">
			<div class="bs-component">
				<div class="row">

					<h1>
						<a href="javascript:history.back()"><button type="button"
								class="btn btn-default">Back</button></a>
					</h1>
					<c:if test="${empty images}">
						<h3>No image for this venue</h3>
					</c:if>

					<c:if test="${not empty images}">
						<div id="carousel-example-generic" class="carousel slide"
							data-ride="carousel" style="width: 100%; float: left;">

							<!-- Wrapper for slides -->
							<div class="carousel-inner">
								<h1 align="center">Venue Images</h1>

								<c:forEach var="image" varStatus="loop" items="${images}">
									<c:choose>
										<c:when test="${loop.count > 1}">
											<div class="item">
										</c:when>
										<c:otherwise>
											<div class="item active">
										</c:otherwise>
									</c:choose>
									<div id="carousel_image">
										<img src="<c:out value="${image.url}" ></c:out>" />
									</div>
									<div class="carousel-caption">
										<c:out value="${loop.count}"></c:out>
										/
										<c:out value="${fn:length(images)}"></c:out>
									</div>
							</div>

							</c:forEach>
						</div>
						<!-- Controls -->
						<a class="left carousel-control" href="#carousel-example-generic"
							data-slide="prev"> <span
							class="glyphicon glyphicon-chevron-left"></span>
						</a>
						<a class="right carousel-control" href="#carousel-example-generic"
							data-slide="next"> <span
							class="glyphicon glyphicon-chevron-right"></span>
						</a>
					</c:if>
				</div>

		</div>
	</div>
</div>
</div>
<footer>
	<%@ include file="footer.html"%>
</footer>
</body>
</html>