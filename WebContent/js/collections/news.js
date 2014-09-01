define(['underscore', 'backbone'],
function() {
	return Backbone.Collection.extend({
		model: Backbone.Model.extend({defaults: {icon: 'images/ukraine.gif'}}), //TODO: NEED REAL DEFAULT ICON
		initialize: function() {
			this.fetches = 0;
			
			this.on('request', _.bind(function() {
				this.fetches++;
			}, this));
			
			this.fetch();
		},
		parse: function(response) {
			this.fetches--;
			
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