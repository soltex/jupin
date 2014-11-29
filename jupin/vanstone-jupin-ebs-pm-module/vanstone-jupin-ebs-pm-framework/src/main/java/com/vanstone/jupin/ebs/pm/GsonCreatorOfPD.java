/**
 * 
 */
package com.vanstone.jupin.ebs.pm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vanstone.jupin.ebs.pm.framework.serializer.PDAttributeSerializer;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.AbstractAttribute;

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
