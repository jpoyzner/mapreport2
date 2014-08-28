package mapreport.view.map;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class RectangleSerializer  implements JsonSerializer<Rectangle> {

	@Override
	public JsonElement serialize(Rectangle rect, final Type typeOfSrc, final JsonSerializationContext context) {
	    final JsonObject jsonObject = new JsonObject();
 
	    jsonObject.add("bottom", context.serialize(rect.bottom));
	    jsonObject.add("left", context.serialize(rect.left));
	    jsonObject.add("right", context.serialize(rect.right));
	    jsonObject.add("top", context.serialize(rect.top));
	    jsonObject.add("xCenter", context.serialize(rect.xCenter));
	    jsonObject.add("yCenter", context.serialize(rect.yCenter));
	    jsonObject.add("xSpan", context.serialize(rect.xSpan));
	    jsonObject.add("ySpan", context.serialize(rect.ySpan));
	    
	    return jsonObject;  
	}
}