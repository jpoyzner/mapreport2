define(['react', 'components/page', 'backbone'], function(React, Page) {
	return new (Backbone.Router.extend({
	    initialize: function() {
	    	window.router = this;
	    	
	    	var usePushStates = "pushState" in history;
	    	Backbone.history.start({pushState: usePushStates, hashChange: usePushStates});
	    },
	    routes: {
	    	'*path': 'homePage'
	    },
	    homePage: function(options) {
	    	this.rootDomain = document.domain + ":8080";
	    	this.pathPrefix = document.domain.indexOf('amazon') === -1 ? "/mapreport-stable/" : "/mapreport/";

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
	    	
	    	var left, right, top, bottom, search;
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
	    		} else if (entry[0] === 'search') {
	    			search = entry[1];
	    		}
	    	});
	    	
	    	this.settings = {topic: topic, loc: loc, date: date};
	    	
	    	if (left && right && top && bottom) {
	    		this.settings.mapBounds = {left: left, right: right, top: top, bottom: bottom};
	    	}
	    	
	    	if (search) {
	    		this.settings.search = search;
	    	}
	    	
	    	if (typeof page === 'undefined') {
	    		React.render(React.createElement(Page), $('body')[0]);
	    	} else {
	    		page.getInitialState();
	    	}
	    },
	    redirectTo: function(path) {
	        location.href = path;
	    }, 
	    navigateTo: function(URL) {
	    	this.navigate(this.pathPrefix + URL);
	    }
	}))();
});