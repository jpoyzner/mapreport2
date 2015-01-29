requirejs.config({
    paths: {
        jquery: '//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.0/jquery.min',
        jquerycolor: '//cdnjs.cloudflare.com/ajax/libs/jquery-color/2.1.2/jquery.color.min',
        underscore: '//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.6.0/underscore-min',
        backbone: '//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.2/backbone-min',
        mobiledetect: '//cdnjs.cloudflare.com/ajax/libs/mobile-detect/0.4.0/mobile-detect.min'
    },
    shim: {
	    jquerycolor: {
	        deps: ['jquery'],
	        exports: 'jquerycolor'
	    }
    }
});

requirejs(['router'], function(){});