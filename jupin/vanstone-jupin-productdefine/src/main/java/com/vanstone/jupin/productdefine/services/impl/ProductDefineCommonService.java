/**
 * 
 */
package com.vanstone.jupin.productdefine.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @author shipeng
 */
@Service("productDefineCommonService")
@Validated
public class ProductDefineCommonService {

	/**
	 * 验证品类中是否已经存在商品（通过颜色）
	 * @return
	 */
	public boolean validateProductCategoryByColor() {
		return true;
	}
	
	/**
	 * 验证怕你累中是否已经存在商品（通过尺码）
	 * @param sizeTemplateId
	 * @return
	 */
	public boolean validateProductCategoryBySizeTemplate(int sizeTemplateId) {
		return true;
	}
	
	/**
	 * 验证Brand是否可以修改
	 * @param brandID
	 * @return
	 */
	public boolean validateProductCategoryByBrandID(int brandID) {
		return true;
	}
	
	/**
	 * 刷新ProductCategory缓冲
	 * @param categoryId
	 */
	public void refreshProductCateogryCache(int categoryId) {
		
	}
}
