/**
 * 
 */
package com.vanstone.jupin.framework.productdef;

import com.vanstone.jupin.framework.common.ImageObject;

/**
 * @author shipeng
 */
public class Attr4EnumValue extends AbstractAttributeValue {

	private EnumValue enumValue;
	private String aliasObjectValue;
	private ImageObject aliasImageObject;
	
	public Attr4EnumValue() {
		super(AttributeType.Enum);
	}

	public EnumValue getEnumValue() {
		return enumValue;
	}

	public void setEnumValue(EnumValue enumValue) {
		this.enumValue = enumValue;
	}

	public String getAliasObjectValue() {
		return aliasObjectValue;
	}

	public void setAliasObjectValue(String aliasObjectValue) {
		this.aliasObjectValue = aliasObjectValue;
	}

	public ImageObject getAliasImageObject() {
		return aliasImageObject;
	}

	public void setAliasImageObject(ImageObject aliasImageObject) {
		this.aliasImageObject = aliasImageObject;
	}

	/**
	 * 是否存在别名
	 * @return
	 */
	public boolean isAlias() {
		if (this.getAliasObjectValue() != null || this.getAliasImageObject() != null) {
			return true;
		}
		return false;
	}
	
}
