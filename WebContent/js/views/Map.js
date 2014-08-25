define(['utils/css', 'googlemap', 'backbone', 'underscore'], function(Css) {
	return Backbone.View.extend({
		template: _.template($('#mr-map-template').html()),
		initialize: function(options) {
			this.news = options.news;
			this.bucket = options.bucket;
			this.latitude = options.latitude;
			this.longitude = options.longitude;
			
			Css.load('map');
			this.bucket.append(this.template({latitude: this.latitude, longitude: this.longitude}));
			
			this.listenTo(options.news, 'sync', this.addMarkers);
			
		},
		addMarkers: function() {
			var component = this.bucket.find('google-map');
			_.each(this.news.models, _.bind(function(article) { //this should be in template appended once
				component.append( //TODO: turn this into template, also need to make template.js which should cache
					'<google-map-marker latitude="' + (this.latitude + Math.random() - 0.5)
						+ '" longitude="' + (this.longitude + Math.random() - 0.5)
						+ '" icon="' + 'images/ukraine.gif'
						+ '">' + article.get('shortLabel') + '</google-map-marker>');
			}, this));
		}	

//			$(document).ready(function() {
//				var script = document.createElement('script');
//				script.type = 'text/javascript';
//				script.src = 'https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&callback=embedMap';
//				document.body.appendChild(script);
//				
//				window.embedMap = function() {
//					var map = new google.maps.Map(document.getElementById("gmap_canvas"), {
//						zoom:14,
//						center: new google.maps.LatLng(37.759753,-122.50232699999998),
//						mapTypeId: google.maps.MapTypeId.ROADMAP
//					});
//					
//					var marker = new google.maps.Marker({map: map,position: new google.maps.LatLng(37.759753, -122.50232699999998)});
//					
//					var infowindow = new google.maps.InfoWindow({
//						content:"<span style='height:auto !important; display:block; white-space:nowrap; overflow:hidden !important;'>" +
//							"<strong style='font-weight:400;'>Jeff Poyzner</strong><br>1442 43rd ave<br>94122 san francisco</span>"
//					});
//					
//					google.maps.event.addListener(marker, "click", function(){
//						infowindow.open(map,marker);
//					});
//					
//					infowindow.open(map,marker);
//				};
//			});
	});
});
