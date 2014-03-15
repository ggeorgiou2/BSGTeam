<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <style type="text/css">
      html { height: 100% }
      body { height: 100%; margin: 0; padding: 0 }
      #map-canvas { height: 100% }
    </style>
<!--     <script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDHoP8tlmFj1q763ivChFu-vj6RaljATkE&sensor=false">
    </script>
    <script type="text/javascript">
      function initialize() {
        var mapOptions = {
          center: new google.maps.LatLng(-34.397, 150.644),
          zoom: 8
        };
        var map = new google.maps.Map(document.getElementById("map-canvas"),
            mapOptions);
      }
      google.maps.event.addDomListener(window, 'load', initialize);
    </script>
  </head>
  <body>
  <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
 -->
 
<script>
 var length = prompt("Rectangle length in cm?");
 var width = prompt("Rectangle width in cm?");
 document.write("Area = "+length*width);
 alert("Area = "+length*width);
 // single line comment
 /* multiple-line comment 
 multiple-line comment */
</script>
 <script type="text/javascript">

var geocoder = new google.maps.Geocoder();
var address = "new york";

geocoder.geocode( { 'address': address}, function(results, status) {

  if (status == google.maps.GeocoderStatus.OK) {
    var latitude = results[0].geometry.location.lat();
    var longitude = results[0].geometry.location.lng();
    alert(latitude + " " + longitude);
  } 
}); 
</script>
    <div id="map-canvas"/>
  </body>
</html>