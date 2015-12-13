package mapreport.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import mapreport.news.News;
import mapreport.util.Log;

import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;
import org.apache.commons.jcs.access.exception.CacheException;
import org.apache.commons.jcs.engine.control.CompositeCacheManager;

public class Cache {
	private static CacheAccess<String, Object> cache;
	
	static {
		Log.info("Cache configureCacheFile");
		CompositeCacheManager ccm = CompositeCacheManager
				.getUnconfiguredInstance(); 
		Properties props = new Properties();		
		InputStream is = ccm.getClass().getResourceAsStream( "c:\\git\\mapreport2\\config\\cache.ccf" );  // "C:/git/mapreport2/config/cache.ccf"
		Log.info("Cache is:" + is);
		
		try {
			FileReader f = new FileReader("C:\\git\\mapreport2\\config\\cache.ccf");
			File currentDir = new File("").getAbsoluteFile();
			Log.info("Cache configureCacheFile currentDir:" + currentDir.toString()); 
			
			Log.info("Cache configureCacheFile f:" + f.toString()); 
			props.load(f/* load properties from some location defined by your app */);

		//	JCS.setConfigFilename("C:/git/mapreport2/config/cache.ccf");
		//	JCS.setConfigFilename("cache.ccf");
			cache  = JCS.getInstance( "default" );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ccm.configure(props);
	}
	
    public static void putInCache( String key, String value ) 
    {
        try 
        {
            cache.put( key, value );
        }
        catch ( CacheException e ) 
        {
            System.out.println( String.format( "Problem putting string %s in the cache, for key %s%n%s",
            		value, key, e.getMessage() ) );
        }
    }

    public static Object retrieveFromCache( String key ) 
    {
        return cache.get( key );
    }
    
    public static void putInCache( String key, Map<Integer, News> value ) 
    {
        try 
        {
            cache.put( key, value );
        }
        catch ( CacheException e ) 
        {
            System.out.println( String.format( "Problem putting string %s in the cache, for key %s%n%s",
            		value, key, e.getMessage() ) );
        }
    }

    
    // Map<Integer, News>
}
