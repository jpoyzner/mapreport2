define(['templates', 'utils/css', 'backbone', 'underscore'],
function(Templates, Css) {
	return Backbone.View.extend({
		el: $('#mr-report-bucket'),
		initialize: function(options) {
			this.news = options.news;
			
			Css.load('report');
			
			this.listenTo(this.news, 'request', this.refresh);
			this.listenTo(this.news, 'sync', this.render);
		},
		refresh: function() {
			this.$el.html('<web-loader type="clock" color="white"></web-loader>');
		},
		render: function() {		
			this.$el.html(Templates['mr-report-template'](this.news));
		}
	});
});
