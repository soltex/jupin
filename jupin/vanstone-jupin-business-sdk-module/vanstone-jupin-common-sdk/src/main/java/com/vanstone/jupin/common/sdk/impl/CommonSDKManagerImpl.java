/**
 * 
 */
package com.vanstone.jupin.common.sdk.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanstone.business.MyAssert4Business;
import com.vanstone.jupin.authority.Admin;
import com.vanstone.jupin.authority.services.AdminService;
import com.vanstone.jupin.business.sdk.common.CommonSDKManager;
import com.vanstone.jupin.ecs.product.define.Brand;
import com.vanstone.jupin.ecs.product.define.ProductCategoryDetail;
import com.vanstone.jupin.ecs.product.define.attribute.AbstractAttribute;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4Enum;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4EnumValue;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4Text;
import com.vanstone.jupin.ecs.product.define.attribute.AttributeType;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Color;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Size;
import com.vanstone.jupin.ecs.product.define.attribute.sku.SizeTemplate;
import com.vanstone.jupin.ecs.product.define.services.AttributeService;
import com.vanstone.jupin.ecs.product.define.services.BrandService;
import com.vanstone.jupin.ecs.product.define.services.ProductCategoryService;
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
	@Autowired
	private ProductCategoryService categoryService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private AttributeService attributeService;
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.business.sdk.common.CommonSDKManager#getAndValidateColor(int)
	 */
	@Override
	public Color getColorAndValidate(int id) {
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

	@Override
	public ProductCategoryDetail getProductCategoryDetailAndValidate(int id) {
		ProductCategoryDetail productCategoryDetail = this.categoryService.getProductCategoryDetail(id);
		MyAssert4Business.notNull(productCategoryDetail);
		return productCategoryDetail;
	}

	@Override
	public Admin getAdminAndValidate(String id) {
		Admin admin = this.adminService.getAdmin(id);
		MyAssert4Business.notNull(admin);
		return admin;
	}

	@Override
	public AbstractAttribute getAttributeAndValidate(int id) {
		AbstractAttribute attribute = this.attributeService.getAttribute(id);
		MyAssert4Business.objectInitialized(attribute);
		return attribute;
	}

	@Override
	public Attr4Text getAttr4TextAndValidate(int id) {
		AbstractAttribute attribute = this.getAttributeAndValidate(id);
		if (!attribute.getAttributeType().equals(AttributeType.Text)) {
			throw new IllegalArgumentException();
		}
		return (Attr4Text)attribute;
	}
	
	@Override
	public Attr4Enum getAttr4EnumAndValidate(int id) {
		AbstractAttribute attribute = this.getAttributeAndValidate(id);
		if (!attribute.getAttributeType().equals(AttributeType.Enum)) {
			throw new IllegalArgumentException();
		}
		return (Attr4Enum)attribute;
	}

	@Override
	public Attr4EnumValue getAttr4EnumValueAndValidate(int id) {
		Attr4EnumValue attr4EnumValue = this.attributeService.getAttr4EnumValue(id);
		if (attr4EnumValue == null) {
			throw new IllegalArgumentException();
		}
		return attr4EnumValue;
	}
	
}
