define(['templates', 'utils/css', 'backbone', 'underscore'],
function(Templates, Css) {
	return Backbone.View.extend({
		el: $('#mr-report-bucket'),
		initialize: function(options) {
			this.news = options.news;
			this.listenTo(this.news, 'sync', this.render);
		},
		render: function() {
			Css.load('report');
			this.$el.html(Templates['mr-report-template'](this.news));
		}
	});
});
