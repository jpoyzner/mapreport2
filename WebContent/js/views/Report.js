define(['templates/reporthtml', 'utils/css', 'backbone', 'underscore'],
function(ReportTemplate, Css) {
	return Backbone.View.extend({
		template: _.template(ReportTemplate),
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
