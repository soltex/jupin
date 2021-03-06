/**
 * 
 */
package com.vanstone.jupin.ecs.product.framework.services.impl;

import com.vanstone.business.lang.EnumUtils;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.jupin.common.util.BoolUtil;
import com.vanstone.jupin.ecs.product.define.Brand;
import com.vanstone.jupin.ecs.product.define.CategoryState;
import com.vanstone.jupin.ecs.product.define.ProductCategoryDetail;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Color;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Size;
import com.vanstone.jupin.ecs.product.define.attribute.sku.SizeTemplate;
import com.vanstone.jupin.ecs.product.framework.persistence.object.PDTBrandDO;
import com.vanstone.jupin.ecs.product.framework.persistence.object.PDTCategoryDO;
import com.vanstone.jupin.ecs.product.framework.persistence.object.PDTSkuColorTableDO;
import com.vanstone.jupin.ecs.product.framework.persistence.object.PDTSkuSizeTableDO;
import com.vanstone.jupin.ecs.product.framework.persistence.object.PDTSkuSizeTemplateDO;
import com.vanstone.jupin.ecs.product.framework.persistence.object.QueryBrandStatResultMap;

/**
 * @author shipeng
 */
public class BeanUtil {
	
	public static Color toColor(PDTSkuColorTableDO pDTSkuColorTableDO) {
		Color color = new Color();
		color.setId(pDTSkuColorTableDO.getId());
		color.setColorName(pDTSkuColorTableDO.getColorName());
		color.setColorRGB(pDTSkuColorTableDO.getColorRgb());
		color.setColorCSS(pDTSkuColorTableDO.getColorCss());
		color.setSort(pDTSkuColorTableDO.getSort());
		return color;
	}
	
	public static PDTSkuColorTableDO toPDTSkuColorTableDO(Color color) {
		PDTSkuColorTableDO pDTSkuColorTableDO = new PDTSkuColorTableDO();
		pDTSkuColorTableDO.setId(color.getId());
		pDTSkuColorTableDO.setColorName(color.getColorName());
		pDTSkuColorTableDO.setColorRgb(color.getColorRGB());
		pDTSkuColorTableDO.setColorCss(color.getColorCSS());
		pDTSkuColorTableDO.setSort(color.getSort());
		return pDTSkuColorTableDO;
	}
	
	public static SizeTemplate toSizeTemplate(PDTSkuSizeTemplateDO pdtSkuSizeTemplateDO) {
		SizeTemplate sizeTemplate = new SizeTemplate();
		sizeTemplate.setId(pdtSkuSizeTemplateDO.getId());
		sizeTemplate.setTemplateName(pdtSkuSizeTemplateDO.getTemplateName());
		sizeTemplate.setTemplateContent(pdtSkuSizeTemplateDO.getTemplateContent());
		sizeTemplate.setSystemable(BoolUtil.parseInt(pdtSkuSizeTemplateDO.getSystemable()));
		sizeTemplate.setWaistlineable(BoolUtil.parseInt(pdtSkuSizeTemplateDO.getWaistlineable()));
		sizeTemplate.setWeightable(BoolUtil.parseInt(pdtSkuSizeTemplateDO.getWeightable()));
		sizeTemplate.setHipable(BoolUtil.parseInt(pdtSkuSizeTemplateDO.getHipable()));
		sizeTemplate.setChestable(BoolUtil.parseInt(pdtSkuSizeTemplateDO.getChestable()));
		sizeTemplate.setHeightable(BoolUtil.parseInt(pdtSkuSizeTemplateDO.getHeightable()));
		sizeTemplate.setShoulderable(BoolUtil.parseInt(pdtSkuSizeTemplateDO.getShoulderable()));
		return sizeTemplate;
	}
	
	public static SizeTemplate toSizeTemplate(PDTSkuSizeTemplateDO pdtSkuSizeTemplateDO, int sizeCount) {
		SizeTemplate sizeTemplate = new SizeTemplate(sizeCount);
		sizeTemplate.setId(pdtSkuSizeTemplateDO.getId());
		sizeTemplate.setTemplateName(pdtSkuSizeTemplateDO.getTemplateName());
		sizeTemplate.setTemplateContent(pdtSkuSizeTemplateDO.getTemplateContent());
		sizeTemplate.setSystemable(BoolUtil.parseInt(pdtSkuSizeTemplateDO.getSystemable()));
		sizeTemplate.setWaistlineable(BoolUtil.parseInt(pdtSkuSizeTemplateDO.getWaistlineable()));
		sizeTemplate.setWeightable(BoolUtil.parseInt(pdtSkuSizeTemplateDO.getWeightable()));
		sizeTemplate.setHipable(BoolUtil.parseInt(pdtSkuSizeTemplateDO.getHipable()));
		sizeTemplate.setChestable(BoolUtil.parseInt(pdtSkuSizeTemplateDO.getChestable()));
		sizeTemplate.setHeightable(BoolUtil.parseInt(pdtSkuSizeTemplateDO.getHeightable()));
		sizeTemplate.setShoulderable(BoolUtil.parseInt(pdtSkuSizeTemplateDO.getShoulderable()));
		return sizeTemplate;
	}
	
