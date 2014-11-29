/**
 * 
 */
package com.vanstone.jupin.ebs.pm.productdefine.services;

/**
 * 产品定义公共业务API
 * @author shipeng
 */
public interface DefineCommonService {
	
	/**
	 * 清理缓冲
	 */
	void clearCategoryDefineCache();
	
	/**
	 * 清理颜色表缓冲定义
	 */
	void clearColorTableCache();
	
	/**
	 * 清理尺码表缓冲
	 */
	void clearSizeTableCache();
	
	/**
	 * 清理品牌缓冲
	 */
	void clearBrandCache();
	
	/**
	 * 清理全部ProductDefine缓冲
	 */
	void clearProductDefineCache();
	
	/**
	 * 验证是否允许对Color进行更新和删除操作 
	 * @return
	 */
	boolean validateAllowUDOperateColor();
	
	/**
	 * 验证是否允许对SizeTemplate进行更新和删除操作
	 * @param sizeTemplateID
	 * @return
	 */
	boolean validateAllowUDOperateSizeTemplate(int sizeTemplateID);
	
	/**
	 * 验证是否允许对Brand进行更新删除操作
	 * @param brandID
	 * @return
	 */
	boolean validateAllowUDOperateBrand(int brandID);
	
}
