/**
 * 
 */
package com.vanstone.jupin.ebs.pm.framework.serializer;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.AbstractAttribute;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.Attr4Enum;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.Attr4Text;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.AttributeType;

/**
 * Product Attribute Gson Deserializer by AttributeType
 * @author shipeng
 */
public class PDAttributeSerializer implements JsonDeserializer<AbstractAttribute>,JsonSerializer<AbstractAttribute> {

	@Override
	public AbstractAttribute deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		if (json != null) {
			JsonObject jsonObject = json.getAsJsonObject();
			String attributeType = jsonObject.get("attributeType").getAsString();
			Gson gson = new Gson();
			if (attributeType != null && attributeType.equalsIgnoreCase(AttributeType.Text.toString())) {
				//text
				return gson.fromJson(jsonObject, Attr4Text.class);
			}
			if (attributeType != null && attributeType.equalsIgnoreCase(AttributeType.Enum.toString())) {
				return gson.fromJson(jsonObject, Attr4Enum.class);
			}
		}
		return null;
	}

	@Override
	public JsonElement serialize(AbstractAttribute src, Type typeOfSrc, JsonSerializationContext context) {
		if (src != null) {
			Gson gson = new Gson();
			return gson.toJsonTree(src);
		}
		return null;
	}
	
}
