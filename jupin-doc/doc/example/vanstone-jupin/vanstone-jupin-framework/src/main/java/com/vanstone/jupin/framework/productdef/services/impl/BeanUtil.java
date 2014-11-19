/**
 * 
 */
package com.vanstone.jupin.framework.productdef.services.impl;

import com.vanstone.business.lang.EnumUtils;
import com.vanstone.business.lang.TrueFalseUtil;
import com.vanstone.jupin.framework.Constants;
import com.vanstone.jupin.framework.common.ImageObject;
import com.vanstone.jupin.framework.productdef.Attr4Enum;
import com.vanstone.jupin.framework.productdef.Attr4Lang;
import com.vanstone.jupin.framework.productdef.AttributeScope;
import com.vanstone.jupin.framework.productdef.EnumValue;
import com.vanstone.jupin.framework.productdef.ProductCategory;
import com.vanstone.jupin.framework.productdef.persistence.object.PDTAttributeDefDO;
import com.vanstone.jupin.framework.productdef.persistence.object.PDTAttributeEnumValueDO;
import com.vanstone.jupin.framework.productdef.persistence.object.PDTCategoryDO;
import com.vanstone.weedfs.client.WeedFile;

/**
 * @author shipeng
 */
public class BeanUtil {
	
	/**
	 * Parent Category未转换
	 * 转换为ProductCategory
	 * @param pdtCategoryDO
	 * @return
	 */
	public static ProductCategory toProductCategory(PDTCategoryDO pdtCategoryDO) {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setId(pdtCategoryDO.getId());
		productCategory.setCategoryName(pdtCategoryDO.getCategoryName());
		productCategory.setDescription(pdtCategoryDO.getDescription());
		productCategory.setCategoryBindPage(pdtCategoryDO.getCategoryBindPage());
		if (pdtCategoryDO.getCoverFileId() != null && !"".equals(pdtCategoryDO.getCoverFileId())) {
			WeedFile weedFile = new WeedFile(pdtCategoryDO.getCoverFileId(), pdtCategoryDO.getCoverFileExt());
			ImageObject imageObject = new ImageObject();
			imageObject.setImageWeedFile(weedFile);
			imageObject.setWidth(pdtCategoryDO.getCoverFileWidth());
			imageObject.setHeight(pdtCategoryDO.getCoverFileHeight());
			productCategory.setCoverImageObject(imageObject);
		}
		productCategory.setLeafable(TrueFalseUtil.parseBool(pdtCategoryDO.getLeafable()));
		productCategory.setSort(pdtCategoryDO.getSort());
		return productCategory;
	}
	
	/**
	 * 转换为PDTCategoryDO 
	 * @param productCategory
	 * @return
	 */
	public static PDTCategoryDO toPDTCategoryDO(ProductCategory productCategory) {
		PDTCategoryDO pdtCategoryDO = new PDTCategoryDO();
		pdtCategoryDO.setId(productCategory.getId());
		pdtCategoryDO.setCategoryName(productCategory.getCategoryName());
		pdtCategoryDO.setDescription(productCategory.getDescription());
		pdtCategoryDO.setCategoryBindPage(productCategory.getCategoryBindPage());
		if (productCategory.getCoverImageObject() != null) {
			pdtCategoryDO.setCoverFileId(productCategory.getCoverImageObject().getImageWeedFile().getFileid());
			pdtCategoryDO.setCoverFileExt(productCategory.getCoverImageObject().getImageWeedFile().getExtName());
			pdtCategoryDO.setCoverFileWidth(productCategory.getCoverImageObject().getWidth());
			pdtCategoryDO.setCoverFileHeight(productCategory.getCoverImageObject().getHeight());
		}
		if (productCategory.getParentProductCategory() != null) {
			pdtCategoryDO.setParentId(productCategory.getParentProductCategory().getId());
		}else{
			pdtCategoryDO.setParentId(Constants.DEFAULT_ROOT_PRODUCT_CATEGORY_ID);
		}
		pdtCategoryDO.setSort(productCategory.getSort());
		pdtCategoryDO.setLeafable(TrueFalseUtil.parseInt(productCategory.isLeafable()));
		return pdtCategoryDO;
	}
	
