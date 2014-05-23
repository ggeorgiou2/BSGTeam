		function initialize(searchLat, searchLon) {
			var mapOptions = {
				center : new google.maps.LatLng(searchLat, searchLon),
				zoom : 11,
				mapTypeId : google.maps.MapTypeId.ROADMAP
			};
			var map = new google.maps.Map(
					document.getElementById("map-canvas"), mapOptions);
			var marker = new google.maps.Marker();

			return map;
		}

		function getFlickrPhotos(map, flickrApi, searchLat, searchLon, hashkey) {

			var FLICKR_API_KEY = flickrApi;

			var searchUrl = "http://api.flickr.com/services/rest/?method=flickr.photos.search&";

			var searchReqParams = {
				'api_key' : FLICKR_API_KEY,
				'tags' : hashkey,
				'has_geo' : true,
				'lat' : searchLat,
				'lon' : searchLon,
				'accuracy' : 15,
				'format' : 'json',
				'safe_search' : 1,
				'privacy_filter' : 1,
				'per_page' : 10
			}

			$.ajax({
				type : 'GET',
				url : searchUrl,
				dataType : 'jsonp',
				cache : true,
				crossDomain : true,
				jsonp : false,
				jsonpCallback : 'jsonFlickrApi',
				data : searchReqParams,
				success : function(data) {

					if (data.photos.photo.length > 0) {
						getAndMarkPhotos(data.photos);
						$('#warning').hide();
					} else {
						console.log(data.photos);
						$('#warning').show();
					}

				}
			}).fail(
					function(jqXHR, textStatus, errorThrown) {
						console.log('req failed');
						console.log('textStatus: ', textStatus, ' code: ',
								jqXHR.status);
					});

			function getAndMarkPhotos(photos) {
				var numPhotos = photos.photo.length;
				for (var i = 0; i < numPhotos; i++) {
					var photo = photos.photo[i];
					getPhotoLocation(photo.id, photo.secret, photo.farm,
							photo.server, photo.title);
				}
			}

			function getPhotoLocation(photoId, secret, farm, server, title) {
				var photoLocUrl = "http://api.flickr.com/services/rest/?method=flickr.photos.geo.getLocation&";

				var photoParams = {
					'api_key' : FLICKR_API_KEY,
					'photo_id' : photoId
				}

				$.ajax(
						{
							type : 'GET',
							url : photoLocUrl,
							async : false,
							cache : true,
							crossDomain : true,
							dataType : 'xml',
							data : photoParams,
							success : function(data) {
								var location = $(data).find('location')[0];
								var photoLat = $(location).attr('latitude');
								var photoLon = $(location).attr('longitude');
								addOverlay(photoLat, photoLon, photoId, secret,
										farm, server, title, map);

							}
						}).fail(
						function(jqXHR, textStatus, errorThrown) {
							console.log('req failed ', jqXHR);
							console.log('textStatus: ', textStatus, ' code: ',
									jqXHR.status);
						});

			}

			function addOverlay(lat, long, text, secret, farm, server, title,
					map) {
				var m = 'http://farm6.static.flickr.com/5082/13934534836_e2cdbf90a5_m.jpg';
				var myLatlng = new google.maps.LatLng(lat, long);
				var marker = new google.maps.Marker({
					position : myLatlng,
					animation : google.maps.Animation.DROP,
					title : title

				}),

				infowindow = new google.maps.InfoWindow(
						{
							// content: "<div><img width='254' height='355' src='http://www.hyperpac.de/wp-content/uploads/2009/09/255px-Excitebike_cover.jpg'</div>"

							content : "<div><h3>"
									+ title
									+ "</h3><img width='254' height='355' src='http://farm"
									+ farm + ".static.flickr.com/" + server
									+ "/" + text + "_" + secret
									+ "_m.jpg'</div>"
						});

				function toggleBounce() {
					if (marker.getAnimation() != null) {
						marker.setAnimation(null);
					} else {
						marker.setAnimation(google.maps.Animation.BOUNCE);
					}
				}

				google.maps.event.addListener(marker, 'click', function() {
					toggleBounce();
					infowindow.open(map, marker);
					setTimeout(toggleBounce, 1500);
				});

				marker.setMap(map);
				//map.setCenter(myLatLng);
			}
		}

		$(function() {
			$('.your-class').submit(
					function() {
						var flickrApiKey = $('#key').val();
						var searchLat = $('#lat').val();
						var searchLon = $('#long').val();
						var hashKey = $('#tag').val();
						var googleMap = initialize(searchLat, searchLon);
						getFlickrPhotos(googleMap, flickrApiKey, searchLat,
								searchLon, hashKey);
						return false;
					})
		});