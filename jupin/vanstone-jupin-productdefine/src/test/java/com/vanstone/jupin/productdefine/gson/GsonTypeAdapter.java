/**
 * 
 */
package com.vanstone.jupin.productdefine.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vanstone.jupin.productdefine.attr.AbstractAttribute;
import com.vanstone.jupin.productdefine.serializer.AttributeCacheSerializer;

/**
 * @author shipeng
 *
 */
public class GsonTypeAdapter {
	
	public static void main(String[] args) {
		Gson gson = new GsonBuilder().registerTypeAdapter(AbstractAttribute.class, new AttributeCacheSerializer()).disableHtmlEscaping().create();
		System.out.println(gson);
	}
}
