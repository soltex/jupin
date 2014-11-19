/**
 * 
 */
package com.vanstone.jupin.example.product.services;

import com.vanstone.jupin.example.product.Product;

/**
 * @author shipeng
 */
public interface IProductService {
	
	public static final String SERVICE = "productService";
	
	/**
	 * 添加商品
	 * @param product
	 * @return
	 */
	Product addProduct(Product product);
	
	/**
	 * 获取Product信息
	 * @param id
	 * @return
	 */
	Product getProduct(String id);
	
}
