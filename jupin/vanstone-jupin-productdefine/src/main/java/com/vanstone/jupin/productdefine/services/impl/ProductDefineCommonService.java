/**
 * 
 */
package com.vanstone.jupin.productdefine.services.impl;

import org.springframework.stereotype.Service;

/**
 * @author shipeng
 */
@Service("productDefineCommonService")
public class ProductDefineCommonService {

	/**
	 * 验证品类中是否已经存在商品（通过颜色）
	 * @return
	 */
	public boolean validateProductCategoryByColor() {
		return false;
	}
	
	/**
	 * 验证怕你累中是否已经存在商品（通过尺码）
	 * @param sizeTemplateId
	 * @return
	 */
	public boolean validateProductCategoryBySizeTemplate(int sizeTemplateId) {
		return false;
	}
}