	public static PDTSkuSizeTemplateDO toPdtSkuSizeTemplateDO(SizeTemplate sizeTemplate) {
		PDTSkuSizeTemplateDO pdtSkuSizeTemplateDO = new PDTSkuSizeTemplateDO();
		pdtSkuSizeTemplateDO.setId(sizeTemplate.getId());
		pdtSkuSizeTemplateDO.setTemplateName(sizeTemplate.getTemplateName());
		pdtSkuSizeTemplateDO.setTemplateContent(sizeTemplate.getTemplateContent());
		pdtSkuSizeTemplateDO.setSystemable(BoolUtil.parseBoolean(sizeTemplate.isSystemable()));
		pdtSkuSizeTemplateDO.setWaistlineable(BoolUtil.parseBoolean(sizeTemplate.isWaistlineable()));
		pdtSkuSizeTemplateDO.setWeightable(BoolUtil.parseBoolean(sizeTemplate.isWeightable()));
		pdtSkuSizeTemplateDO.setHipable(BoolUtil.parseBoolean(sizeTemplate.isHipable()));
		pdtSkuSizeTemplateDO.setChestable(BoolUtil.parseBoolean(sizeTemplate.isChestable()));
		pdtSkuSizeTemplateDO.setHeightable(BoolUtil.parseBoolean(sizeTemplate.isHeightable()));
		pdtSkuSizeTemplateDO.setShoulderable(BoolUtil.parseBoolean(sizeTemplate.isShoulderable()));
		return pdtSkuSizeTemplateDO;
	}
	
	public static PDTSkuSizeTableDO toPDTSkuSizeTableDO(Size size) {
		PDTSkuSizeTableDO pDTSkuSizeTableDO = new PDTSkuSizeTableDO();
		
		pDTSkuSizeTableDO.setSizeTemplateId(size.getSizeTemplate() != null ? size.getSizeTemplate().getId() : null);
		pDTSkuSizeTableDO.setId(size.getId());
		pDTSkuSizeTableDO.setSizeName(size.getSizeName());
		
		pDTSkuSizeTableDO.setWaistlineable(BoolUtil.parseBoolean(size.isWaistlineable()));
		pDTSkuSizeTableDO.setWaistlineEnd(size.getWaistlineEnd());
		pDTSkuSizeTableDO.setWaistlineStart(size.getWaistlineStart());
		
		pDTSkuSizeTableDO.setWeightable(BoolUtil.parseBoolean(size.isWeightable()));
		pDTSkuSizeTableDO.setWeightStart(size.getWeightStart());
		pDTSkuSizeTableDO.setWeightEnd(size.getWeightEnd());
		
		pDTSkuSizeTableDO.setHipable(BoolUtil.parseBoolean(size.isHipable()));
		pDTSkuSizeTableDO.setHipStart(size.getHipStart());
		pDTSkuSizeTableDO.setHipEnd(size.getHipEnd());
		
		pDTSkuSizeTableDO.setChestable(BoolUtil.parseBoolean(size.isChestable()));
		pDTSkuSizeTableDO.setChestStart(size.getChestStart());
		pDTSkuSizeTableDO.setChestEnd(size.getChestEnd());
		
		pDTSkuSizeTableDO.setHeightable(BoolUtil.parseBoolean(size.isHeightable()));
		pDTSkuSizeTableDO.setHeightStart(size.getHeightStart());
		pDTSkuSizeTableDO.setHeightEnd(size.getHeightEnd());
		
		pDTSkuSizeTableDO.setShoulderable(BoolUtil.parseBoolean(size.isShoulderable()));
		pDTSkuSizeTableDO.setShoulderStart(size.getShoulderStart());
		pDTSkuSizeTableDO.setShoulderEnd(size.getShoulderEnd());
		
		return pDTSkuSizeTableDO;
	}
	
