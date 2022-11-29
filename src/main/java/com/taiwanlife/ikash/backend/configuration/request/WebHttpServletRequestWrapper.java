package com.taiwanlife.ikash.backend.configuration.request;



import java.io.ByteArrayInputStream;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

import javax.servlet.ReadListener ;
import javax.servlet.ServletInputStream ;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletRequestWrapper ;

import com.taiwanlife.ikash.backend.utility.HttpRequestUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebHttpServletRequestWrapper extends HttpServletRequestWrapper
{   private ThreadLocal tLocal = new ThreadLocal();
	
	//private final byte [] body ;
	
	/**
	 * Constructor
	 */
	public WebHttpServletRequestWrapper ( HttpServletRequest request )
	{
		 super ( request ) ;
	     byte[] body = HttpRequestUtil.getBodyContent ( request ).getBytes ( StandardCharsets.UTF_8 ) ;

	     tLocal.set(body);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getInputStream()
	 */
	@Override
    public ServletInputStream getInputStream () throws IOException
	{
		byte[] body = (byte[])tLocal.get();

        final ByteArrayInputStream bais = new ByteArrayInputStream ( body ) ;
        
        return new ServletInputStream ()
        {
        	
            /* (non-Javadoc)
             * @see java.io.InputStream#read()
             */
            @Override
            public int read () throws IOException
            {
                return bais.read () ;
            }
            
            /* (non-Javadoc)
             * @see javax.servlet.ServletInputStream#isFinished()
             */
            @Override
            public boolean isFinished ()
            {
                return false ;
            }
            
            /* (non-Javadoc)
             * @see javax.servlet.ServletInputStream#isReady()
             */
            @Override
            public boolean isReady ()
            {
                return false ;
            }
            
            /* (non-Javadoc)
             * @see javax.servlet.ServletInputStream#setReadListener(javax.servlet.ReadListener)
             */
            @Override
            public void setReadListener ( ReadListener readListener )
            {

            }
            
        } ;
        
    }
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getReader()
	 */
	
	@Override
 	public Map<String, String[]> getParameterMap() {
 		Map<String, String[]> map = super.getParameterMap();
 		Map<String, String[]> paraMap = new HashMap<String, String[]>();
 		if (map == null) {
 			return null;
 		}

		String[] values = null;
		String key;
		Map.Entry<String, String[]> entry;
		Iterator<Map.Entry<String, String[]>> it = map.entrySet().iterator();
		while (it.hasNext()){
			entry = it.next();

			key = entry.getKey();
			values = entry.getValue();

 			try {
 				if (values != null) {
 					for (int i = 0; i < values.length; i++) {
 						try {
 							values[i] = xssEncode(values[i]);
 						} catch (Exception e) {
 							log.error("Exception" + e.getMessage());
 						}
 					}
 				}
 				paraMap.put(xssEncode(key), values);
 			} catch (Exception e) {
 				log.error("Exception. " + e.getMessage());
 			}
 		}
 		return paraMap;
 	}
	
	@Override
	public BufferedReader getReader () throws IOException
	{
		return new BufferedReader ( new InputStreamReader ( getInputStream () ) ) ;
	}
	
	@Override
 	public String getParameter(String name) {
 		String value = super.getParameter(name);
 		if (value != null) {
 			value = xssEncode(value);
 		}
 		return value;
 	}
	
	@Override
 	public String getHeader(String name) {
 
 		String value = super.getHeader(name);
 		if (value != null) {
 			value = xssEncode(value);
 		}
 		return value;
 	}
	
	/**
 	 * @param
 	 * @return
 	 */
 	private static String xssEncode(String value) {
 		if (value != null) {
 			/*
 			 * value = value.replace("<", "&lt;"); value = value.replace(">",
 			 * "&gt;");
 			 */
 
 			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>",Pattern.CASE_INSENSITIVE);
 			value = scriptPattern.matcher(value).replaceAll("");
 
 			scriptPattern = Pattern.compile("</script>",Pattern.CASE_INSENSITIVE);
 			value = scriptPattern.matcher(value).replaceAll("");
 
			scriptPattern = Pattern.compile("<img.*?on.*?=.*?>",Pattern.CASE_INSENSITIVE);
 			value = scriptPattern.matcher(value).replaceAll("");
 
			scriptPattern = Pattern.compile("<script(.*?)>",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
 
 			scriptPattern = Pattern.compile("eval\\((.*?)\\)",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL);
 			value = scriptPattern.matcher(value).replaceAll("");
 
 			scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL);
 			value = scriptPattern.matcher(value).replaceAll("");
 
 			scriptPattern = Pattern.compile("expression\\((.*?)\\)",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL);
 			value = scriptPattern.matcher(value).replaceAll("");
 
 			scriptPattern = Pattern.compile("javascript:",Pattern.CASE_INSENSITIVE);
 			value = scriptPattern.matcher(value).replaceAll(""); 
 			
 			scriptPattern = Pattern.compile("vbscript:",Pattern.CASE_INSENSITIVE);
 			value = scriptPattern.matcher(value).replaceAll("");

 			scriptPattern = Pattern.compile("onload(.*?)=",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL);
 			value = scriptPattern.matcher(value).replaceAll("");
 
 			scriptPattern = Pattern.compile("<%.*?java.*?%>", Pattern.CASE_INSENSITIVE| Pattern.MULTILINE | Pattern.DOTALL);
 			value = scriptPattern.matcher(value).replaceAll("");

 			scriptPattern = Pattern.compile("<jsp:.*?>.*?</jsp:.*?>",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL);
 			value = scriptPattern.matcher(value).replaceAll("");
 
 			scriptPattern = Pattern.compile("<meta.*?>",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL);
 			value = scriptPattern.matcher(value).replaceAll("");

 		}
 		return value;
 	}
}
