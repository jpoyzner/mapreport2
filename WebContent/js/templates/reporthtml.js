define([], function() { return '' +
	'<div id="mr-report">' +
		'<% _.each(models, function(article) { %>' +
			'<div class="mr-report-article"><%= article.get("label") %></div>' +
		'<% }); %>' +
	'</div>';
});