	public static Size toSize(PDTSkuSizeTableDO pDTSkuSizeTableDO,SizeTemplate sizeTemplate) {
		Size size = new Size();
		size.setId(pDTSkuSizeTableDO.getId());
		size.setSizeName(pDTSkuSizeTableDO.getSizeName());
		size.setSizeTemplate(sizeTemplate);
		
		size.setWaistlineable(BoolUtil.parseInt(pDTSkuSizeTableDO.getWaistlineable()));
		size.setWaistlineEnd(pDTSkuSizeTableDO.getWaistlineEnd());
		size.setWaistlineStart(pDTSkuSizeTableDO.getWaistlineStart());
		
		size.setWeightable(BoolUtil.parseInt(pDTSkuSizeTableDO.getWeightable()));
		size.setWeightStart(pDTSkuSizeTableDO.getWeightStart());
		size.setWeightEnd(pDTSkuSizeTableDO.getWeightEnd());
		size.setHipable(BoolUtil.parseInt(pDTSkuSizeTableDO.getHipable()));
		size.setHipStart(pDTSkuSizeTableDO.getHipStart());
		size.setHipEnd(pDTSkuSizeTableDO.getHipEnd());
		size.setChestable(BoolUtil.parseInt(pDTSkuSizeTableDO.getChestable()));
		size.setChestStart(pDTSkuSizeTableDO.getChestStart());
		size.setChestEnd(pDTSkuSizeTableDO.getChestEnd());
		size.setHeightable(BoolUtil.parseInt(pDTSkuSizeTableDO.getHeightable()));
		size.setHeightStart(pDTSkuSizeTableDO.getHeightStart());
		size.setHeightEnd(pDTSkuSizeTableDO.getHeightEnd());
		size.setShoulderable(BoolUtil.parseInt(pDTSkuSizeTableDO.getShoulderable()));
		size.setShoulderStart(pDTSkuSizeTableDO.getShoulderStart());
		size.setShoulderEnd(pDTSkuSizeTableDO.getShoulderEnd());
		
		return size;
	}
	
	/**
	 * The following are not being converted. 
	 * java.lang.Integer productCategoryCount;
	 * java.lang.Integer productCount;
	 */
	public static Brand toBrand(PDTBrandDO pdtBrandDO) {
		Brand brand = new Brand();
		brand.setId(pdtBrandDO.getId());
		brand.setBrandName(pdtBrandDO.getBrandName());
		brand.setBrandNameEN(pdtBrandDO.getBrandNameEn());
		brand.setBrandNamefirstLetter(pdtBrandDO.getBrandNameFirstLetter() != null ? pdtBrandDO.getBrandNameFirstLetter().charAt(0) : null);
		brand.setBrandNamePinyin(pdtBrandDO.getBrandNamePinyin());
		brand.setContent(pdtBrandDO.getContent());
		if (pdtBrandDO.getLogoFileId() != null && !pdtBrandDO.getLogoFileId().equals("")) {
			ImageBean imageBean  = new ImageBean(pdtBrandDO.getLogoFileId(), pdtBrandDO.getLogoFileExt(), 
					pdtBrandDO.getLogoWidth(), pdtBrandDO.getLogoHeight());
			brand.setLogoImage(imageBean);
		}
		brand.setSystemable(BoolUtil.parseInt(pdtBrandDO.getSystemable()));
		return brand;
	}
	
	public static Brand toBrand(QueryBrandStatResultMap resultMap) {
		Brand brand = new Brand(resultMap.getCategoryIDCount(), 0);
		brand.setId(resultMap.getId());
		brand.setBrandName(resultMap.getBrandName());
		brand.setBrandNameEN(resultMap.getBrandNameEn());
		brand.setBrandNamefirstLetter(resultMap.getBrandNameFirstLetter() != null ? resultMap.getBrandNameFirstLetter().charAt(0) : null);
		brand.setBrandNamePinyin(resultMap.getBrandNamePinyin());
		brand.setContent(resultMap.getContent());
		if (resultMap.getLogoFileId() != null && !resultMap.getLogoFileId().equals("")) {
			ImageBean imageBean  = new ImageBean(resultMap.getLogoFileId(), resultMap.getLogoFileExt(), 
					resultMap.getLogoWidth(), resultMap.getLogoHeight());
			brand.setLogoImage(imageBean);
		}
		brand.setSystemable(BoolUtil.parseInt(resultMap.getSystemable()));
		return brand;
	}
	

