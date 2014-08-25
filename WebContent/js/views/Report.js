define(['utils/css', 'backbone', 'underscore'],
function(Css) {
	return Backbone.View.extend({
		template: _.template($('#mr-report-template').html()),
		el: $('#mr-report-bucket'),
		initialize: function(options) {
			this.news = options.news;
			this.listenTo(this.news, 'sync', this.render);
		},
		render: function() {
			Css.load('report');
			this.$el.html(this.template(this.news));
		}
	});
});
