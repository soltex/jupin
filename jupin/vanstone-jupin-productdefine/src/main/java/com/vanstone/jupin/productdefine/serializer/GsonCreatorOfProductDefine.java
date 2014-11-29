/**
 * 
 */
package com.vanstone.jupin.productdefine.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vanstone.jupin.productdefine.attr.AbstractAttribute;

/**
 * @author shipeng
 *
 */
public class GsonCreatorOfProductDefine {
	
	/**
	 * 创建品类序列化定义
	 * @return
	 */
	public static Gson create() {
		Gson gson = new GsonBuilder().registerTypeAdapter(AbstractAttribute.class, new AttributeCacheSerializer()).disableHtmlEscaping().create();
		return gson;
	}
	
}
