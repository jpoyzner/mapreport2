define([], function() {
	return function(path, params) {
		var URL = path;
		var firstParam = params.shift();
		if (firstParam) {
			URL += "?" + firstParam;
			
			_.each(params, function(param) {
				URL += "&" + param;
			});
		}
		
        return URL;
	};
});
