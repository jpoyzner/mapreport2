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
				window.google.maps.event.addListener(event.target.map, 'dragend', _.bind(function(object) {
				    this.map.removeAttr('fitToMarkers');
				    var bounds = event.target.map.getBounds();
					this.news.fetching = true;
				    this.news.mapBounds = {left: bounds.pa.j, right: bounds.pa.k, top: bounds.Ca.j, bottom: bounds.Ca.k};
				    this.news.fetch();
				}, this));
			}, this));

			this.listenTo(options.news, 'request', this.refresh);
			this.listenTo(options.news, 'sync', this.populateMarkers);
		},
		refresh: function() {
			this.map[0].clear();
		},
		populateMarkers: function() {
			if (!this.news.fetches) {
				this.map.html(Templates['mr-map-markers-template']({
					news: this.news,
					latitude: this.latitude,
					longitude: this.longitude}));
			}
		}
	});
});
