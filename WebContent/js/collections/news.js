define(['collections/topics', 'utils/buildurl', 'underscore', 'backbone'], function(TopicsModel, BuildURL) {
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
			var params = [];
			
			if (this.mapBounds) {
				params.push(
					"left=" + this.mapBounds.left,
            		"right=" + this.mapBounds.right,
            		"top=" + this.mapBounds.top,
            		"bottom=" + this.mapBounds.bottom);
			}
			
			if (this.topic) {
				params.push("topic=" + encodeURI(this.topic));
			}
			
			return BuildURL('news', params);
        }
	});
});