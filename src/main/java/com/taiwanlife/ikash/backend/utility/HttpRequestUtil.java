package com.taiwanlife.ikash.backend.utility;

import java.io.BufferedReader ;
import java.io.IOException ;
import java.io.InputStream ;
import java.io.InputStreamReader ;
import java.nio.charset.StandardCharsets ;

import javax.servlet.http.HttpServletRequest ;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpRequestUtil
{
	
	/**
	 * Constructor
	 */
	private HttpRequestUtil ()
	{
		super () ;
		throw new IllegalStateException ( "HttpRequestUtil utility class" ) ;
	}
	
	/**
	 * @param request
	 * @return String
	 * @throws IOException
	 */
	public static String getBodyContent ( HttpServletRequest request ) {
		StringBuilder sb = new StringBuilder () ;
        
        try (InputStream inputStream = request.getInputStream ();
             BufferedReader reader = new BufferedReader (new InputStreamReader ( inputStream, StandardCharsets.UTF_8 ), 2048) ) {
        	String line ;
            
            while ((line = reader.readLine ()) != null )  {
                sb.append(line).append(System.lineSeparator()) ;
            }
        } catch ( IOException e ) {
        	log.error("IOException. " + e.getMessage() + ", Cause: " + e.getCause());
        }
        
        return sb.toString () ;
    }
}
