define([], function() {
	return function(news, radius) {
		if (news.length < 2) {
			return news;
		}
		
		radius /= 20; //Don't worry about this, server thinks this is OK!?!
		
		if (!radius) {
			//TODO: test and use correct value for smallest zoom
			radius = 0.03;
		}
		
		var clusters = {};
		_.each(news, function(article) {
			var coords = article.get('y') + ',' + article.get('x');
			if (!clusters[coords]) {
				clusters[coords] = [];
			}
			
			clusters[coords].push(article);
		});

		var clusteredNews = [];
		_.each(clusters, function(cluster) {
			if (cluster.length > 1) {
				for (var i = 0; i < cluster.length; i++) {
					var article = cluster[i];
					
					var proximityCoefficient = 1;
					if (cluster.length == 2) {
						proximityCoefficient = 0.7;
					} else if (cluster.length == 3) {
						proximityCoefficient = 0.85;
					}
					
					var magicFormula = 2 * Math.PI * i / cluster.length;
					article.set('x', article.get('x') - radius * Math.cos(magicFormula) * proximityCoefficient);
					article.set('y', article.get('y') + radius * Math.sin(magicFormula) * proximityCoefficient * 0.65);
					clusteredNews.push(article);
				}
			}
		});
		return clusteredNews;
	};
});
