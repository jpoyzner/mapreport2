package mapreport.controller;

import mapreport.util.JcsExample.City;

import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;
import org.apache.commons.jcs.access.exception.CacheException;

public class Cache {
	private static CacheAccess<String, String> cache  = JCS.getInstance( "default" );
	
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

    public static String retrieveFromCache( String key ) 
    {
        return cache.get( key );
    }
}
