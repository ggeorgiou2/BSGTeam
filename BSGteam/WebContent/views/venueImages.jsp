<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<%@ include file="header.html"%>

<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCP0N_f7wpMqzijj1w_b2n5k6q3u4NKo_c&v=3.exp&sensor=false"></script>
<script>
	var url = document.location.toString();

	var firstsplit = url.split('lat=')[1];
	var formLat = firstsplit.split('&lng')[0];
	//alert(formLat);
	var formLng = url.split('lng=')[1];
	//alert(formLng);

	function initialize() {
		var myLatlng = new google.maps.LatLng(formLat, formLng);
		var mapOptions = {
			zoom : 14,
			center : myLatlng
		}
		var map = new google.maps.Map(document.getElementById('map-canvas'),
				mapOptions);

		var marker = new google.maps.Marker({
			position : myLatlng,
			map : map,
			draggable : true,
			animation : google.maps.Animation.DROP

		});
	}
	google.maps.event.addDomListener(window, 'load', initialize);
</script>

<div class="bs-docs-section">
	<div class="row">
		<div class="col-lg-9 col-md-12 col-md-push-2">
			<div class="bs-component">
				<h1><a href="javascript:history.back()"><button type="button"
						class="btn btn-default">Back</button></a>
				<c:if test="${empty images}">
					<p>No image for this venue</p>
				</c:if>
				<c:if test="${not empty images}">
					Venue Images</h1>
					<div class="row">
						<div id="carousel-example-generic" class="carousel slide"
							data-ride="carousel" style="width:50%">
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
									<img src="<c:out value="${image.url}" ></c:out>" style="height:450px; width:auto"/>
									<div class="carousel-caption">
										<c:out value="${loop.count}"></c:out>
									</div>
							</div>

							</c:forEach>
						</div>
						<!-- Controls -->
						<a class="left carousel-control" href="#carousel-example-generic"
							data-slide="prev"> <span
							class="glyphicon glyphicon-chevron-left"></span>
						</a> <a class="right carousel-control"
							href="#carousel-example-generic" data-slide="next"> <span
							class="glyphicon glyphicon-chevron-right"></span>
						</a>
					</div>
				</c:if>
				<h1>Venue location on map</h1>
				<div id="map-canvas"></div>
			</div>
		</div>
	</div>
</div>
<footer>
	<%@ include file="footer.html"%>
</footer>
</body>
</html>