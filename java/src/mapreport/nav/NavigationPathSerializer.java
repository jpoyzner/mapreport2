package mapreport.nav;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class NavigationPathSerializer  implements JsonSerializer<NavigationPath> {

	@Override
	public JsonElement serialize(NavigationPath navigationPath, final Type typeOfSrc, final JsonSerializationContext context) {
	    final JsonObject jsonObject = new JsonObject();
   	    final JsonElement jsonMetaData = context.serialize(navigationPath.nodeList);
	    jsonObject.add("nodeList", jsonMetaData);
	    return jsonObject;  
	}
}