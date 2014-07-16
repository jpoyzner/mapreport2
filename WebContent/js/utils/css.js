define(['jquery'], function() {
	var headElement = $('head');
	
	return {
		load: function(filename) {
			headElement.append('<link rel="stylesheet" type="text/css" href="//50.62.80.222/MapReport/css/' + filename + '.css">');
		}
	};
});
