/**
 * 
 */
package com.vanstone.jupin.ecs.product.define.services;

import com.vanstone.jupin.common.JupinException;

/**
 * 当品类节点下存在商品信息，不允许修改品类相关信息
 * @author shipeng
 */
public class ExistProductsNotAllowWriteException extends JupinException {
	
	/***/
	private static final long serialVersionUID = 539668339091065204L;

}
