define(['utils/css', 'backbone', 'underscore'],
function(Css) {
	return Backbone.View.extend({
		template: _.template($('#mr-options-template').html()),
		el: $('#mr-options-bucket'),
		initialize: function(options) {
			Css.load('options');
			this.$el.html(this.template(options.news));
		}
	});
});
