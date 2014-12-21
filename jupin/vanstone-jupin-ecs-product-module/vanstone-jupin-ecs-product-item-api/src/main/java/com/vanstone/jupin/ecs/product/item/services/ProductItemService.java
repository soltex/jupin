/**
 * 
 */
package com.vanstone.jupin.ecs.product.item.services;

import java.util.Collection;

import com.vanstone.jupin.ecs.product.item.Product;
import com.vanstone.jupin.ecs.product.item.ProductSku;

/**
 * 产品业务接口
 * @author shipeng
 */
public interface ProductItemService {
	
	/**
	 * 增加商品
	 * @param product
	 * @param productSkus
	 * @return
	 * @throws AttributeNotFoundException
	 * @throws AttributeRequiredException
	 */
	Product addProduct(Product product, Collection<ProductSku> productSkus) throws AttributeNotFoundException, AttributeRequiredException;
	
	Product updateBaseProductInfo(Product product) throws AttributeNotFoundException, AttributeRequiredException;
	
	ProductSku updateProductSku(ProductSku productSku);
	
	
}
