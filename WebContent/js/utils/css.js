define(['jquery'], function() {
	var headElement = $('head');
	
	return {
		load: function(filename) {
			headElement.append('<link rel="stylesheet" type="text/css" href="css/' + filename + '.css">');
		}
	};
});
