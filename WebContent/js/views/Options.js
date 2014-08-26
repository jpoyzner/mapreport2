define(['templates', 'utils/css', 'backbone', 'underscore'], function(Templates, Css) {
	return Backbone.View.extend({
		el: $('#mr-options-bucket'),
		initialize: function(options) {
			Css.load('options');
			this.$el.html(Templates['mr-options-template'](options.news));
		}
	});
});
