define(['utils/css', 'googlemap', 'backbone', 'underscore'], function(Css) {
	return Backbone.View.extend({
		template: _.template($('#mr-map-template').html()),
		el: $('#mr-map-bucket'),
		initialize: function(options) {
			this.news = options.news;
			this.latitude = options.latitude;
			this.longitude = options.longitude;
			
			Css.load('map');
			this.$el.html(this.template({latitude: this.latitude, longitude: this.longitude}));
			
			this.listenTo(options.news, 'sync', this.addMarkers);
		},
		addMarkers: function() {
			var component = this.$el.find('google-map');
			_.each(this.news.models, _.bind(function(article) { //this should be in template appended once
				component.append( //TODO: turn this into template, also need to make template.js which should cache
					'<google-map-marker latitude="' + (this.latitude + Math.random() - 0.5)
						+ '" longitude="' + (this.longitude + Math.random() - 0.5)
						+ '" icon="' + 'images/ukraine.gif'
						+ '">' + article.get('shortLabel') + '</google-map-marker>');
			}, this));
		}	
	});
});