	public static PDTBrandDO toPDTBrandDO(Brand brand) {
		PDTBrandDO pdtBrandDO = new PDTBrandDO();
		pdtBrandDO.setId(brand.getId());
		pdtBrandDO.setBrandName(brand.getBrandName());
		pdtBrandDO.setBrandNameEn(brand.getBrandNameEN());
		pdtBrandDO.setBrandNameFirstLetter(brand.getBrandNamefirstLetter() != null ? brand.getBrandNamefirstLetter().toString() : null);
		pdtBrandDO.setBrandNamePinyin(brand.getBrandNamePinyin());
		if (brand.getLogoImage() != null) {
			pdtBrandDO.setLogoFileId(brand.getLogoImage().getWeedFile().getFileid());
			pdtBrandDO.setLogoFileExt(brand.getLogoImage().getWeedFile().getExtName());
			pdtBrandDO.setLogoWidth(brand.getLogoImage().getWidth());
			pdtBrandDO.setLogoHeight(brand.getLogoImage().getHeight());
		}
		pdtBrandDO.setSystemable(BoolUtil.parseBoolean(brand.isSystemable()));
		pdtBrandDO.setContent(brand.getContent());
		return pdtBrandDO;
	}
	
	public static PDTCategoryDO toPdtCategoryDO(ProductCategoryDetail productCategory) {
		PDTCategoryDO model = new PDTCategoryDO();
		model.setId(productCategory.getId());
		model.setCategoryName(productCategory.getCategoryName());
		model.setDescription(productCategory.getDescription());
		model.setCategoryBindPage(productCategory.getCategoryBindPage());
		model.setFormTemplate(productCategory.getFormTemplate());
		if (productCategory.getCoverImage() != null) {
			model.setCoverFileId(productCategory.getCoverImage().getWeedFile().getFileid());
			model.setCoverFileExt(productCategory.getCoverImage().getWeedFile().getExtName());
			model.setCoverFileWidth(productCategory.getCoverImage().getWidth());
			model.setCoverFileHeight(productCategory.getCoverImage().getHeight());
		}
		if (productCategory.getParentProductCategory() != null) {
			model.setParentId(productCategory.getParentProductCategory().getId());
		}
		model.setSort(productCategory.getSort());
		model.setLeafable(BoolUtil.parseBoolean(productCategory.isLeafable()));
		model.setCategoryState(productCategory.getCategoryState().getCode());
		model.setExistProduct(BoolUtil.parseBoolean(productCategory.isExistProduct()));
		model.setSkuColorable(BoolUtil.parseBoolean(productCategory.isSkuColor()));
		model.setSkuSizeable(BoolUtil.parseBoolean(productCategory.isExistSkuTemplate()));
		model.setSizeTemplateId(productCategory.getSizeTemplate() != null ? productCategory.getSizeTemplate().getId() : null);
		return model;
	}
	
	public static ProductCategoryDetail toProductCategory(PDTCategoryDO pdtCategoryDO, ProductCategoryDetail parentProductCategory, SizeTemplate sizeTemplate) {
		ProductCategoryDetail category = new ProductCategoryDetail();
		category.setId(pdtCategoryDO.getId());
		category.setCategoryName(pdtCategoryDO.getCategoryName());
		category.setDescription(pdtCategoryDO.getDescription());
		category.setCategoryBindPage(pdtCategoryDO.getCategoryBindPage());
		category.setFormTemplate(pdtCategoryDO.getFormTemplate());
		if (pdtCategoryDO.getCoverFileId() != null && !pdtCategoryDO.getCoverFileId().equals("")) {
			ImageBean imageBean = new ImageBean(pdtCategoryDO.getCoverFileId(), pdtCategoryDO.getCoverFileExt(), pdtCategoryDO.getCoverFileWidth(), pdtCategoryDO.getCoverFileHeight());
			category.setCoverImage(imageBean);
		}
		category.setParentProductCategory(parentProductCategory);
		category.setSort(pdtCategoryDO.getSort());
		category.setLeafable(BoolUtil.parseInt(pdtCategoryDO.getLeafable()));
		category.setCategoryState((CategoryState)EnumUtils.getByCode(pdtCategoryDO.getCategoryState(), CategoryState.class));
		category.setExistProduct(BoolUtil.parseInt(pdtCategoryDO.getExistProduct()));
		category.setSkuColor(BoolUtil.parseInt(pdtCategoryDO.getSkuColorable()));
		category.setSizeTemplate(sizeTemplate);
		return category;
	}
	
}
