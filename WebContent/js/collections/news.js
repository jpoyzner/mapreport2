define(['underscore', 'backbone'],
function() {
	return Backbone.Collection.extend({
		model: Backbone.Model.extend({defaults: {icon: 'images/ukraine.gif'}}), //TODO: NEED REAL DEFAULT ICON
		initialize: function() {
			this.fetch();
		},
		parse: function(response) {
//			if (response.errors) {
//				return response;
//			}
			
			return response.news;
		},
		url: function() {
            return "news"
            	+ (this.mapBounds ?
            		"?left=" + this.mapBounds.left
            			+ "&right=" + this.mapBounds.right
            			+ "&top=" + this.mapBounds.top
            			+ "&bottom=" + this.mapBounds.bottom
            		: "");
        }
	});
});