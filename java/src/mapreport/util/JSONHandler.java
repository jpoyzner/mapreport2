package mapreport.util;

import mapreport.front.page.PageMetaData;
import mapreport.front.page.PageMetaDataSerialiser;
import mapreport.front.page.PagePresentation;
import mapreport.front.page.PagePresentationSerialiser;
import mapreport.nav.NavigationNode;
import mapreport.nav.NavigationNodeSerializer;
import mapreport.nav.NavigationPath;
import mapreport.nav.NavigationPathSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONHandler {	   
	  public static final Gson gson;
	  static {
		    // Configure GSON
		    final GsonBuilder gsonBuilder = new GsonBuilder();
		    gsonBuilder.registerTypeAdapter(PagePresentation.class, new PagePresentationSerialiser());
		    gsonBuilder.registerTypeAdapter(PageMetaData.class, new PageMetaDataSerialiser());
		    gsonBuilder.registerTypeAdapter(NavigationPath.class, new NavigationPathSerializer());
		    gsonBuilder.registerTypeAdapter(NavigationNode.class, new NavigationNodeSerializer());
		 //   gsonBuilder.registerTypeAdapter(JsonError.class, new JsonErrorSerializer());
		    
		    gsonBuilder.setPrettyPrinting();
		    gson = gsonBuilder.create();
	  }
}
