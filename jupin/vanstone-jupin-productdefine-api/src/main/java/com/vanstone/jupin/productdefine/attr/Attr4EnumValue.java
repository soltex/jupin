/**
 */
package com.vanstone.jupin.productdefine.attr;

import com.vanstone.business.def.AbstractBusinessObject;
import com.vanstone.jupin.common.Constants;

/**
 * 属性枚举值
 * @author shipeng
 */
public class Attr4EnumValue extends AbstractBusinessObject {

	/***/
	private static final long serialVersionUID = 8182529168878012989L;
	
	private Integer id;
	private Attr4Enum attr4Enum;
	private String objectText;
	private int sort = Constants.SYS_DEFAULT_SORT;

	@Override
	public Integer getId() {
		return this.id;
	}

	public Attr4Enum getAttr4Enum() {
		return attr4Enum;
	}

	public void setAttr4Enum(Attr4Enum attr4Enum) {
		this.attr4Enum = attr4Enum;
	}

	public String getObjectText() {
		return objectText;
	}

	public void setObjectText(String objectText) {
		this.objectText = objectText;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
