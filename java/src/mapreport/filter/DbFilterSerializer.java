package mapreport.filter;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DbFilterSerializer implements JsonSerializer<NameFilter> {

	@Override 
	public JsonElement serialize(NameFilter filter, final Type typeOfSrc, final JsonSerializationContext context) {
	    final JsonObject jsonObject = new JsonObject();
     //   jsonObject.addProperty("title", "titleaaaa");

	    final JsonElement jsonName = context.serialize(filter.getName());
	    jsonObject.add("name", jsonName);
	    
	    final JsonElement jsonPriority = context.serialize(filter.getPriority()); 
	    jsonObject.add("filter-priority", jsonPriority);

	    return jsonObject;
	}
}