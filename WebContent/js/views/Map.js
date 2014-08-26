define(['templates', 'utils/css', 'googlemap', 'backbone', 'underscore'], function(Templates, Css) {
	return Backbone.View.extend({
		el: $('#mr-map-bucket'),
		initialize: function(options) {
			this.news = options.news;
			this.latitude = options.latitude;
			this.longitude = options.longitude;
			
			Css.load('map');
			this.$el.html(Templates['mr-map-template']({latitude: this.latitude, longitude: this.longitude}));
			
			this.listenTo(options.news, 'sync', this.addMarkers);
		},
		addMarkers: function() {
			this.$el.find('google-map').html(Templates['mr-map-markers-template']({
				news: this.news,
				latitude: this.latitude,
				longitude: this.longitude}));
		}	
	});
});
