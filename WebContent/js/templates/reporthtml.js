define([], function() { return '' +
	'<div id="mr-report">' +
		'<% _.each(models, function(article) { %>' +
			'<div class="mr-report-article"><%= article.title %></div>' +
		'<% }); %>' +
	'</div>';
});
