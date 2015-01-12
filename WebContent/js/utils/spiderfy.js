define([], function() {
	return function(news, mapBounds) {
		if (!mapBounds || news.length < 2) { //ONLY DOING MAP BOUNDS NOW
			return news;
		}
		
		var farthestLeft = undefined;
		var farthestRight = undefined;
		var farthestTop = undefined;
		var farthestBottom = undefined;
		
		var clusters = {};
		_.each(news, function(article) {
			var coords = article.x + ',' + article.x;
			if (!clusters[coords]) {
				clusters[coords] = [];
			}
			
			clusters[coords].push(article);
			
			if (!mapBounds) {
				if (!farthestLeft || article.x < farthestLeft) {
					farthestLeft = article.x;
				}
				
				if (!farthestRight || article.x > farthestRight) {
					farthestRight = article.x;
				}
				
				if (!farthestTop || article.y > farthestTop) {
					farthestTop = article.y;
				}
				
				if (!farthestBottom || article.y < farthestBottom) {
					farthestBottom = article.y;
				}
			}	
		});
		
		//TODO: still need to take into account cases where map is on boundaries for distances:
		
		var xDistance, yDistance;
		if (mapBounds) {
			xDistance = (mapBounds.right - mapBounds.left) / 10;
			yDistance = (mapBounds.top - mapBounds.bottom) / 10;
		} else {
			//var calculatedDistance = Math.max(farthestRight - farthestLeft, farthestTop - farthestBottom) / 10;
			var xDistance = 0.5;//calculatedDistance;
			var yDistance = 0.5;//calculatedDistsance / 2;
		}
		
		_.each(clusters, function(cluster) {
			if (cluster.length > 1) {
				for (var i = 0; i < cluster.length; i++) {
					var article = cluster[i];
					article.x -= xDistance * Math.cos(2 * Math.PI * i / cluster.length);
					article.y += yDistance * Math.sin(2 * Math.PI * i / cluster.length);
				}
			}
		});

		return news;
	};
});
