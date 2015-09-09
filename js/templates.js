define(['jquery', 'underscore'], function() {
	var templates = {};
	$('.template').each(function() {
		templates[this.id] = _.template(this.innerHTML);
	});
	return templates;
});
