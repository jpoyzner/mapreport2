define(['templates/optionshtml', 'utils/css', 'backbone', 'underscore'],
function(OptionsTemplate, Css) {
	return Backbone.View.extend({
		template: _.template(OptionsTemplate),
		initialize: function(options) {
			Css.load('options');
			options.bucket.append(this.template(options.news));
		}
	});
});
