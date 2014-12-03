/**
 * 
 */
package com.vanstone.jupin.common.sdk.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanstone.business.MyAssert4Business;
import com.vanstone.jupin.business.sdk.common.CommonSDKManager;
import com.vanstone.jupin.ecs.product.define.Brand;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Color;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Size;
import com.vanstone.jupin.ecs.product.define.attribute.sku.SizeTemplate;
import com.vanstone.jupin.ecs.product.define.services.BrandService;
import com.vanstone.jupin.ecs.product.define.services.ColorTableService;
import com.vanstone.jupin.ecs.product.define.services.SizeService;

/**
 * @author shipeng
 */
@Service("commonSDKManager")
public class CommonSDKManagerImpl implements CommonSDKManager {

	@Autowired
	private ColorTableService colorTableService;
	@Autowired
	private SizeService sizeService;
	@Autowired
	private BrandService brandService;
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.business.sdk.common.CommonSDKManager#getAndValidateColor(int)
	 */
	@Override
	public Color getAndValidateColor(int id) {
		Color color = this.colorTableService.getColor(id);
		MyAssert4Business.notNull(color);
		return color;
	}

	@Override
	public SizeTemplate getSizeTemplateAndValidate(int id) {
		SizeTemplate sizeTemplate = this.sizeService.getSizeTemplate(id);
		MyAssert4Business.notNull(sizeTemplate);
		return sizeTemplate;
	}
	
	@Override
	public Size getSizeAndValidate(int id) {
		Size size = this.sizeService.getSize(id);
		MyAssert4Business.notNull(size);
		return size;
	}

	@Override
	public Brand getBrandAndValidate(int id) {
		Brand brand = this.brandService.getBrand(id);
		MyAssert4Business.notNull(brand);
		return brand;
	}
	
}
