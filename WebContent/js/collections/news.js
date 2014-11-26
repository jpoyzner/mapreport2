define(['collections/topics', 'underscore', 'backbone'], function(TopicsModel) {
	return Backbone.Collection.extend({
		model: Backbone.Model.extend({defaults: {icon: 'http://www.mapreport.com/images/common/list5.gif'}}), //TODO: NEED REAL DEFAULT ICON
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
			
			this.topics = new TopicsModel(response.topics.children);
			
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