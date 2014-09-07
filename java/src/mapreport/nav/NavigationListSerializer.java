package mapreport.nav;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class NavigationListSerializer implements JsonSerializer<NavigationList> {

	@Override
	public JsonElement serialize(NavigationList navigationList, final Type typeOfSrc, final JsonSerializationContext context) {
	    final JsonObject jsonObject = new JsonObject();
	    
  	    final JsonElement jsonName = context.serialize(navigationList.name);
	    jsonObject.add("name", jsonName);
	    
  	    final JsonElement jsonNodeList = context.serialize(navigationList.nodeList);
	    jsonObject.add("nodeList", jsonNodeList);

	  //  jsonObject.remove("nodeList");
	  //  jsonObject.remove("childrenMap");
	    
	    return jsonObject;  
	}
}