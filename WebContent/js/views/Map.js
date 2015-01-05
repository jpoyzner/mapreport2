define(['templates', 'utils/css', 'backbone', 'underscore'], function(Templates, Css) {
	return Backbone.View.extend({
		initialize: function(options) {
			this.news = options.news;
			this.latitude = options.latitude;
			this.longitude = options.longitude;
			
			Css.load('map');

			this.map =
				$('#mr-map-bucket').html(
					Templates['mr-map-template']({latitude: this.latitude, longitude: this.longitude}))
						.find('google-map');

			//EVENTS ARE HERE: https://developers.google.com/maps/documentation/javascript/reference#MapsEventListener
			
			this.map.on('google-map-ready', _.bind(function(event) {
				var boundLoadFunc = _.bind(this.mapUpdated, this, event);
				window.google.maps.event.addListener(event.target.map, 'dragend', boundLoadFunc);
				window.google.maps.event.addListener(event.target.map, 'zoom_changed', _.after(3, boundLoadFunc));
			}, this));

			this.listenTo(this.news, 'sync', this.populateMarkers);
		},
		populateMarkers: function() {
			if (this.news.fetches) {
				return;
			}
			
			if (this.news.optionMapUpdates && this.news.optionMapUpdates != 1) {
				this.map.attr('fitToMarkers', '');
			}
			
			this.map[0].clear();
			this.map.html(Templates['mr-map-markers-template']({news: this.news, latitude: this.latitude, longitude: this.longitude}));
		},
		mapUpdated: function(event) { //called after template updated above
			if (this.news.optionMapUpdates) { //do not fetch again if map updated because of options changing
				this.news.optionMapUpdates--;
				return;
			}

			var bounds = event.target.map.getBounds();
		    var ne = bounds.getNorthEast();
		    var sw = bounds.getSouthWest();
			this.news.fetching = true;
		    this.news.mapBounds = {left: sw.lng(), right: ne.lng(), top: ne.lat(), bottom: sw.lat()};
		    this.news.loc = undefined;
		    
		    this.map.removeAttr('fitToMarkers');
		    this.news.fetch();
		}
	});
});
