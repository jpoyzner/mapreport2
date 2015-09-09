var gulp = require('gulp');

//TODO: REPLACE JS IN WEBAPP WITH JS DEPLOYED TO S3!!! Then make one gulp task to deploy all JS!!!
gulp.task('default', ['bundle'], function() {
    console.log("deploying MapReport JavaScript!");
});

gulp.task('bundle', ['clean'], function() {
	var bundle =
		require('gulp-requirejs')({
	        baseUrl: 'js',
	        name: 'mapreport',
	        out: 'mapreport.js',
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
    
    bundle.pipe(require('gulp-uglify')()).pipe(gulp.dest('./WebContent/js/'));
    
    gulp.src('./js/components/**/*').pipe(gulp.dest('./WebContent/js/components/'));
});

gulp.task('debug', ['clean'], function() {
	gulp.src('./js/**/*').pipe(gulp.dest('./WebContent/js/'));
});

gulp.task('clean', function(callback) {
	require('del')(["WebContent/js/**/*"], callback);
});


//Does gulp-war work?
gulp.task('deploy', require('gulp-shell').task([
 "scp -i ../PoyznerKey.pem ../mapreport.war ec2-user@52.8.176.59:mapreport.war"
]));