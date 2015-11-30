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
				
				//doesn't work because it has to prompt user first for permission:
//				if (!latitude || !longitude) {
//					if (navigator.geolocation) {
//				        navigator.geolocation.getCurrentPosition(function(position) {
//				        	latitude = position.coords.latitude;
//				        	longitude = position.coords.longitude; 
//						});
//				    } else {
//				    	latitude = 37.7576793;
//			        	longitude = -122.5076404;
//				    }
//				}
				
				mapComponent.map =
					new google.maps.Map(document.getElementById('mr-map'), {center: {lat: latitude, lng: longitude}, zoom: 8});
			}
			
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
		componentDidMount: function() {
			//EVENTS ARE HERE: https://developers.google.com/maps/documentation/javascript/reference#MapsEventListener
			
//			this.map.on('google-map-ready', _.bind(function(event) {
//				var boundLoadFunc = _.bind(this.mapUpdated, this, event);
//				window.google.maps.event.addListener(event.target.map, 'dragend', boundLoadFunc);
//				window.google.maps.event.addListener(event.target.map, 'zoom_changed', _.after(3, boundLoadFunc));
//			}, this));
		}
	});
});