package mapreport.filter.loc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mapreport.news.News;
import mapreport.news.NewsPresentation;
import mapreport.util.Log;

public class ClusterHandler {

	public ClusterHandler() {
		// TODO Auto-generated constructor stub
	}

//    (cluster is an array of coordinates on the same point)
//
//    if (cluster.length > 1) {
//        for (var i = 0; i < cluster.length; i++) {
//            var article = cluster[i];
//            
//            var proximityCoefficient = 1;
//            if (cluster.length == 2) {
//                proximityCoefficient = 0.7;
//            } else if (cluster.length == 3) {
//                proximityCoefficient = 0.85;
//            }
//            
//            var magicFormula = 2 * Math.PI * i / cluster.length;
//            article.set('x', article.get('x') - radius * Math.cos(magicFormula) * proximityCoefficient);
//            article.set('y', article.get('y') + radius * Math.sin(magicFormula) * proximityCoefficient * 0.65);
//            clusteredNews.push(article);
//        }
//    }
	
	public static List<News> buildClustersLocations(List<News> newsList, double span) {
		
      double proximityCoefficient = 0.05;
      if (newsList.size() == 2) {
          proximityCoefficient = 0.1 * proximityCoefficient;
      } else if (newsList.size() == 3) {
          proximityCoefficient = 0.2 * proximityCoefficient;
      } else if (newsList.size() < 7) {
          proximityCoefficient = 0.5 * proximityCoefficient;
      }
      
      for (int i = 0; i < newsList.size(); i++) {      
    	  double magicFormula = 2 * Math.PI * i / newsList.size();
    	  News news = newsList.get(i);
    	  double x = news.getX() - span * Math.cos(magicFormula) * proximityCoefficient;
    	  double y = news.getY() - span * Math.sin(magicFormula) * proximityCoefficient * 0.65;
    	  
    	  Log.log("buildClustersLocations newsList.size()=" + newsList.size() + " span=" + span  + " i=" + i  + " magicFormula=" + magicFormula + " x:" + x + " y=" + x);
    	  
    	  news.setX(x);
    	  news.setY(y);
      }
		
	  for (News news : newsList) {
			Log.log("buildClustersLocations after x:" + news.getX() + " y=" + news.getY());
      }
				
	  return newsList;
	}
	
	public static List<News> buildAllClusterLocations(List<News> newsList, double span) {
		  Log.info("buildAllClusterLocations span = " + span);
		if (span == 0D) {
			  Log.info("buildAllClusterLocations if span = 0D ");
			  double xMin = Double.MAX_VALUE; 
			  double xMax = - Double.MAX_VALUE; 
			  double yMin = Double.MAX_VALUE; 
			  double yMax = - Double.MAX_VALUE; 
			  
			  for (News news : newsList) {
				  if (news.isMapShow() && news.getX() == 0) {
					  news.setMapShow(false);
				  }
				  if (!news.isMapShow()) {
					  Log.info("buildAllClusterLocations !news.isMapShow label:" + news.getLabel());
					  continue;
				  }
				  if (news.getX() < xMin) 
					  xMin = news.getX();
				  if (news.getX() > xMax) 
					  xMax = news.getX();
				  
				  if (news.getY() < yMin) 
					  yMin = news.getY();
				  if (news.getY() > yMax) 
					  yMax = news.getY();					  
			  }
	
			  double xSpan = xMax - xMin;
			  double ySpan = yMax - yMin;			  			  
			  span = Math.max(xSpan, ySpan) * 1.5;
			  Log.info("buildAllClusterLocations after span = 0 list.size()=" + newsList.size() + " xMax=" + xMax  + " xMin=" + xMin  + " yMax=" + yMax + " yMin:" + yMin
					  + " xSpan=" + xSpan + " ySpan=" + ySpan);
		}
		
		Map<String, List<News>> clusterMap = new HashMap<String, List<News>>();
		
		for (News news : newsList) {
			Log.log("buildAllClusterLocations span=" + span + " x=" + news.getX() + " y=" + news.getY());
			
			String key = news.getX() + ";" + news.getY();
			
			List<News> clusterNewses = clusterMap.get(key);
			if (clusterNewses == null) {
				clusterNewses = new ArrayList<News>();
				clusterMap.put(key, clusterNewses);
			}
			clusterNewses.add(news);
		}
		
		for (Entry<String, List<News>> cluster : clusterMap.entrySet()) {
			if (cluster.getValue().size() > 1) {
				Log.log("buildAllClusterLocations size=" + cluster.getValue().size() + " x=" + cluster.getValue().get(0).getX() + " y=" + cluster.getValue().get(0).getY());
				buildClustersLocations(cluster.getValue(), span);
			}
		}
		
		for (News news : newsList) {
			Log.log("buildAllClusterLocations after x=" + news.getX() + " y=" + news.getY());
		}
		
		return newsList;
	}

	
}
