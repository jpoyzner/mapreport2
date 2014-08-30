package mapreport.front.page;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class PageMetaDataSerialiser implements JsonSerializer<PageMetaData> {

	@Override
	public JsonElement serialize(PageMetaData page, final Type typeOfSrc, final JsonSerializationContext context) {
	    final JsonObject jsonObject = new JsonObject();

	    final JsonElement jsonDescription = context.serialize(page.description);
	    jsonObject.add("description", jsonDescription);
	    
	    final JsonElement jsonNews = context.serialize(page.header); 
	    jsonObject.add("header", jsonNews);

	    final JsonElement jsonImage = context.serialize(page.image); 
	    jsonObject.add("image", jsonImage);

	    return jsonObject; 
	}
}
