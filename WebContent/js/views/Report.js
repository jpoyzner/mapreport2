define(['templates', 'utils/css', 'utils/color', 'backbone', 'underscore'],
function(Templates, Css, Color) {
	return Backbone.View.extend({
		initialize: function(options) {
			Css.load('report');
			
			this.news = options.news;
			this.bucket = $('#mr-report-bucket');
			
			this.listenTo(this.news, 'request', this.refresh);
			this.listenTo(this.news, 'sync', this.render);
		},
		refresh: function() {
			this.bucket.html('<web-loader type="clock" color="white"></web-loader>');
		},
		render: function() {
			if (!this.news.fetches) {
				this.bucket.html(Templates['mr-report-template']({news: this.news, randomColor: Color.random}));
			}
		}
	});
});
