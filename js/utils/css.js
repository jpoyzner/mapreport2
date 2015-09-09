//TODO: I DONT KNOW IF THIS IS EFFICIENT, PROBABLY WILL REPLACE WITH WEBPACK OR OTHER BUNDLING

define(['utils/detector', 'jquery'], function(Detector) {
	var headElement = $('head');
	
	return {
		load: function(filename) {
			headElement.append(
				'<link rel="stylesheet" type="text/css" href="css/' + (Detector.phone() ? 'mobile/' : '')
					+ filename + '.css">');
		}
	};
});
