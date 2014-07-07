package mapreport.nav;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import mapreport.util.Log;

public class NavigationNodeSerializer  implements JsonSerializer<NavigationNode> {

	@Override
	public JsonElement serialize(NavigationNode navigationNode, final Type typeOfSrc, final JsonSerializationContext context) {
	    final JsonObject jsonObject = new JsonObject();
   	    final JsonElement jsonMetaData = context.serialize(navigationNode.metaData.getHeader());
	    jsonObject.add("node", jsonMetaData);   	

		    Log.log ("NavigationNodeSerializer navigationNode=" + navigationNode);
		    Log.log ("NavigationNodeSerializer navigationNode.getPageFilters()=" + navigationNode.getPageFilters());
		    Log.log ("NavigationNodeSerializer navigationNode.getPageFilters().getLink()=" + navigationNode.getPageFilters().getLink());
	    final JsonElement jsonLink = context.serialize(navigationNode.getPageFilters().getLink());
	    jsonObject.add("link", jsonLink);
	    return jsonObject;
	}
}