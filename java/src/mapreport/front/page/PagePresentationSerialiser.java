package mapreport.front.page;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class PagePresentationSerialiser implements JsonSerializer<PagePresentation> {

	@Override
	public JsonElement serialize(PagePresentation page, final Type typeOfSrc, final JsonSerializationContext context) {
	    final JsonObject jsonObject = new JsonObject();

	    final JsonElement jsonTitle = context.serialize(page.title); 
	    jsonObject.add("title", jsonTitle);   
	    
	 //   final JsonElement jsonView = context.serialize(page.getView()); 
	 //   jsonObject.add("view", jsonView);   

	    final JsonElement jsonMetaData = context.serialize(page.getMetaData());
	    jsonObject.add("metaData", jsonMetaData);
	    
	    final JsonElement jsonNews = context.serialize(page.getView()); //.getNewsList());
	    jsonObject.add("news", jsonNews);

	//    final JsonElement jsonNavPath = context.serialize(page.getNavigationPath()); 
	//    jsonObject.add("path", jsonNavPath);
	    
	    final JsonElement jsonNavLocations = context.serialize(page.navLocations); 
	    jsonObject.add("locations", jsonNavLocations);

	    final JsonElement jsonNavTopics = context.serialize(page.navTopics); 
	    jsonObject.add("topics", jsonNavTopics);

	    final JsonElement jsonNavDates = context.serialize(page.navDates); 
	    jsonObject.add("dates", jsonNavDates);   

		 //   final JsonElement jsonNavTree = context.serialize(page.getNavigationTree()); 
	   // jsonObject.add("tree", jsonNavTree);

	    return jsonObject;
	}
}
