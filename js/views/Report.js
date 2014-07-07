define(['templates/reporthtml', 'utils/css', 'backbone', 'underscore'],
function(ReportTemplate, Css) {
	return Backbone.View.extend({
		template: _.template(ReportTemplate),
		initialize: function(options) {
			Css.load('report');
			options.bucket.append(this.template(options.news));
		}
	});
});
