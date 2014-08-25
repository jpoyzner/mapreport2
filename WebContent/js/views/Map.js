define(['utils/css', 'googlemap', 'backbone', 'underscore'], function(Css) {
	return Backbone.View.extend({
		template: _.template($('#mr-map-template').html()),
		initialize: function(options) {
			Css.load('map');
			options.bucket.append(this.template);
			
			$(document).ready(function() {
				var script = document.createElement('script');
				script.type = 'text/javascript';
				script.src = 'https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&callback=embedMap';
				document.body.appendChild(script);
				
				window.embedMap = function() {
					var map = new google.maps.Map(document.getElementById("gmap_canvas"), {
						zoom:14,
						center: new google.maps.LatLng(37.759753,-122.50232699999998),
						mapTypeId: google.maps.MapTypeId.ROADMAP
					});
					
					var marker = new google.maps.Marker({map: map,position: new google.maps.LatLng(37.759753, -122.50232699999998)});
					
					var infowindow = new google.maps.InfoWindow({
						content:"<span style='height:auto !important; display:block; white-space:nowrap; overflow:hidden !important;'>" +
							"<strong style='font-weight:400;'>Jeff Poyzner</strong><br>1442 43rd ave<br>94122 san francisco</span>"
					});
					
					google.maps.event.addListener(marker, "click", function(){
						infowindow.open(map,marker);
					});
					
					infowindow.open(map,marker);
				};
			});
		}
	});
});
