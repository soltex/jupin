/**
 * 
 */
package com.vanstone.jupin.framework.productdef.serializer;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.vanstone.business.serialize.GsonCreator;
import com.vanstone.jupin.framework.productdef.AbstractAttribute;
import com.vanstone.jupin.framework.productdef.Attr4Enum;
import com.vanstone.jupin.framework.productdef.Attr4Lang;
import com.vanstone.jupin.framework.productdef.AttributeType;

/**
 * @author shipeng
 *
 */
public class AttributeSerializer implements JsonDeserializer<AbstractAttribute> {

	@Override
	public AbstractAttribute deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Gson gson = GsonCreator.create();
		
		JsonObject jsonObject = (JsonObject)json;
		String strAttributeType = jsonObject.get("attributeType").getAsString();
		AttributeType attributeType = Enum.valueOf(AttributeType.class, strAttributeType);
		if (attributeType.equals(AttributeType.Lang)) {
			// Lang类型
			return gson.fromJson(json, Attr4Lang.class);
		} else {
			return gson.fromJson(json, Attr4Enum.class);
		}
	}

}
