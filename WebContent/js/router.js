define(['utils/detector', 'templates', 'collections/news', 'views/Map', 'views/Options', 'views/Report', 'jquery', 'backbone'],
function (Detector, Templates, News, Map, Options, Report) {
	return Backbone.Router.extend({
	    initialize: function() {
	    	this.usePushStates = "pushState" in history;
	    	Backbone.history.start({pushState: this.usePushStates, hashChange: this.usePushStates});
	    },
	    routes: {
	        '*path': 'homePage'
	    },
	    homePage: function(options) {
	    	$('body').html(Templates['mr-template']({platform: Detector.phone() ? 'mobile' : 'desktop'}));
	    	
	    	var news = new News();
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
	    		this.navigate(URL, {replace: true});
	    	}
	    }
	});
});