define(['collections/topics', 'collections/locations', 'collections/dates', 'models/story', 'utils/buildurl', 'underscore', 'backbone'],
function(TopicsCollection, LocationsCollection, DatesCollection, StoryModel, BuildURL) {
	return Backbone.Collection.extend({
		model: StoryModel,
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
			
			if (response.topics) {
				this.topics = new TopicsCollection(response.topics.children);
			}
			
			if (response.locations) {
				this.locations = new LocationsCollection(response.locations.children);
			}
			
			if (response.dates) {
				this.dates = new DatesCollection(response.dates.children);
			}
			
			console.log(response.SQL);
			
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
			
			if (this.loc) {
				params.push("location=" + encodeURI(this.loc));
			}
			
			if (this.date) {
				params.push("date=" + encodeURI(this.date));
			}
			
			return BuildURL('news', params);
        },
        optionsChanging: function() {
        	this.optionMapUpdates = 3;
        }
	});
});