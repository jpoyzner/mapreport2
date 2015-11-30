define(['react', 'utils/css'], function(React, Css) {
	return React.createClass({
		getInitialState: function() {
			window.mapComponent = this;
			
			window.initMap = function() {
				var latitude = 37.7576793;
	        	var longitude = -122.5076404;
				if (this.props) {
					latitude = this.props.latitude;
					longitude = this.props.longitude;
				}
				
				mapComponent.map =
					new google.maps.Map(document.getElementById('mr-map'), {center: {lat: latitude, lng: longitude}, zoom: 8});
				
				google.maps.event.addListener(mapComponent.map, 'zoom_changed', function() {
				    var zoomChangeBoundsListener = 
				        google.maps.event.addListener(mapComponent.map, 'bounds_changed', function(event) {
				            if (this.getZoom() < 1) {
				                this.setZoom(1);
				            }
				            
				            google.maps.event.removeListener(zoomChangeBoundsListener);
				        });
				});
			};
			
			require(['gmaps']);
			Css.load('map');	
			
			return {};
		},
		render: function() {
			return (
				<div id="mr-map-bucket">
					<div id="mr-map" />
				</div>
			);
		},
		componentDidUpdate: function() {
//			if (this.news.fetches) {
//				return;
//			}
//			
//			//if (this.news.optionMapUpdates && this.news.optionMapUpdates != 1) {
//			if (!this.news.mapReloading) {
//				this.map.attr('fitToMarkers', '');
//			}
//	
			
			
			
//			this.map[0].clear();
			
			if (!this.props.news) {
				return;
			}
			
			var bounds = new google.maps.LatLngBounds();
			this.props.news.models.map(function(article) {
				var marker =
					new google.maps.Marker({
						position: new google.maps.LatLng(article.get('x'), article.get('y')),
						map: this.map,
						icon: article.get('icon'),
						title: article.get('label')
					});
				
				bounds.extend(marker.getPosition());
				
				marker.addListener('click', function() {
					new google.maps.InfoWindow({
					    content:
					    	'<a href="' + article.get('url') + '" target="_blank">' +
					    		article.get('dateTime') +
					    		' @ ' + article.get('address') +
					    		': ' + article.get('label') +
					    	'</a>'
					}).open(this.map, marker);
				}.bind(this));
			}.bind(this));
			
			if (this.props.news.models.length) {
				this.map.fitBounds(bounds);
			}
			
			//EVENTS ARE HERE: https://developers.google.com/maps/documentation/javascript/reference#MapsEventListener
			
//			this.map.on('google-map-ready', _.bind(function(event) {
//				var boundLoadFunc = _.bind(this.mapUpdated, this, event);
//				window.google.maps.event.addListener(event.target.map, 'dragend', boundLoadFunc);
//				window.google.maps.event.addListener(event.target.map, 'zoom_changed', _.after(3, boundLoadFunc));
//			}, this));
		}
	});
});