var gulp = require('gulp');
var react = require('gulp-react');
var shell = require('gulp-shell');

//TODO: REPLACE JS IN WEBAPP WITH JS DEPLOYED TO S3!!! Then make one gulp task to deploy all JS!!!
gulp.task('default', ['build'], function() {
    console.log("deploying MapReport JavaScript!");
});

gulp.task('build', ['bundle'], shell.task([
	"sleep 5; rm -rf tempjs"
]));

gulp.task('bundle', ['temp'], function() {
	var bundle =
		require('gulp-requirejs')({
	        baseUrl: 'tempjs',
	        name: 'mapreport',
	        out: 'mapreport.js',
	        paths: {
	            jquery: '//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.0/jquery.min',
	            jquerycolor: '//cdnjs.cloudflare.com/ajax/libs/jquery-color/2.1.2/jquery.color.min',
	            underscore: '//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.6.0/underscore-min',
	            backbone: '//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.2/backbone-min',
	            react: '//cdnjs.cloudflare.com/ajax/libs/react/0.13.3/react.min',
	            mobiledetect: '//cdnjs.cloudflare.com/ajax/libs/mobile-detect/0.4.0/mobile-detect.min',
	            gmaps: '//maps.googleapis.com/maps/api/js?key=AIzaSyCH89LosFbZ6Inmy5T8yCL0ao54qf2htCo&callback=initMap'
	        },
	        shim: {
	    	    jquerycolor: {
	    	        deps: ['jquery'],
	    	        exports: 'jquerycolor'
	    	    }
	        }
	    });

	bundle.pipe(require('gulp-uglify')()).pipe(gulp.dest('./WebContent/js/'));
	gulp.src('js/lib/**/*.js').pipe(gulp.dest('./WebContent/js/lib/'));
});

gulp.task('temp', ['tempjs'], function() {
	return gulp.src('js/components/**/*.js').pipe(react()).pipe(gulp.dest('./tempjs/components/'));
});

gulp.task('tempjs', ['clean'], function() {
	return gulp.src(['js/**/*.js', '!js/components/**/*.js']).pipe(gulp.dest('./tempjs/'));
});

gulp.task('debug', ['clean'], function() {
	gulp.src(['js/**/*.js', '!js/components/**/*.js']).pipe(gulp.dest('./WebContent/js/'));
	gulp.src('js/components/**/*.js').pipe(react()).pipe(gulp.dest('./WebContent/js/components/'));
});

gulp.task('clean', shell.task([
	"rm -rf WebContent/js/*"
]));

gulp.task('db', shell.task([
	"cd /usr/local/mysql/bin; sudo ./mysqld -u root"
]));