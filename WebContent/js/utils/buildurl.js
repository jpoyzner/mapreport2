define([], function() {
	return function(URL, params) {
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
