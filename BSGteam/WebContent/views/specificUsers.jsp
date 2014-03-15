<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="./bootstrap.css" media="screen">
<link rel="stylesheet" href="../assets/css/bootswatch.min.css">
<title>Search Twitter</title>
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Testing</a>
			</div>
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="twitter">Twitter</a></li>
					<li><a href="views/ggmaps.html">GMaps</a></li>
				</ul>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="custom">
			<h1>Search</h1>
			<form action="twitter" method="post">
				<table>
					<tr>
						<td>Topic:</td>
						<td><input type="text" name="tweetData" /></td>
					</tr>
					<tr>
						<td>Latitude:</td>
						<td><input type="text" name="lat" /></td>
					</tr>
					<tr>
						<td>Longitude:</td>
						<td><input type="text" name="long" /></td>
					</tr>
					<tr>
						<td>Area: (in km)</td>
						<td><input type="text" name="area" /></td>
					</tr>
				</table>
				<table>
					<tr>
						<td><button type="submit" class="btn btn-primary">Search</button></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>