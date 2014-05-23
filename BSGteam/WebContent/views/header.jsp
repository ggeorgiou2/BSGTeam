<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="BSG Team">
<meta name="author" content="BSG Team">

<title>Social Web queries</title>
<link rel="stylesheet" href="css/datepicker.css">
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/modern-business.css" rel="stylesheet">
<link href="css/custom.css" rel="stylesheet">
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link rel="images/favicon.ico" href="images/favicon.ico"
	type="image/x-icon" />

<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCP0N_f7wpMqzijj1w_b2n5k6q3u4NKo_c&v=3.exp&sensor=false&libraries=places"></script>			
<script src="js/googleMap.js"></script>
<script src="js/jquery-1.10.2.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/jqBootstrapValidation.js"></script>

<script src="js/modern-business.js"></script>
<script>
	$(function() {
		$("input,select,textarea").not("[type=submit]").jqBootstrapValidation();
	});
</script>
</head>
<body>
	<div class="container">
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target=".navbar-ex1-collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<!-- You'll want to use a responsive image option so this logo looks good on devices - I recommend using something like retina.js (do a quick Google search for it and you'll find it) -->
					<a class="navbar-brand" href="twitter"> 
						<img src="images/instagram.png" width="32" height="32"	alt="Instagram" />
						<img src="images/twittericon.png" width="32" height="32" alt="Twitter" />
						<img src="images/foursquareicon.png" width="32" height="32"	alt="Foursquare" />
						<img src="images/mapsicon.png" width="32" height="32" alt="GoogleMaps" />
						<img src="images/flickr.png" width="32" height="32" alt="flickr" />
						BSG Team 
					</a>
				</div>

				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse navbar-ex1-collapse">
					<ul class="nav navbar-nav navbar-right">
						<li><a class="navbar-brand" href="twitter"><span
								class="glyphicon glyphicon-home"></span> Home</a></li>
								
						<% if ((session.getAttribute("twitterToken") != null) && (session.getAttribute("foursquareToken") != null))  { %>					
						<li><a class="navbar-brand" href="additional_features?action=additional"><span
								class="glyphicon glyphicon-book"></span> Additional Features</a>
						</li>
						<% } %>
						<li><a class="navbar-brand" href="additional_features?action=more"><span
								class="glyphicon glyphicon-plus-sign"></span> More APIs</a>
						</li>
						<li><a class="navbar-brand" href="userSearch"><span
								class="glyphicon glyphicon-search"></span> Search Triple Store</a></li>
						<% if ((session.getAttribute("twitterToken") != null) || (session.getAttribute("foursquareToken") != null))  { %>
						<li>
								<a class="navbar-brand" href='twitterToken?action=logout'><span
								class="glyphicon glyphicon-user"></span> Logout</a>
						</li>
						<% } %>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container -->
		</nav>
		<br/> <br/>

	</div>