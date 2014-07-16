requirejs.config({
    paths: {
        jquery: '//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.0/jquery.min',
        underscore: '//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.6.0/underscore-min',
        backbone: '//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.2/backbone-min',
        googlemap: '//maps.google.com/maps/api/js?v=3&sensor=false'
    }
});

requirejs(['models/news', 'views/Map', 'views/Options', 'views/Report', 'jquery', 'backbone'], function(News, Map, Options, Report) {
	var news = new News();
	//TODO: these will be loaded on sync ? NO loads views first
	//and make each of these views implement on sync listeners
	new Map({news: news, bucket: $('#mr-top-left-bucket')});
	new Options({news: news, bucket: $('#mr-top-right-bucket')});
	new Report({news: news, bucket: $('#mr-bottom-bucket')});
});