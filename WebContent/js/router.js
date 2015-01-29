define(['utils/detector', 'templates', 'collections/news', 'views/Map', 'views/Options', 'views/Report', 'jquery', 'backbone'],
function (Detector, Templates, News, Map, Options, Report) {
	return new (Backbone.Router.extend({
	    initialize: function() {
	    	this.usePushStates = "pushState" in history; //don't need "this" here
	    	Backbone.history.start({pushState: this.usePushStates, hashChange: this.usePushStates});
	    },
	    routes: {
	    	'*path': 'homePage'
	    },
	    homePage: function(options) {
	    	$('body').html(Templates['mr-template']({platform: Detector.phone() ? 'mobile' : 'desktop'}));
	    	
	    	var path = window.location.pathname.split('/');
	    	
	    	switch (document.domain) {
	    		case "50.62.80.222": this.pathPrefix = "/mapreport/"; break;
	    		case "localhost": this.pathPrefix = "/mapreport-stable/"; break;
	    		default: this.pathPrefix = "";
	    	}
	    	
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
	    	
	    	var news = new News(topic, loc);
	    	
	    	new Map({news: news, latitude: 37.759753, longitude: -122.50232699999998}); //won't need coordinates probably
	    	new Options({news: news});	    	
	    	new Report({news: news});
	    },
	    redirectTo: function(path) {
	        location.href = path;
	    }, 
	    //Use this function instead of backbone's navigate(..., {replace: true}) because of IE9 (no push states):
	    navigateReplace: function(URL) {
	    	if (this.usePushStates) {
	    		this.navigate(this.pathPrefix + URL, {replace: true});
	    	}
	    }
	}))();
});