requirejs.config({
    paths: {
        jquery: '//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.0/jquery.min',
        underscore: '//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.6.0/underscore-min',
        backbone: '//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.2/backbone-min',
        googlemap: '//maps.google.com/maps/api/js?v=3&sensor=false'
    }
});

requirejs(['collections/news', 'views/Map', 'views/Options', 'views/Report', 'jquery', 'backbone'], function(News, Map, Options, Report) {
	var news = new News();
	new Map({news: news, latitude: 37.759753, longitude: -122.50232699999998}); //won't need coordinates probably
	new Options({news: news});
	new Report({news: news});
});