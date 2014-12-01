/**
 * 
 */
package com.vanstone.jupin.ecs.product;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vanstone.jupin.ecs.product.define.attribute.AbstractAttribute;
import com.vanstone.jupin.ecs.product.framework.serializer.PDAttributeSerializer;

/**
 * @author shipeng
 *
 */
public class GsonCreatorOfPD {
	
	/**
	 * 创建品类序列化定义
	 * @return
	 */
	public static Gson create() {
		Gson gson = new GsonBuilder().registerTypeAdapter(AbstractAttribute.class, new PDAttributeSerializer()).disableHtmlEscaping().create();
		return gson;
	}
	
}
