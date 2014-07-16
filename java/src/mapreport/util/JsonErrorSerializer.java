package mapreport.util;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonErrorSerializer implements JsonSerializer<JsonError> {

	@Override
	public JsonElement serialize(JsonError error, final Type typeOfSrc, final JsonSerializationContext context) {
	    final JsonObject jsonObject = new JsonObject();
  	 //   final JsonElement jsonErrorMsg = context.serialize(error.getErrMsg());
	  //  jsonObject.add("errorMsg", jsonErrorMsg);   
  	 //   final JsonElement jsonException = context.serialize(error.getException());
	  //  jsonObject.add("exception", jsonException);   
		 //   Log.log ("JsonError error=" + error);
	    return jsonObject;
	}
}