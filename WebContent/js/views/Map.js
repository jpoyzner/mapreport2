define(['templates', 'utils/css', 'backbone', 'underscore'], function(Templates, Css) {
	return Backbone.View.extend({
		el: $('#mr-map-bucket'),
		initialize: function(options) {
			this.news = options.news;
			this.latitude = options.latitude;
			this.longitude = options.longitude;
			
			Css.load('map');
			this.$el.html(Templates['mr-map-template']({latitude: this.latitude, longitude: this.longitude}));
			this.map = this.$el.find('google-map');
			
			this.listenTo(options.news, 'request', this.refresh);
			this.listenTo(options.news, 'sync', this.populateMarkers);
		},
		refresh: function() {
			this.map[0].clear();
		},
		populateMarkers: function() {
			this.map.html(Templates['mr-map-markers-template']({
				news: this.news,
				latitude: this.latitude,
				longitude: this.longitude}));
		}	
	});
});
