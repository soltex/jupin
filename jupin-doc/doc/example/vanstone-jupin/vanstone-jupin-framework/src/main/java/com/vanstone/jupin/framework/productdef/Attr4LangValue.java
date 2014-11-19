/**
 * 
 */
package com.vanstone.jupin.framework.productdef;

/**
 * @author shipeng
 * 
 */
public class Attr4LangValue extends AbstractAttributeValue {

	private String value;

	public Attr4LangValue() {
		super(AttributeType.Lang);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
