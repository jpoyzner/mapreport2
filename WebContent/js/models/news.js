define(['underscore', 'backbone'], function() {
	return Backbone.Collection.extend({
		initialize: function(contentId) {
			this.models = [{title: "ROBOCOP IS A CROOK!"}, {title: "Your pets are aliens!"}];
        }
		//,
//        parse: function(response) {
//        	if (response.errors) {
//				return response;
//			}
//        	
//        	return response.results[0];
//		},
//        url: function() {
//            return '/v4/api/content/get.json?environment=1&content_id=' + this.contentId;
//        }
	});
});