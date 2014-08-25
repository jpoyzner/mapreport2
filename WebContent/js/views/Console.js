define(['utils/css', 'backbone', 'underscore'], function(Css) {
	//might need to redo a bit with new changes
	return Backbone.View.extend({
		template: _.template($('#mr-console-template').html()),
		initialize: function() {
			Css.load('console');
			$('body').append(this.template);
		}
	});
});
