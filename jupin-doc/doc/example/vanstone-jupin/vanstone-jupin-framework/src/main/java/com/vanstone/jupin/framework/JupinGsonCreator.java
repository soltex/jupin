/**
 * 
 */
package com.vanstone.jupin.framework;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vanstone.business.serialize.GsonCreator;
import com.vanstone.jupin.framework.productdef.AbstractAttribute;
import com.vanstone.jupin.framework.productdef.serializer.AttributeSerializer;

/**
 * @author shipeng
 */
public class JupinGsonCreator extends GsonCreator {
	
	/**
	 * 为AbstractAttribute创建Gson创建器
	 * @return
	 */
	public static Gson createGson4Attribute() {
		Gson gson = new GsonBuilder().registerTypeAdapter(AbstractAttribute.class, new AttributeSerializer()).create();
		return gson;
	}
	
}
