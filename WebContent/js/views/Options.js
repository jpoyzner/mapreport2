define(['utils/css', 'backbone', 'underscore'],
function(Css) {
	return Backbone.View.extend({
		template: _.template($('#mr-options-template').html()),
		initialize: function(options) {
			Css.load('options');
			options.bucket.append(this.template(options.news));
		}
	});
});
