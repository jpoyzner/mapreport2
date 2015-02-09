define(['utils/detector', 'templates', 'collections/news', 'views/Map', 'views/Options', 'views/Report', 'jquery', 'backbone'],
function (Detector, Templates, News, Map, Options, Report) {
	return new (Backbone.Router.extend({
	    initialize: function() {
	    	var usePushStates = "pushState" in history;
	    	Backbone.history.start({pushState: usePushStates, hashChange: usePushStates});
	    },
	    routes: {
	    	'*path': 'homePage'
	    },
	    homePage: function(options) {
	    	$('body').html(Templates['mr-template']({platform: Detector.phone() ? 'mobile' : 'desktop'}));

	    	var rootDomain = document.domain + ":8080";
	    	switch (document.domain) {
	    		case "50.62.80.222": this.pathPrefix = "/mapreport/"; break;
	    		case "localhost": this.pathPrefix = "/mapreport-stable/"; break;
	    		default: this.pathPrefix = ""; //is this actually needed?
	    	}

	    	var path = location.pathname.split('/');
	    	
	    	var topic;
	    	var topicIndex = path.indexOf('topic') + 1;
	    	if (topicIndex && topicIndex < path.length) {
	    		topic = path[topicIndex];
	    	}
	    	
	    	var loc;
	    	var locationIndex = path.indexOf('location') + 1;
	    	if (locationIndex && locationIndex < path.length) {
	    		loc = path[locationIndex];
	    	}
	    	
	    	var date;
	    	var dateIndex = path.indexOf('date') + 1;
	    	if (dateIndex && dateIndex < path.length) {
	    		date = path[dateIndex];
	    	}
	    	
	    	var left, right, top, bottom;
	    	_.each(location.search.slice(1).split('&'), function(queryParam) {
	    		var entry = queryParam.split('=');
	    		if (entry[0] === 'left') {
	    			left = entry[1];
	    		} else if (entry[0] === 'right') {
	    			right = entry[1];
	    		} else if (entry[0] === 'top') {
	    			top = entry[1];
	    		} else if (entry[0] === 'bottom') {
	    			bottom = entry[1];
	    		}
	    	});
	    	
	    	var settings = {topic: topic, loc: loc, date: date};
	    	
	    	if (left && right && top && bottom) {
	    		settings.mapBounds = {left: left, right: right, top: top, bottom: bottom};
	    	}
	    	
	    	var news = new News("http://" + rootDomain + this.pathPrefix, settings);
	    	
	    	new Map({news: news, latitude: 37.759753, longitude: -122.50232699999998}); //won't need coordinates probably
	    	new Options({news: news});	    	
	    	new Report({news: news});
	    },
	    redirectTo: function(path) {
	        location.href = path;
	    }, 
	    navigateTo: function(URL) {
	    	this.navigate(this.pathPrefix + URL);
	    }
	}))();
});