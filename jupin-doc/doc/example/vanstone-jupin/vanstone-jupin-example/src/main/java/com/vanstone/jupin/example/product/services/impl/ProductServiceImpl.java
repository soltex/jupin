/**
 * 
 */
package com.vanstone.jupin.example.product.services.impl;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanstone.business.serialize.GsonCreator;
import com.vanstone.common.MyAssert;
import com.vanstone.dal.id.IDBuilder;
import com.vanstone.jupin.example.product.Product;
import com.vanstone.jupin.example.product.persistence.PDTProductDODao;
import com.vanstone.jupin.example.product.persistence.object.PDTProductDO;
import com.vanstone.jupin.example.product.services.IProductService;
import com.vanstone.jupin.framework.common.ImageObject;
import com.vanstone.jupin.framework.services.AbstractJupinService;

/**
 * @author shipeng
 */
@Service("productService")
public class ProductServiceImpl extends AbstractJupinService implements IProductService {

	/**  */
	private static final long serialVersionUID = 7335629796805534804L;
	
	@Autowired
	private PDTProductDODao pdtProductDODao;
	
	@Override
	public Product addProduct(Product product) {
		product.setId(IDBuilder.base58Uuid());
		product.setSysInsertTime(new Date());
		product.setSysUpdateTime(new Date());
		this.pdtProductDODao.save(BeanUtil.toPdtProductDO(product));
		return product;
	}

	@Override
	public Product getProduct(String id) {
		MyAssert.hasText(id);
		PDTProductDO model = this.pdtProductDODao.findById(id);
		if (model == null) {
			return null;
		}
		System.out.println(model);
		Collection<ImageObject> imageObjects = model.getImages();
		if (imageObjects != null && imageObjects.size() >0) {
			for (ImageObject imageObject : imageObjects) {
				System.out.println(GsonCreator.getPrettyString(imageObject));
			}
		}
		return null;
	}
	
}
