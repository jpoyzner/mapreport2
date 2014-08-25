define(['utils/css', 'backbone', 'underscore'],
function(Css) {
	return Backbone.View.extend({
		template: _.template($('#mr-report-template').html()),
		initialize: function(options) {
			this.news = options.news;
			this.bucket = options.bucket;
			this.listenTo(this.news, 'sync', this.render);
		},
		render: function() {
			Css.load('report');
			this.bucket.append(this.template(this.news));
		}
	});
});
