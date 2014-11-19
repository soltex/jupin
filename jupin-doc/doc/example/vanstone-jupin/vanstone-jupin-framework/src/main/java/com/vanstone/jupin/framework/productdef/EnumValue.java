/**
 * 
 */
package com.vanstone.jupin.framework.productdef;

/**
 * @author shipeng
 */
public class EnumValue {

	/** 引用Id */
	private Integer id;
	/** 枚举值 */
	private String objectValue;
	/** 内容排序 */
	private Integer sort = 0;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

//	@Override
//	public boolean equals(Object obj) {  
//        if (this == obj)  
//            return true;  
//        if (!(obj instanceof EnumValue))  
//            return false;  
//        final EnumValue other = (EnumValue) obj;  
//  
//        if (this.id.equals(other.getId()))  {
//        	return true;  
//        }else{
//        	return false;  
//        }
//	} 
//	
//	@Override  
//	public int hashCode() {
//		if (this.getId() == null) {
//			return super.hashCode();
//		} else {
//			int result = 1;
//			return 31 * result + this.getId().hashCode();
//		}
//	}
	
}
