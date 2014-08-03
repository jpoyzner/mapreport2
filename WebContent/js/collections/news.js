define(['underscore', 'backbone'],
function() {
	return Backbone.Collection.extend({
		initialize: function() {
			this.fetch();
		},
		parse: function(response) {
//			if (response.errors) {
//				return response;
//			}
			
			return response.news.newsList.newses;
		},
		url: function() {
            return 'news';
        }
	});
});