define(['collections/topics', 'collections/locations', 'collections/dates', 'models/article', 'utils/buildurl', 'utils/spiderfy',
    'utils/detector', 'underscore', 'backbone'],
function(TopicsCollection, LocationsCollection, DatesCollection, ArticleModel, BuildURL, Spiderfy, Detector) {
	return Backbone.Collection.extend({
		model: ArticleModel,
		initialize: function(rootUrl, options) {
			this.rootUrl = rootUrl;
			
			if (options.topic) {
				this.topic = options.topic;
			}
			
			if (options.loc) {
				this.loc = options.loc;
			}
			
			if (options.date) {
				this.date = options.date;
			}
			
			if (options.mapBounds) {
				this.mapBounds = options.mapBounds;
			}
			
			if (options.search) {
				this.search = options.search;
			}
			
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
			
			Spiderfy(response.news, this.mapBounds);
			
			//console.log(response.SQL);
			
			if (this.topic || this.loc || this.date || this.mapBounds || this.search) {
				var queryParams = [];
				
				if (this.mapBounds) {
					queryParams.push(
						"left=" + this.mapBounds.left,
						"right=" + this.mapBounds.right,
					    "top=" + this.mapBounds.top,
					    "bottom=" + this.mapBounds.bottom);
				}
				
				if (this.search) {
					queryParams.push("search=" + this.search);
				}
				
				require('router').navigateTo(
					BuildURL(
						(this.topic ? "topic/" + this.topic : "")
							+ ((this.topic && this.loc) ? "/" : "")
							+ (this.loc ? "location/" + this.loc : "")
							+ (((this.topic || this.loc) && this.date) ? "/" : "")
							+ (this.date ? "date/" + this.date : ""),
						queryParams));
			}
			
			return response.news;
		},
		url: function() {
			console.log("fetching map");
			
			var params = [];
			
			if (this.mapBounds) {
				params.push(
					"left=" + this.mapBounds.left,
            		"right=" + this.mapBounds.right,
            		"top=" + this.mapBounds.top,
            		"bottom=" + this.mapBounds.bottom);
			}
			
			if (this.localLat && this.localLong) {
				params.push(	
					"local-lat=" + this.localLat,
		    		"local-long=" + this.localLong);
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
			
			if (this.search) {
				params.push("keywords=" + this.search);
			}
			
			if (Detector.phone()) {
				params.push("isMobile=true");
			}
			
			return BuildURL(this.rootUrl + 'news', params);
        }
	});
});