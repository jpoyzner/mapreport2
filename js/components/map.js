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
				
				console.log(this.props);
				
				mapComponent.map =
					new google.maps.Map(document.getElementById('mr-map'), {center: {lat: latitude, lng: longitude}, zoom: 8});
				
				google.maps.event.addListener(mapComponent.map, 'zoom_changed', function() {
				    var zoomChangeBoundsListener = 
				        google.maps.event.addListener(mapComponent.map, 'bounds_changed', function(event) {
				            if (this.getZoom() < 1) {
				            	mapComponent.readjustingZoom = true;
				            	this.setZoom(1);
				            	mapComponent.readjustingZoom = false;
				            }
				            
				            google.maps.event.removeListener(zoomChangeBoundsListener);
				        });
				    
				    if (!mapComponent.props.loading && !mapComponent.readjustingZoom && !mapComponent.fittingBounds) {
				    	mapComponent.fetchDataForNewBounds();
				    }
				});
				
				google.maps.event.addListener(mapComponent.map, 'dragend', mapComponent.fetchDataForNewBounds);
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
			if (this.props.loading) {
				return;
			}
			
			if (this.markers && this.markers.length) {
				this.markers.map(function(marker) {
					marker.setMap(null);
				}.bind(this));	
			}
			
			this.markers = [];
			
			var bounds = new google.maps.LatLngBounds();
			this.props.news.models.map(function(article) {
				var marker =
					new google.maps.Marker({
						position: new google.maps.LatLng(article.get('y'), article.get('x')),
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
				
				this.markers.push(marker);
			}.bind(this));
			
			if (!this.mapMoved && this.props.news.models.length) {
				this.fittingBounds = true;
				this.map.fitBounds(bounds);
				this.fittingBounds = false;
			}
			
			this.mapMoved = false;
		},
		fetchDataForNewBounds: function() {
			this.mapMoved = true;

			var bounds = this.map.getBounds();
		    var ne = bounds.getNorthEast();
		    var sw = bounds.getSouthWest();
		    this.props.news.mapBounds = {left: sw.lng(), right: ne.lng(), top: ne.lat(), bottom: sw.lat()};
		    this.props.news.loc = undefined;

		    this.props.news.fetch();
		}
	});
});