/**
 * 
 */
package com.vanstone.jupin.productdefine.services.impl;

import com.vanstone.jupin.common.util.BoolUtil;
import com.vanstone.jupin.productdefine.attr.sku.Color;
import com.vanstone.jupin.productdefine.attr.sku.Size;
import com.vanstone.jupin.productdefine.attr.sku.SizeTemplate;
import com.vanstone.jupin.productdefine.persistence.object.PDTSkuColorTableDO;
import com.vanstone.jupin.productdefine.persistence.object.PDTSkuSizeTableDO;
import com.vanstone.jupin.productdefine.persistence.object.PDTSkuSizeTemplateDO;

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
	
	public static PDTSkuSizeTableDO toPdtSkuSizeTableDO(Size size) {
		return null;
	}
	
	public static Size toSize(PDTSkuSizeTableDO pdtSkuSizeTableDO) {
		return null;
	}
	
}
