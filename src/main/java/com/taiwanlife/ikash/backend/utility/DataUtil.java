package com.taiwanlife.ikash.backend.utility;

import java.io.ByteArrayInputStream ;
import java.io.ByteArrayOutputStream ;
import java.io.ObjectInputStream ;
import java.io.ObjectOutputStream ;
import java.io.Serializable ;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils ;
import org.apache.commons.lang3.StringUtils ;

@Slf4j
public class DataUtil
{
	
	/**
	 * Constructor
	 */
	private DataUtil ()
	{
	    throw new IllegalStateException ( "Utility class" ) ;
	}
	
	/**
	* Convert object to byte array
	* @param object
	* @return
	*/
	public static byte [] fromObjectToByteArray ( Serializable object )
	{
		return SerializationUtils.serialize ( object ) ;
	}
	
	/**
	 * Convert byte array to object
	 * @param bytes
	 * @return
	 */
	public static Object fromByteArrayToObject ( byte [] bytes )
	{
		return SerializationUtils.deserialize ( bytes ) ;
	}
	
	/**
	 * @param <T>
	 * @param source
	 * @return
	 */
	@SuppressWarnings ( "unchecked" )
	public static < T > T getFromByte ( byte [] source )
	{
		
		if ( source == null ) return null ;
		
		try ( ByteArrayInputStream bis = new ByteArrayInputStream ( source ) ;
				ObjectInputStream ois = new ObjectInputStream ( bis ) )
		{
			
			T value = ( T ) ois.readObject () ;
			
			return value ;
			
		} catch ( Exception e ) {
			log.error("Exception.");
			
			throw new RuntimeException ( e.getMessage () ) ;
		}
	}

	/**
	 * @param object
	 * @return
	 */
	public static byte [] toByte ( Object object )
	{
		
		if ( object == null ) return null ;
		
		byte [] data = null ;
		
		try ( ByteArrayOutputStream bos = new ByteArrayOutputStream () ;
				ObjectOutputStream oos = new ObjectOutputStream ( bos ) )
		{
			oos.writeObject ( object ) ;
			oos.flush () ;
			data = bos.toByteArray () ;

			return data ;
			
		} catch ( Exception e ) {
			log.error("Exception.");

			throw new RuntimeException ( e.getMessage () ) ;
		}
	}
	
	/**
	 * @param target sensitive data(like IMSI). e.g ('5476377362899153869')
	 * @param mask mask pattern . e.g. '##########*********'
	 * @return value with mask e.g. '5476377362*********'
	 */
	public static String mask ( String target, String mask )
	{

		if ( StringUtils.isBlank ( mask ) )
		{
			return target ;
		}
		
		int maskStart = mask.indexOf ( "*" ) ;
		int maskEnd = mask.lastIndexOf ( "*" ) + 1 ;
		
		mask = StringUtils.rightPad ( "", maskEnd - maskStart, "*" ) ;
		
		return StringUtils.overlay ( target, mask, maskStart, maskEnd ) ;
		
	}
	
	/**
	 * replace sensitive string (XSS)
	 * 
	 * @param value
	 * @return
	 */
	public static String cleanXSS ( String value )
	{

		if ( value == null || value.length () == 0 )
		{
			return value ;
		}

		value = value.replaceAll ( "<", "& lt;" ).replaceAll ( ">", "& gt;" ) ;
		value = value.replaceAll ( "\\(", "& #40;" ).replaceAll ( "\\)", "&#41;" ) ;
		value = value.replaceAll ( "'", "& #39;" ) ;
		value = value.replaceAll ( "eval\\((.*)\\)", "" ) ;
		value = value.replaceAll ( "[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"" ) ;
		value = value.replaceAll ( "script", "" ) ;
		
		return value ;
	}
}
