/**
 * 
 */
package com.vanstone.jupin.example.product.services.impl;

import java.util.Map;

import com.vanstone.jupin.example.product.Product;
import com.vanstone.jupin.example.product.persistence.object.PDTProductDO;
import com.vanstone.jupin.framework.productdef.AbstractAttribute;
import com.vanstone.jupin.framework.productdef.AbstractAttributeValue;

/**
 * @author shipeng
 *
 */
public class BeanUtil {
	
	public static PDTProductDO toPdtProductDO(Product product) {
		PDTProductDO model = new PDTProductDO();
		model.setId(product.getId());
		model.setProductCategoryId(product.getProductCategory().getId());
		model.setProductName(product.getProductName());
		model.setSubProductName(product.getSubProductName());
		model.setSkuNO(product.getSkuNO());
		model.setBrief(product.getBrief());
		model.setContent(product.getContent());
		model.setMarketPrice(product.getMarketPrice());
		model.setPrice(product.getPrice());
		model.setBrandId(product.getBrand() != null ? product.getBrand().getId() : null);
		model.setPackingList(product.getPackingList());
		model.setAfterSaleInfo(product.getAfterSaleInfo());
		model.setInventory(product.getInventory());
		model.setImages(product.getImages());
		model.setSysInsertTime(product.getSysInsertTime());
		model.setSysUpdateTime(product.getSysUpdateTime());
		if (product.getProductProperties() != null && product.getProductProperties().size() >0) {
			for (Map.Entry<AbstractAttribute, AbstractAttributeValue> entry : product.getProductProperties().entrySet()) {
				model.addProductProperty(entry.getKey().getId(), entry.getValue());
			}
		}
		return model;
	}
}
