/**
 * 
 */
package com.vanstone.jupin.example.product.services.impl;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.vanstone.business.ObjectHasSubObjectException;
import com.vanstone.jupin.example.product.Brand;
import com.vanstone.jupin.example.product.services.IBrandService;
import com.vanstone.jupin.framework.common.ImageObject;
import com.vanstone.jupin.framework.services.AbstractJupinService;

/**
 * @author shipeng
 */
@Service("brandService")
public class BrandServiceImpl extends AbstractJupinService implements IBrandService {
	
	/**  */
	private static final long serialVersionUID = -1229505939989241048L;

	/* (non-Javadoc)
	 * @see com.vanstone.jupin.example.product.services.IBrandService#addBrand(com.vanstone.jupin.example.product.Brand)
	 */
	@Override
	public Brand addBrand(Brand brand) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.example.product.services.IBrandService#getBrand(int)
	 */
	@Override
	public Brand getBrand(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, Brand> getBrandsMapByIds(Collection<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.example.product.services.IBrandService#updateBaseBrandInfo(com.vanstone.jupin.example.product.Brand)
	 */
	@Override
	public Brand updateBaseBrandInfo(Brand brand) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.vanstone.jupin.example.product.services.IBrandService#updateLogoBrand(int, com.vanstone.jupin.framework.common.ImageObject)
	 */
	@Override
	public Brand updateLogoBrand(int brandId, ImageObject imageObject) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.vanstone.jupin.example.product.services.IBrandService#deleteBrand(int)
	 */
	@Override
	public void deleteBrand(int id) throws ObjectHasSubObjectException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.vanstone.jupin.example.product.services.IBrandService#getBrands(java.lang.String, int, int)
	 */
	@Override
	public Brand getBrands(String key, int offset, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.vanstone.jupin.example.product.services.IBrandService#getTotalBrands(java.lang.String)
	 */
	@Override
	public int getTotalBrands(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

}
