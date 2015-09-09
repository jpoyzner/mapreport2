requirejs.config({
    paths: {
    	baseURL: '//50.62.80.222/MapReport/js', //or wherever it's hosted currently
//    	templates: '//50.62.80.222/MapReport/js',
        jquery: '//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.0/jquery.min',
        underscore: '//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.6.0/underscore-min',
        backbone: '//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.2/backbone-min',
        text: '//cdnjs.cloudflare.com/ajax/libs/require-text/2.0.10/text.min'
    }
});

//TODO: you need to make sure pages which might have these libs already wont crash!!!
//TODO: NO CONFLICT OUT THIS JQUERY AND USE IT THROUGHOUT MODULE!

requirejs(['jquery', 'views/Console'], function($, Console) { 
	new Console();
});