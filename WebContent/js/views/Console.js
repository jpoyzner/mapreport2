define(['utils/css', 'backbone', 'underscore'], function(Css) {
	return Backbone.View.extend({
		template: _.template($('#mr-console-template').html()),
		initialize: function() {
			Css.load('console');
			$('body').append(this.template);
		}
	});
});
