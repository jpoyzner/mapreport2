define(['jquery', 'underscore'], function() {
	var templates = {};
	$('script[type="text/template"').each(function() {
		templates[this.id] = _.template(this.innerHTML);
	});
	return templates;
});
