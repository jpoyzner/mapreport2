package mapreport.view.map;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class MapViewSerializer  implements JsonSerializer<MapView> {

	@Override
	public JsonElement serialize(MapView mapView, final Type typeOfSrc, final JsonSerializationContext context) {
	    final JsonObject jsonObject = new JsonObject();
	      
  	    final JsonElement jsonName = context.serialize(mapView.rect);
	    jsonObject.add("rectangle", jsonName);
	    
	    return jsonObject;  
	}
}