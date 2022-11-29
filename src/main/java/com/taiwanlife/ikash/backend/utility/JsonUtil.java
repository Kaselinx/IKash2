package com.taiwanlife.ikash.backend.utility;
import java.io.IOException ;
import java.io.Serializable ;
import java.math.BigInteger ;

import javax.servlet.ServletInputStream ;

import org.apache.commons.lang3.SerializationUtils ;

import com.fasterxml.jackson.core.JsonGenerator ;
import com.fasterxml.jackson.core.JsonParser ;
import com.fasterxml.jackson.core.JsonProcessingException ;
import com.fasterxml.jackson.core.type.TypeReference ;
import com.fasterxml.jackson.databind.DeserializationContext ;
import com.fasterxml.jackson.databind.JsonDeserializer ;
import com.fasterxml.jackson.databind.JsonNode ;
import com.fasterxml.jackson.databind.JsonSerializer ;
import com.fasterxml.jackson.databind.ObjectMapper ;
import com.fasterxml.jackson.databind.SerializerProvider ;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class JsonUtil
{
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper ().findAndRegisterModules () ;
	
	/**
	 * Constructor
	 */
	private JsonUtil ()
	{
		super () ;
		throw new IllegalStateException ( "JsonUtil utility class" ) ;
	}
	
	/**
	 * @param < F >
	 * @param < T >
	 * @param fromObject
	 * @param toObject
	 * @return < T >
	 */
	public static <F, T > T convertObjectToObject ( F fromObject, Class < T > toObject )
	{
		return OBJECT_MAPPER.convertValue ( fromObject, toObject ) ;
	}
	
	/**
	 * @param < F >
	 * @param < T >
	 * @param fromObject
	 * @param toObjectRef
	 * @return < T >
	 */
	public static < F, T > T convertObjectToObject ( F fromObject, TypeReference < T > toObjectRef )
	{
		return OBJECT_MAPPER.convertValue ( fromObject, toObjectRef ) ;
	}
	
	/**
	 * @param < T >
	 * @param json
	 * @param clazz
	 * @return < T >
	 */
	public static < T > T convertJsonToClass ( String json, Class < T > clazz )
	{
		
		try
		{
			return OBJECT_MAPPER.readValue ( json, clazz ) ;
		}
		catch ( IOException e )
		{
			log.error("IOException. " + e.getMessage() + ", Cause: " + e.getCause());
			throw new IllegalArgumentException ( "The given json value: " + json + " cannot be transformed to Json object" ) ;
		}
		
	}
	
	/**
	 * @param < T >
	 * @param json
	 * @param typeReference
	 * @return < T >
	 */
	public static < T > T convertJsonToObject ( String json, TypeReference < T > typeReference )
	{
		
		try
		{
			return OBJECT_MAPPER.readValue ( json, typeReference ) ;
		}
		catch ( IOException e )
		{
			log.error("IOException. " + e.getMessage() + ", Cause: " + e.getCause());
			throw new IllegalArgumentException ( "The given string value: " + json + " cannot be transformed to Json object", e ) ;
		}
		
	}
	
	/**
	 * @param < T >
	 * @param servletInputStream
	 * @param clazz
	 * @return < T >
	 */
	public static < T > T convertStreamToObject ( ServletInputStream servletInputStream, Class < T > clazz )
	{
		
		try
		{
			return OBJECT_MAPPER.readValue ( servletInputStream, clazz ) ;
		}
		catch ( IOException e )
		{
			log.error("IOException. " + e.getMessage() + ", Cause: " + e.getCause());
			throw new IllegalArgumentException ( "The given stream value: " + servletInputStream + " cannot be transformed to Json object", e ) ;
		}
		
	}
	
	/**
	 * @param < T >
	 * @param servletInputStream
	 * @param typeReference
	 * @return < T >
	 */
	public static < T > T convertStreamToObject ( ServletInputStream servletInputStream, TypeReference < T > typeReference )
	{
		
		try
		{
			return OBJECT_MAPPER.readValue ( servletInputStream, typeReference ) ;
		}
		catch ( IOException e )
		{
			log.error("IOException. " + e.getMessage() + ", Cause: " + e.getCause());
			throw new IllegalArgumentException ( "The given stream value: " + servletInputStream + " cannot be transformed to Json object", e ) ;
		}
		
	}
	
	/**
	 * @param value
	 * @return String
	 */
	public static String convertObjectToJson ( Object value )
	{
		
		try
		{
			return OBJECT_MAPPER.writeValueAsString ( value ) ;
		}
		catch ( JsonProcessingException e )
		{
			log.error("JsonProcessingException. " + e.getMessage() + ", Cause: " + e.getCause());
			throw new IllegalArgumentException ( "The given Json object value: " + value + " cannot be transformed to a String" ) ;
		}
		
	}
	
	/**
	 * @param json
	 * @return JsonNode
	 */
	public static JsonNode convertJsonToJsonNode ( String json )
	{
		
		try
		{
			return OBJECT_MAPPER.readTree ( json ) ;
		}
		catch ( IOException e )
		{
			log.error("IOException. " + e.getMessage() + ", Cause: " + e.getCause());
			throw new IllegalArgumentException ( e ) ;
		}
		
	}
	
	/**
	 * @param < T >
	 * @param value
	 * @return < T >
	 */
	@SuppressWarnings ( "unchecked" )
	public static < T > T clone ( T value )
	{
		return ( value instanceof Serializable ) ? ( T ) SerializationUtils.clone ( ( Serializable ) value ) : convertJsonToClass ( convertObjectToJson ( value ), ( ( Class < T > ) value.getClass () ) ) ;
	}
	
	/**
	 * @version 1
	 * 
	 * @date 2020/08/17
	 */
	public static class XssStringJsonSerializer	extends JsonSerializer < String >
	{
		
		/* (non-Javadoc)
		 * @see com.fasterxml.jackson.databind.JsonSerializer#handledType()
		 */
		@Override
		public Class < String > handledType ()
		{
			return String.class ;
		}
		
		/* (non-Javadoc)
		 * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
		 */
		@Override
		public void serialize ( String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider ) throws IOException
		{
			
			if ( value != null )
			{
				
				String encodedValue = DataUtil.cleanXSS ( value ) ;
				
				jsonGenerator.writeString ( encodedValue ) ;
				
			}
			
		}
		
	}
	
	/**
	 * @version 1
	 * 
	 * @date 2020/08/17
	 */
	public static class LongToStringJsonSerializer extends JsonSerializer < Long >
	{
		
		/* (non-Javadoc)
		 * @see com.fasterxml.jackson.databind.JsonSerializer#handledType()
		 */
		@Override
		public Class < Long > handledType ()
		{
			return Long.class ;
		}
		
		/* (non-Javadoc)
		 * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
		 */
		@Override
		public void serialize ( Long value, JsonGenerator jsonGenerator, SerializerProvider serializers )	throws IOException, JsonProcessingException
		{
			
			if ( value != null )
			{
				
				String encodedValue = String.valueOf ( value ) ;
				
				jsonGenerator.writeString ( encodedValue ) ;
				
			}
			
		}
		
	}
	
	/**
	 * @author Seimo.Zhan
	 * @version 1
	 * 
	 * @date 2020/08/17
	 */
	public static class BigIntegerToStringJsonSerializer extends JsonSerializer < BigInteger >
	{
		
		/* (non-Javadoc)
		 * @see com.fasterxml.jackson.databind.JsonSerializer#handledType()
		 */
		@Override
		public Class < BigInteger > handledType ()
		{
			return BigInteger.class ;
		}
		
		/* (non-Javadoc)
		 * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
		 */
		@Override
		public void serialize ( BigInteger value, JsonGenerator jsonGenerator, SerializerProvider serializers ) throws IOException, JsonProcessingException
		{
			
			if ( value != null )
			{
				
				String encodedValue = String.valueOf ( value ) ;
				
				jsonGenerator.writeString ( encodedValue ) ;
				
			}
			
		}
		
	}
	
	/**
	 * @author Seimo.Zhan
	 * @version 1
	 * 
	 * @date 2020/08/17
	 */
	public static class XssStringJsonDeserializer extends JsonDeserializer < String >
	{
		
		/* (non-Javadoc)
		 * @see com.fasterxml.jackson.databind.JsonDeserializer#handledType()
		 */
		@Override
		public Class < String > handledType ()
		{
			return String.class ;
		}
		
		/* (non-Javadoc)
		 * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
		 */
		@Override
		public String deserialize ( JsonParser parser, DeserializationContext ctxt ) throws IOException, JsonProcessingException
		{
			
			String value = parser.getValueAsString () ;
			
			return DataUtil.cleanXSS ( value ) ;
			
		}
		
	}
	
}
