define(['react', 'utils/css'], function(React, Css) {
	return React.createClass({
		getInitialState: function() {
			window.mapComponent = this;
			
			window.initMap = function() {
				//default is SF :)
				var latitude = 37.7576793;
	        	var longitude = -122.5076404;
//				if (this.props) {
//					latitude = this.props.latitude;
//					longitude = this.props.longitude;
//				}
				
				mapComponent.map =
					new google.maps.Map(
						document.getElementById('mr-map'),
						{
							center: {lat: latitude, lng: longitude},
							streetViewControl: false,
							mapTypeControl: false,
							zoom: 8
						});
				
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
				
				if (navigator.geolocation) {
	                navigator.geolocation.getCurrentPosition(function(position) {
	                	mapComponent.curlat = position.coords.latitude;
	                	mapComponent.curlong = position.coords.longitude;
	                });
	            }
			};
			
			require(['gmaps']);
			Css.load('map');	
			
			return {};
		},
		render: function() {
			return (
				<div id="mr-map-bucket">
					<div id="mr-map" />
					<div id="mr-search">
						<input id="mr-search-input" />
						<span id="mr-search-action">Search</span>
						<span id="mr-search-clear">x</span>
					</div>
					<img id="mr-curloc-button" src="images/curloc.png" />
				</div>
			);
		},
		componentDidMount: function() {
			var searchInput = $('#mr-search-input');
			this.searchInput = searchInput;
			searchInput.keyup(function(event) {
				if (event.keyCode == 13) {
			    	this.props.news.search = searchInput.val();
					this.props.news.fetch()
			    }
			}.bind(this));
			
			$('#mr-search-action').show().click(function() {
				this.props.news.search = searchInput.val();
				this.props.news.fetch();
			}.bind(this));
			
			$('#mr-search-clear').show().click(function() {
				this.props.news.search = null;
				searchInput.val('')
				this.props.news.fetch();
			}.bind(this));
			
			$('#mr-curloc-button').show().click(function() {
				this.props.news.loc = "Local";
				this.props.news.localLat = this.curlat;
				this.props.news.localLong = this.curlong;
				this.props.news.fetch();
			}.bind(this));
		},
		componentDidUpdate: function() {
			if (this.props.loading) {
				return;
			}
			
			if (this.props.search) {
				this.searchInput.val(this.props.search);
			}
			
			if (this.markers && this.markers.length) {
				this.markers.map(function(marker) {
					marker.setMap(null);
				}.bind(this));	
			}
			
			this.markers = [];
			
			var bounds = new google.maps.LatLngBounds();
			this.props.news.models.map(function(article) {
				if (!article.get('isMapShow')) {
					return;
				}
				
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