/**
 * 
 */
package com.vanstone.jupin.adminservice.sdk.pdm.define.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.common.MyAssert;
import com.vanstone.framework.business.services.DefaultBusinessService;
import com.vanstone.jupin.business.sdk.adminservice.pdm.define.DefineManager;
import com.vanstone.jupin.business.sdk.adminservice.pdm.define.SizeBean;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Size;
import com.vanstone.jupin.ecs.product.define.attribute.sku.SizeTemplate;
import com.vanstone.jupin.ecs.product.define.services.ExistProductsNotAllowWriteException;
import com.vanstone.jupin.ecs.product.define.services.SizeService;

/**
 * @author shipeng
 */
@Service("sizeManager")
public class DefineManagerImpl extends DefaultBusinessService implements DefineManager {
	
	/***/
	private static final long serialVersionUID = -4899067001770580354L;
	
	@Autowired
	private SizeService sizeService;
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.business.sdk.adminservice.pdm.define.SizeManager#addSizeTemplate(java.lang.String, java.lang.String, boolean, boolean, boolean, boolean, boolean, boolean, boolean, java.util.Collection)
	 */
	@Override
	public SizeTemplate addSizeTemplate(String templateName, String content, boolean systemable, boolean waistlineable,
			boolean weightable, boolean hipable, boolean chestable, boolean heightable, boolean shoulderable,
			Collection<SizeBean> sizeBeans) throws ObjectDuplicateException {
		MyAssert.notNull(sizeBeans);
		MyAssert.notEmpty(sizeBeans);
		Collection<Size> sizes = new ArrayList<Size>();
		for (SizeBean bean : sizeBeans) {
			Size size = new Size();
			size.setSizeName(bean.getSizeName());
			
			size.setWaistlineStart(bean.getWaistlineStart());
			size.setWaistlineEnd(bean.getWaistlineEnd());
			
			size.setWeightStart(bean.getWeightStart());
			size.setWeightEnd(bean.getWeightEnd());
			
			size.setHipStart(bean.getHipStart());
			size.setHipEnd(bean.getHipEnd());
			
			size.setChestStart(bean.getChestStart());
			size.setChestEnd(bean.getChestEnd());
			
			size.setHeightStart(bean.getHeightStart());
			size.setHeightEnd(bean.getHeightEnd());
			
			size.setShoulderStart(bean.getShoulderStart());
			size.setShoulderEnd(bean.getShoulderEnd());
			
			sizes.add(size);
		}
		return this.sizeService.addSizeTemplate(templateName, content, systemable, waistlineable, weightable, hipable, chestable, heightable, shoulderable, sizes);
	}

	@Override
	public Size addSize(SizeBean sizeBean) throws ObjectDuplicateException, ExistProductsNotAllowWriteException {
		MyAssert.notNull(sizeBean);
		SizeTemplate sizeTemplate = this.sizeService.getSizeTemplate(sizeBean.getSizeTemplateId());
		if (sizeTemplate == null) {
			throw new IllegalArgumentException();
		}
		Size size = new Size(sizeTemplate);
		size.setSizeName(sizeBean.getSizeName());
		size.setWaistlineStart(sizeBean.getWaistlineStart());
		size.setWaistlineEnd(sizeBean.getWaistlineEnd());
		size.setWeightStart(sizeBean.getWeightStart());
		size.setWeightEnd(sizeBean.getWeightEnd());
		size.setHipStart(sizeBean.getHipStart());
		size.setHipEnd(sizeBean.getHipEnd());
		size.setChestStart(sizeBean.getChestStart());
		size.setChestEnd(sizeBean.getChestEnd());
		size.setHeightStart(sizeBean.getHeightStart());
		size.setHeightEnd(sizeBean.getHeightEnd());
		size.setShoulderStart(sizeBean.getShoulderStart());
		size.setShoulderEnd(sizeBean.getShoulderEnd());
		
		return this.sizeService.addSize(size);
	}

	@Override
	public Size updateSize(SizeBean sizeBean) throws ObjectDuplicateException, ExistProductsNotAllowWriteException {
		MyAssert.notNull(sizeBean);
		SizeTemplate sizeTemplate = this.sizeService.getSizeTemplate(sizeBean.getSizeTemplateId());
		if (sizeTemplate == null) {
			throw new IllegalArgumentException();
		}
		Size size = new Size(sizeTemplate);
		size.setSizeName(sizeBean.getSizeName());
		size.setWaistlineStart(sizeBean.getWaistlineStart());
		size.setWaistlineEnd(sizeBean.getWaistlineEnd());
		size.setWeightStart(sizeBean.getWeightStart());
		size.setWeightEnd(sizeBean.getWeightEnd());
		size.setHipStart(sizeBean.getHipStart());
		size.setHipEnd(sizeBean.getHipEnd());
		size.setChestStart(sizeBean.getChestStart());
		size.setChestEnd(sizeBean.getChestEnd());
		size.setHeightStart(sizeBean.getHeightStart());
		size.setHeightEnd(sizeBean.getHeightEnd());
		size.setShoulderStart(sizeBean.getShoulderStart());
		size.setShoulderEnd(sizeBean.getShoulderEnd());
		size.setId(sizeBean.getId());
		return this.sizeService.updateSize(size);
	}
	
}
