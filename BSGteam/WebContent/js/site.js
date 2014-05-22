
$(function() {

	var lat = "";

	var bond = "";

	var str = "";
	var newstr = "";
	var phone = "";
	var rating = "";

	var icon = "";
	var address = "";

	var formLat = "";
	var formLng = "";

	$("#query").click(function() {
		$(this).val("");
	});

	$("#lat").click(function() {
		$(this).val("");
	});

	$("#lng").click(function() {
		$(this).val("");
	});

	$("#query").blur(function() {
		if ($(this).val() == "") {
			$(this).val("Location Name");
		}

		if ($(this).val() != "Location Name") {
			$(this).addClass("focus");
		} else {
			$(this).removeClass("focus");
		}
	});

	$("#lat").blur(function() {
		if ($(this).val() == "") {
			$(this).val("Latitude");
		}

		if ($(this).val() != "Latitude") {
			$(this).addClass("focus");
		} else {
			$(this).removeClass("focus");
		}
	});

	$("#lng").blur(function() {
		if ($(this).val() == "") {
			$(this).val("longititude");
		}

		if ($(this).val() != "longititude") {
			$(this).addClass("focus");
		} else {
			$(this).removeClass("focus");
		}
	});

	$(window).load(function(event) {
		event.preventDefault();
		if (!lat) {
			navigator.geolocation.getCurrentPosition(getLocation);
		} else {
			getPlace();
		}
	});

	function getLocation(location) {
		lat = location.coords.latitude;
		lng = location.coords.longitude;
		getPlace();
	}

	function getPlace() {
		var url = document.location.toString();
		// alert(url.split('lng')[1]);

		formLat = 53.83;
		formLng = url.split('lng=')[1];

		$
				.ajax({
					type : "GET",
					url : "https://api.foursquare.com/v2/venues/explore?ll="
							+ formLat
							+ ","
							+ formLng
							+ "&client_id=KTSRNGJZY4BGFSZAQYGKP2BBTZGGJAMXWKQSYTOSTV5WC31H&client_secret=4KYFXNFEMT5RAIO3DEXMBC52ALUQG3AIXJHGBDBNYISGTO1H&v=20130619&query="
							+ $("#query").val() + "",
					success : function(data) {
						$("#venues").show();
						var dataobj = data.response.groups[0].items;
						$("#venues").html("");

						// Rebuild the map using data.
						var myOptions = {
							zoom : 10,
							center : new google.maps.LatLng(formLat, formLng),
							mapTypeControl : true,
							mapTypeControlOptions : {
								style : google.maps.MapTypeControlStyle.DROPDOWN_MENU
							},

							zoomControl : true,
							zoomControlOptions : {
								style : google.maps.ZoomControlStyle.SMALL
							}
						// mapTypeId: google.maps.MapTypeId.ROADMAP,
						// panControl: false
						},
						//
						map = new google.maps.Map(document
								.getElementById('map'), myOptions);

						// Build markers and elements for venues returned.
						$
								.each(
										dataobj,
										function() {
											if (this.venue.rating) {
												rating = '<span class="rating">'
														+ this.venue.rating
														+ '</span>';
											}

											if (this.venue.location.address) {
												address = '<p class="subinfo">'
														+ this.venue.location.address
														+ '<br>';
											} else {
												address = "";
											}

											if (this.venue.contact.formattedPhone) {
												phone = "Phone:"
														+ this.venue.contact.formattedPhone;
											} else {
												phone = "";
											}

											bond = '<div class="venue"><h2><span>'
													+ this.venue.name
													+ " "
													+ rating
													+ '</span></h2>'
													+ address
													+ phone
													+ '</p><p><strong>Total Checkins:</strong> '
													+ this.venue.stats.checkinsCount
													+ '</p></div>';
											$("#venues").append(bond);

											// Build markers
											var markerImage = {
												url : '../images/pin2.png',
												scaledSize : new google.maps.Size(
														24, 24),
												origin : new google.maps.Point(
														0, 0),
												anchor : new google.maps.Point(
														24 / 2, 24)
											}, markerOptions = {
												map : map,
												position : new google.maps.LatLng(
														this.venue.location.lat,
														this.venue.location.lng),

												title : this.venue.name,
												animation : google.maps.Animation.DROP,
												icon : markerImage,
												optimized : false
											}, marker = new google.maps.Marker(
													markerOptions)

										});
					}
				});
	}

	function displaymap() {
		$("#venues").hide();
		var myOptions = {
			zoom : 5,
			center : new google.maps.LatLng(38.962612, -99.080879),
			mapTypeId : google.maps.MapTypeId.ROADMAP,
			panControl : false,

			mapTypeControlOptions : {
				style : google.maps.MapTypeControlStyle.DROPDOWN_MENU
			},

			zoomControl : true,
			zoomControlOptions : {
				style : google.maps.ZoomControlStyle.SMALL
			}
		}, map = new google.maps.Map(document.getElementById('map'), myOptions);
	}

	displaymap();
});