	/**
	 * 转化为Attr4Lang
	 * @param pdtAttributeDefDO
	 * @return
	 */
	public static Attr4Lang toAttr4Lang(PDTAttributeDefDO pdtAttributeDefDO) {
		Attr4Lang attr4Lang = new Attr4Lang((AttributeScope)EnumUtils.getByCode(pdtAttributeDefDO.getAttributeScope(), AttributeScope.class));
		attr4Lang.setAttributeName(pdtAttributeDefDO.getAttributeName());
		attr4Lang.setDescription(pdtAttributeDefDO.getAttributeDescription());
		attr4Lang.setId(pdtAttributeDefDO.getId());
		attr4Lang.setListShowable(TrueFalseUtil.parseBool(pdtAttributeDefDO.getListshowable()));
		return attr4Lang;
	}
	
	public static Attr4Enum toAttr4Enum(PDTAttributeDefDO pdtAttributeDefDO) {
		Attr4Enum attr4Enum = new Attr4Enum((AttributeScope)EnumUtils.getByCode(pdtAttributeDefDO.getAttributeScope(), AttributeScope.class));
		attr4Enum.setAttributeName(pdtAttributeDefDO.getAttributeName());
		attr4Enum.setDescription(pdtAttributeDefDO.getAttributeDescription());
		attr4Enum.setId(pdtAttributeDefDO.getId());
		attr4Enum.setSearchable(TrueFalseUtil.parseBool(pdtAttributeDefDO.getSearchable()));
		attr4Enum.setListShowable(TrueFalseUtil.parseBool(pdtAttributeDefDO.getListshowable()));
		return attr4Enum;
	}
	
	/**
	 * 转换为PDTAttributeDefDO
	 * @param attr4Lang
	 * @return
	 */
	public static PDTAttributeDefDO toPDTAttributeDefDO(Attr4Lang attr4Lang) {
		PDTAttributeDefDO model = new PDTAttributeDefDO();
		model.setId(attr4Lang.getId());
		model.setAttributeName(attr4Lang.getAttributeName());
		model.setAttributeDescription(attr4Lang.getDescription());
		model.setAttributeScope(attr4Lang.getAttributeScope().getCode());
		model.setAttributeType(attr4Lang.getAttributeType().getCode());
		model.setListshowable(TrueFalseUtil.parseInt(attr4Lang.isListShowable()));
		return model;
	}
	
	/**
	 * 转换为PDTAttributeDefDO
	 * @param attr4Enum
	 * @return
	 */
	public static PDTAttributeDefDO toPDTAttributeDefDO(Attr4Enum attr4Enum) {
		PDTAttributeDefDO model = new PDTAttributeDefDO();
		model.setId(attr4Enum.getId());
		model.setAttributeName(attr4Enum.getAttributeName());
		model.setAttributeDescription(attr4Enum.getDescription());
		model.setAttributeType(attr4Enum.getAttributeType().getCode());
		model.setSearchable(TrueFalseUtil.parseInt(attr4Enum.isSearchable()));
		model.setAttributeScope(attr4Enum.getAttributeScope().getCode());
		model.setListshowable(TrueFalseUtil.parseInt(attr4Enum.isListShowable()));
		return model;
	}
	
	/**
	 * AttributeDefId未被转换
	 * 转换为PDTAttributeEnumValueDO
	 * @param enumValue
	 * @return
	 */
	public static PDTAttributeEnumValueDO toPDTAttributeEnumValueDO(EnumValue enumValue) {
		PDTAttributeEnumValueDO model = new PDTAttributeEnumValueDO();
		model.setId(enumValue.getId());
		model.setObjectvalue(enumValue.getObjectValue());
		model.setSort(enumValue.getSort());
		return model;
	}
	
	/**
	 * 转换为EnumValue
	 * @param pdtAttributeEnumValueDO
	 * @return
	 */
	public static EnumValue toEnumValue(PDTAttributeEnumValueDO pdtAttributeEnumValueDO) {
		EnumValue enumValue = new EnumValue();
		enumValue.setId(pdtAttributeEnumValueDO.getId());
		enumValue.setObjectValue(pdtAttributeEnumValueDO.getObjectvalue());
		enumValue.setSort(pdtAttributeEnumValueDO.getSort());
		return enumValue;
	}
}
