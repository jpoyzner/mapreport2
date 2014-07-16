define(['templates/consolehtml', 'utils/css', 'backbone', 'underscore'], function(ConsoleTemplate, Css) {
	return Backbone.View.extend({
		template: _.template(ConsoleTemplate),
		initialize: function() {
			Css.load('console');
			$('body').append(this.template);
		}
	});
});
