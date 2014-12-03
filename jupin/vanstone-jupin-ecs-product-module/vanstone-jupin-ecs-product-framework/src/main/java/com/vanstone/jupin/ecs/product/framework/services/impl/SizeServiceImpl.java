/**
 * 
 */
package com.vanstone.jupin.ecs.product.framework.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.NotNull;

import org.apache.curator.framework.CuratorFramework;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.validation.annotation.Validated;

import com.vanstone.business.MyAssert4Business;
import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.business.ObjectHasSubObjectException;
import com.vanstone.framework.business.services.DefaultBusinessService;
import com.vanstone.framework.business.services.ServiceUtil;
import com.vanstone.jupin.common.Constants;
import com.vanstone.jupin.common.util.BoolUtil;
import com.vanstone.jupin.common.util.InterProcessMutexCallback;
import com.vanstone.jupin.common.util.ZKUtil;
import com.vanstone.jupin.ecs.product.PDCache;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Size;
import com.vanstone.jupin.ecs.product.define.attribute.sku.SizeTemplate;
import com.vanstone.jupin.ecs.product.define.attribute.sku.SizeTemplateWrapBean;
import com.vanstone.jupin.ecs.product.define.services.DefineCommonService;
import com.vanstone.jupin.ecs.product.define.services.ExistProductsNotAllowWriteException;
import com.vanstone.jupin.ecs.product.define.services.SizeService;
import com.vanstone.jupin.ecs.product.framework.persistence.PDTSkuSizeTableDOMapper;
import com.vanstone.jupin.ecs.product.framework.persistence.PDTSkuSizeTemplateDOMapper;
import com.vanstone.jupin.ecs.product.framework.persistence.object.PDTSkuSizeTableDO;
import com.vanstone.jupin.ecs.product.framework.persistence.object.PDTSkuSizeTemplateDO;
import com.vanstone.jupin.ecs.product.framework.persistence.object.QuerySizeTemplateDOWithSizeTableResultMap;
import com.vanstone.jupin.framework.cache.JupinRedisRef;
import com.vanstone.redis.RedisTemplate;

/**
 * @author shipeng
 */
@Service("sizeService")
@Validated
public class SizeServiceImpl extends DefaultBusinessService implements SizeService {
	
	/***/
	private static final long serialVersionUID = 5077934760251196625L;
	
	@Autowired
	private PDTSkuSizeTableDOMapper pdtSkuSizeTableDOMapper;
	@Autowired
	private PDTSkuSizeTemplateDOMapper pdtSkuSizeTemplateDOMapper;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private DefineCommonService defineCommonService;
	
	@Override
	public SizeTemplate addSizeTemplate(final String templateName, final String content, final boolean systemable,
			final boolean waistlineable, final boolean weightable, final boolean hipable, final boolean chestable, final boolean heightable, final boolean shoulderable,
			final Collection<Size> sizes) throws ObjectDuplicateException {
		//判断模板名称是否重复
		PDTSkuSizeTemplateDO model = this.pdtSkuSizeTemplateDOMapper.selectByTemplateName(templateName);
		if (model != null) {
			throw new ObjectDuplicateException();
		}
		//尺码值验证
		Set<String> sizeNames = new LinkedHashSet<String>();
		for (Size size : sizes) {
			if (sizeNames.contains(size.getSizeName())) {
				throw new ObjectDuplicateException();
			}
		}
		if (!_validateSizeCollectionData(waistlineable, weightable, hipable, chestable, heightable, shoulderable, sizes)) {
			throw new IllegalArgumentException("waistlineable or weightable or hipable or chestable or heightable or shoulderable not illegal, please check");
		}
		Integer sizeTemplateId = this.execute(new TransactionCallback<Integer>() {
			@Override
			public Integer doInTransaction(TransactionStatus arg0) {
				PDTSkuSizeTemplateDO templateDO = new PDTSkuSizeTemplateDO();
				templateDO.setTemplateName(templateName);
				templateDO.setTemplateContent(content);
				templateDO.setSystemable(BoolUtil.parseBoolean(systemable));
				templateDO.setWaistlineable(BoolUtil.parseBoolean(waistlineable)); 
				templateDO.setWeightable(BoolUtil.parseBoolean(weightable)); 
				templateDO.setHipable(BoolUtil.parseBoolean(hipable)); 
				templateDO.setChestable(BoolUtil.parseBoolean(chestable)); 
				templateDO.setHeightable(BoolUtil.parseBoolean(heightable)); 
				templateDO.setShoulderable(BoolUtil.parseBoolean(shoulderable));
				//写入模板数据
				pdtSkuSizeTemplateDOMapper.insert(templateDO);
				//写入尺码数据
				for (Size size : sizes) {
					PDTSkuSizeTableDO sizeTableDO = new PDTSkuSizeTableDO();
					
					sizeTableDO.setSizeName(size.getSizeName());
					
					sizeTableDO.setSizeTemplateId(templateDO.getId());
					sizeTableDO.setWaistlineable(BoolUtil.parseBoolean(waistlineable)); 
					sizeTableDO.setWeightable(BoolUtil.parseBoolean(weightable)); 
					sizeTableDO.setHipable(BoolUtil.parseBoolean(hipable)); 
					sizeTableDO.setChestable(BoolUtil.parseBoolean(chestable));
					sizeTableDO.setHeightable(BoolUtil.parseBoolean(heightable)) ;
					sizeTableDO.setShoulderable(BoolUtil.parseBoolean(shoulderable));
					if (waistlineable) {
						sizeTableDO.setWaistlineEnd(size.getWaistlineEnd());
						sizeTableDO.setWaistlineStart(size.getWaistlineStart());
					}
					if (weightable) {
						sizeTableDO.setWeightEnd(size.getWeightEnd());
						sizeTableDO.setWeightStart(size.getWeightStart());
					}
					if (hipable) {
						sizeTableDO.setHipEnd(size.getHipEnd());
						sizeTableDO.setHipStart(size.getHipStart());
					}
					if (chestable) {
						sizeTableDO.setChestEnd(size.getChestEnd());
						sizeTableDO.setChestStart(size.getChestStart());
					}
					if (heightable) {
						sizeTableDO.setHeightEnd(size.getHeightEnd());
						sizeTableDO.setHeightStart(size.getHeightStart());
					}
					if (shoulderable) {
						sizeTableDO.setShoulderEnd(size.getShoulderEnd());
						sizeTableDO.setShoulderStart(size.getShoulderStart());
					}
					//写入尺码数据
					pdtSkuSizeTableDOMapper.insert(sizeTableDO);
				}
				return templateDO.getId();
			}
		});
		defineCommonService.clearProductDefineCache();
		return this.getSizeTemplate(sizeTemplateId);
	}
	
	@Override
	public void updateSizeTemplateInfo(@NotNull final SizeTemplate sizeTemplate,
			final @NotEmpty Collection<Size> sizes)
			throws ObjectDuplicateException, ExistProductsNotAllowWriteException {
		MyAssert4Business.objectInitialized(sizeTemplate);
		if (sizeTemplate == null) {
			throw new IllegalArgumentException();
		}
		//尺码值验证
		Set<String> sizeNames = new LinkedHashSet<String>();
		for (Size size : sizes) {
			if (sizeNames.contains(size.getSizeName())) {
				throw new ObjectDuplicateException();
			}
		}
		//尺码DB验证
		for (Size size : sizes) {
			PDTSkuSizeTableDO model = null;
			if (size.initialable()) {
				//需要更新的Size
				model = pdtSkuSizeTableDOMapper.selectBySizeTemplateId_SizeName_NotSelf(sizeTemplate.getId(), size.getSizeName(), size.getId());
				if (model != null) {
					throw new ObjectDuplicateException();
				}
			}else{
				//最新写入的Size
				model = pdtSkuSizeTableDOMapper.selectBySizeTemplateId_SizeName(sizeTemplate.getId(), size.getSizeName());
				if (model != null) {
					throw new ObjectDuplicateException();
				}
			}
		}
		if (!_validateSizeCollectionData(sizeTemplate.isWaistlineable(), sizeTemplate.isWeightable(), sizeTemplate.isHipable(), sizeTemplate.isChestable(), sizeTemplate.isHeightable(), sizeTemplate.isShoulderable(), sizes)) {
			throw new IllegalArgumentException("waistlineable or weightable or hipable or chestable or heightable or shoulderable not illegal, please check");
		}
		this.execute(new TransactionCallbackWithoutResult() {
			
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTSkuSizeTemplateDO templateDO = BeanUtil.toPdtSkuSizeTemplateDO(sizeTemplate);
				templateDO.setId(sizeTemplate.getId());
				templateDO.setSystemable(BoolUtil.parseBoolean(sizeTemplate.isSystemable()));
				templateDO.setWaistlineable(BoolUtil.parseBoolean(sizeTemplate.isWaistlineable())); 
				templateDO.setWeightable(BoolUtil.parseBoolean(sizeTemplate.isWeightable())); 
				templateDO.setHipable(BoolUtil.parseBoolean(sizeTemplate.isHipable())); 
				templateDO.setChestable(BoolUtil.parseBoolean(sizeTemplate.isChestable())); 
				templateDO.setHeightable(BoolUtil.parseBoolean(sizeTemplate.isHeightable())); 
				templateDO.setShoulderable(BoolUtil.parseBoolean(sizeTemplate.isShoulderable()));
				//修改模板数据
				pdtSkuSizeTemplateDOMapper.updateByPrimaryKey(templateDO);
				
				//写入尺码数据
				for (Size size : sizes) {
					if (size.initialable()) {
						//更新数据
						PDTSkuSizeTableDO sizeTableDO = BeanUtil.toPDTSkuSizeTableDO(size);
						
						sizeTableDO.setSizeName(size.getSizeName());
						
						sizeTableDO.setSizeTemplateId(templateDO.getId());
						sizeTableDO.setWaistlineable(BoolUtil.parseBoolean(sizeTemplate.isWaistlineable())); 
						sizeTableDO.setWeightable(BoolUtil.parseBoolean(sizeTemplate.isWeightable())); 
						sizeTableDO.setHipable(BoolUtil.parseBoolean(sizeTemplate.isHipable())); 
						sizeTableDO.setChestable(BoolUtil.parseBoolean(sizeTemplate.isChestable()));
						sizeTableDO.setHeightable(BoolUtil.parseBoolean(sizeTemplate.isHeightable())) ;
						sizeTableDO.setShoulderable(BoolUtil.parseBoolean(sizeTemplate.isShoulderable()));
						
						if (sizeTemplate.isWaistlineable()) {
							sizeTableDO.setWaistlineEnd(size.getWaistlineEnd());
							sizeTableDO.setWaistlineStart(size.getWaistlineStart());
						}else {
							sizeTableDO.setWaistlineEnd(null);
							sizeTableDO.setWaistlineStart(null);
						}
						
						if (sizeTemplate.isWeightable()) {
							sizeTableDO.setWeightEnd(size.getWeightEnd());
							sizeTableDO.setWeightStart(size.getWeightStart());
						}else{
							sizeTableDO.setWeightEnd(null);
							sizeTableDO.setWeightStart(null);
						}
						
						if (sizeTemplate.isHipable()) {
							sizeTableDO.setHipEnd(size.getHipEnd());
							sizeTableDO.setHipStart(size.getHipStart());
						}else{
							sizeTableDO.setHipEnd(null);
							sizeTableDO.setHipStart(null);
						}
						
						if (sizeTemplate.isChestable()) {
							sizeTableDO.setChestEnd(size.getChestEnd());
							sizeTableDO.setChestStart(size.getChestStart());
						}else{
							sizeTableDO.setChestEnd(null);
							sizeTableDO.setChestStart(null);
						}
						
						if (sizeTemplate.isHeightable()) {
							sizeTableDO.setHeightEnd(size.getHeightEnd());
							sizeTableDO.setHeightStart(size.getHeightStart());
						}else {
							sizeTableDO.setHeightEnd(null);
							sizeTableDO.setHeightStart(null);
						}
						
						if (sizeTemplate.isShoulderable()) {
							sizeTableDO.setShoulderEnd(size.getShoulderEnd());
							sizeTableDO.setShoulderStart(size.getShoulderStart());
						}else {
							sizeTableDO.setShoulderEnd(null);
							sizeTableDO.setShoulderStart(null);
						}
						//写入尺码数据
						pdtSkuSizeTableDOMapper.updateByPrimaryKey(sizeTableDO);
					}else{
						//写入新数据
						PDTSkuSizeTableDO sizeTableDO = new PDTSkuSizeTableDO();
						
						sizeTableDO.setSizeName(size.getSizeName());
						
						sizeTableDO.setSizeTemplateId(templateDO.getId());
						sizeTableDO.setWaistlineable(BoolUtil.parseBoolean(sizeTemplate.isWaistlineable())); 
						sizeTableDO.setWeightable(BoolUtil.parseBoolean(sizeTemplate.isWeightable())); 
						sizeTableDO.setHipable(BoolUtil.parseBoolean(sizeTemplate.isHipable())); 
						sizeTableDO.setChestable(BoolUtil.parseBoolean(sizeTemplate.isChestable()));
						sizeTableDO.setHeightable(BoolUtil.parseBoolean(sizeTemplate.isHeightable())) ;
						sizeTableDO.setShoulderable(BoolUtil.parseBoolean(sizeTemplate.isShoulderable()));
						
						if (sizeTemplate.isWaistlineable()) {
							sizeTableDO.setWaistlineEnd(size.getWaistlineEnd());
							sizeTableDO.setWaistlineStart(size.getWaistlineStart());
						}else {
							sizeTableDO.setWaistlineEnd(null);
							sizeTableDO.setWaistlineStart(null);
						}
						
						if (sizeTemplate.isWeightable()) {
							sizeTableDO.setWeightEnd(size.getWeightEnd());
							sizeTableDO.setWeightStart(size.getWeightStart());
						}else{
							sizeTableDO.setWeightEnd(null);
							sizeTableDO.setWeightStart(null);
						}
						
						if (sizeTemplate.isHipable()) {
							sizeTableDO.setHipEnd(size.getHipEnd());
							sizeTableDO.setHipStart(size.getHipStart());
						}else{
							sizeTableDO.setHipEnd(null);
							sizeTableDO.setHipStart(null);
						}
						
						if (sizeTemplate.isChestable()) {
							sizeTableDO.setChestEnd(size.getChestEnd());
							sizeTableDO.setChestStart(size.getChestStart());
						}else{
							sizeTableDO.setChestEnd(null);
							sizeTableDO.setChestStart(null);
						}
						
						if (sizeTemplate.isHeightable()) {
							sizeTableDO.setHeightEnd(size.getHeightEnd());
							sizeTableDO.setHeightStart(size.getHeightStart());
						}else {
							sizeTableDO.setHeightEnd(null);
							sizeTableDO.setHeightStart(null);
						}
						
						if (sizeTemplate.isShoulderable()) {
							sizeTableDO.setShoulderEnd(size.getShoulderEnd());
							sizeTableDO.setShoulderStart(size.getShoulderStart());
						}else {
							sizeTableDO.setShoulderEnd(null);
							sizeTableDO.setShoulderStart(null);
						}
						//写入尺码数据
						pdtSkuSizeTableDOMapper.insert(sizeTableDO);
					}
				}
			}
		});
		this.defineCommonService.clearProductDefineCache();
	}
	
	/**
	 * 验证尺码集合合法性
	 * @param waistlineable
	 * @param weightable
	 * @param hipable
	 * @param chestable
	 * @param heightable
	 * @param shoulderable
	 * @param sizes
	 * @return
	 */
	private boolean _validateSizeCollectionData(boolean waistlineable, boolean weightable, boolean hipable,boolean chestable, boolean heightable, boolean shoulderable,Collection<Size> sizes) {
		if (sizes == null || sizes.size() <=0) {
			return true;
		}
		for (Size size : sizes) {
			//腰围
			if (waistlineable) {
				if (size.getWaistlineEnd() == null || size.getWaistlineStart() == null) {
					return false;
				}
			}
			//体重
			if (weightable) {
				if (size.getWeightEnd() == null || size.getWeightStart() == null) {
					return false;
				}
			}
			//臀围
			if (hipable) {
				if (size.getHipStart() == null || size.getHipEnd() == null) {
					return false;
				}
			}
			//胸围
			if (chestable) {
				if (size.getChestEnd() == null || size.getChestStart() == null) {
					return false;
				}
			}
			//肩宽
			if (shoulderable) {
				if (size.getShoulderEnd() == null || size.getShoulderStart() == null) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean _validateSizeData(boolean waistlineable, boolean weightable, boolean hipable,boolean chestable, boolean heightable, boolean shoulderable,Size size) {
		if (size == null) {
			return true;
		}
		//腰围
		if (waistlineable) {
			if (size.getWaistlineEnd() == null || size.getWaistlineStart() == null) {
				return false;
			}
		}
		//体重
		if (weightable) {
			if (size.getWeightEnd() == null || size.getWeightStart() == null) {
				return false;
			}
		}
		//臀围
		if (hipable) {
			if (size.getHipStart() == null || size.getHipEnd() == null) {
				return false;
			}
		}
		//胸围
		if (chestable) {
			if (size.getChestEnd() == null || size.getChestStart() == null) {
				return false;
			}
		}
		//肩宽
		if (shoulderable) {
			if (size.getShoulderEnd() == null || size.getShoulderStart() == null) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public SizeTemplate updateBaseSizeTemplateInfo(final int id, final String templateName, final String content) throws ObjectHasSubObjectException, ExistProductsNotAllowWriteException {
		final SizeTemplate loadSizeTemplate = this.getSizeTemplateAndValidate(id);
		if (!this.defineCommonService.validateAllowUDOperateSizeTemplate(id)) {
			throw new ExistProductsNotAllowWriteException();
		}
		PDTSkuSizeTemplateDO tempModel = this.pdtSkuSizeTemplateDOMapper.selectByTemplateName_NotSelf(id, templateName);
		if (tempModel != null) {
			throw new ObjectHasSubObjectException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				loadSizeTemplate.setTemplateName(templateName);
				loadSizeTemplate.setTemplateContent(content);
				PDTSkuSizeTemplateDO model = BeanUtil.toPdtSkuSizeTemplateDO(loadSizeTemplate);
				pdtSkuSizeTemplateDOMapper.updateByPrimaryKeyWithBLOBs(model);
			}
		});
		defineCommonService.clearProductDefineCache();
		return getSizeTemplate(id);
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.SizeService#deleteSizeTemplate(int)
	 */
	@Override
	public void deleteSizeTemplate(final int sizeTemplateId) throws ExistProductsNotAllowWriteException {
		this.getSizeTemplateAndValidate(sizeTemplateId);
		if(!this.defineCommonService.validateAllowUDOperateSizeTemplate(sizeTemplateId)) {
			throw new ExistProductsNotAllowWriteException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtSkuSizeTableDOMapper.deleteBySizeTemplateId(sizeTemplateId);
				pdtSkuSizeTemplateDOMapper.deleteByPrimaryKey(sizeTemplateId);
			}
		});
		defineCommonService.clearSizeTableCache();
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.SizeService#getSizeTemplates()
	 */
	@Override
	public Collection<SizeTemplate> getSizeTemplates() {
		List<PDTSkuSizeTemplateDO> models = this.pdtSkuSizeTemplateDOMapper.selectAll();
		if (models == null || models.size() <=0) {
			return null;
		}
		Collection<SizeTemplate> sizeTemplates = new ArrayList<SizeTemplate>();
		for (PDTSkuSizeTemplateDO m : models) {
			sizeTemplates.add(BeanUtil.toSizeTemplate(m));
		}
		return sizeTemplates;
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.SizeService#addSize(com.vanstone.jupin.productdefine.attr.sku.Size)
	 */
	@Override
	public Size addSize(final Size size) throws ObjectDuplicateException,ExistProductsNotAllowWriteException {
		MyAssert4Business.objectInitialized(size.getSizeTemplate());
		if (!defineCommonService.validateAllowUDOperateSizeTemplate(size.getSizeTemplate().getId())) {
			throw new ExistProductsNotAllowWriteException();
		}
		PDTSkuSizeTableDO tempModel = this.pdtSkuSizeTableDOMapper.selectBySizeTemplateId_SizeName(size.getSizeTemplate().getId(), size.getSizeName());
		if (tempModel != null) {
			throw new ObjectDuplicateException();
		}
		if (!_validateSizeData(size.getSizeTemplate().isWaistlineable(), size.getSizeTemplate().isWeightable(), size
				.getSizeTemplate().isHipable(), size.getSizeTemplate().isChestable(), size.getSizeTemplate()
				.isHeightable(), size.getSizeTemplate().isShoulderable(), size)) {
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTSkuSizeTableDO model = BeanUtil.toPDTSkuSizeTableDO(size);
				if (size.isChestable()) {
					model.setChestable(BoolUtil.parseBoolean(true));
					model.setChestEnd(size.getChestEnd());
					model.setChestStart(size.getChestStart());
				}
				if (size.isHeightable()) {
					model.setHeightable(BoolUtil.parseBoolean(true));
					model.setHeightEnd(size.getHeightEnd());
					model.setHeightStart(size.getHeightStart());
				}
				if (size.isHipable()) {
					model.setHipable(BoolUtil.parseBoolean(true));
					model.setHipEnd(size.getHipEnd());
					model.setHipStart(size.getHipStart());
				}
				if (size.isShoulderable()) {
					model.setShoulderable(BoolUtil.parseBoolean(true));
					model.setShoulderEnd(size.getShoulderEnd());
					model.setShoulderStart(size.getShoulderStart());
				}
				if (size.isWaistlineable()) {
					model.setWaistlineable(BoolUtil.parseBoolean(true));
					model.setWaistlineEnd(size.getWaistlineEnd());
					model.setWaistlineStart(size.getWaistlineStart());
				}
				if (size.isWeightable()) {
					model.setWeightable(BoolUtil.parseBoolean(true));
					model.setWeightEnd(size.getWeightEnd());
					model.setWeightStart(size.getWeightStart());
				}
				pdtSkuSizeTableDOMapper.insert(model);
				size.setId(model.getId());
			}
		});
		defineCommonService.clearProductDefineCache();
		return size;
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.SizeService#updateSize(com.vanstone.jupin.productdefine.attr.sku.Size)
	 */
	@Override
	public Size updateSize(final Size size) throws ObjectDuplicateException, ExistProductsNotAllowWriteException {
		MyAssert4Business.objectInitialized(size.getSizeTemplate());
		MyAssert4Business.objectInitialized(size);
		if (!defineCommonService.validateAllowUDOperateSizeTemplate(size.getSizeTemplate().getId())) {
			throw new ExistProductsNotAllowWriteException();
		}
		PDTSkuSizeTableDO tempModel = this.pdtSkuSizeTableDOMapper.selectBySizeTemplateId_SizeName_NotSelf(size.getSizeTemplate().getId(), size.getSizeName(),size.getId());
		if (tempModel != null) {
			throw new ObjectDuplicateException();
		}
		if (!_validateSizeData(size.getSizeTemplate().isWaistlineable(), size.getSizeTemplate().isWeightable(), size
				.getSizeTemplate().isHipable(), size.getSizeTemplate().isChestable(), size.getSizeTemplate()
				.isHeightable(), size.getSizeTemplate().isShoulderable(), size)) {
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTSkuSizeTableDO model = BeanUtil.toPDTSkuSizeTableDO(size);
				if (size.isChestable()) {
					model.setChestable(BoolUtil.parseBoolean(true));
					model.setChestEnd(size.getChestEnd());
					model.setChestStart(size.getChestStart());
				}
				if (size.isHeightable()) {
					model.setHeightable(BoolUtil.parseBoolean(true));
					model.setHeightEnd(size.getHeightEnd());
					model.setHeightStart(size.getHeightStart());
				}
				if (size.isHipable()) {
					model.setHipable(BoolUtil.parseBoolean(true));
					model.setHipEnd(size.getHipEnd());
					model.setHipStart(size.getHipStart());
				}
				if (size.isShoulderable()) {
					model.setShoulderable(BoolUtil.parseBoolean(true));
					model.setShoulderEnd(size.getShoulderEnd());
					model.setShoulderStart(size.getShoulderStart());
				}
				if (size.isWaistlineable()) {
					model.setWaistlineable(BoolUtil.parseBoolean(true));
					model.setWaistlineEnd(size.getWaistlineEnd());
					model.setWaistlineStart(size.getWaistlineStart());
				}
				if (size.isWeightable()) {
					model.setWeightable(BoolUtil.parseBoolean(true));
					model.setWeightEnd(size.getWeightEnd());
					model.setWeightStart(size.getWeightStart());
				}
				pdtSkuSizeTableDOMapper.updateByPrimaryKey(model);
			}
		});
		defineCommonService.clearProductDefineCache();
		return size;
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.SizeService#deleteSize(int)
	 */
	@Override
	public void deleteSize(final int sizeId) throws ExistProductsNotAllowWriteException {
		final PDTSkuSizeTableDO model = this.pdtSkuSizeTableDOMapper.selectByPrimaryKey(sizeId);
		if (model == null) {
			throw new IllegalArgumentException();
		}
		if (!defineCommonService.validateAllowUDOperateSizeTemplate(model.getSizeTemplateId())) {
			throw new ExistProductsNotAllowWriteException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtSkuSizeTableDOMapper.deleteByPrimaryKey(sizeId);
				List<PDTSkuSizeTableDO> sizeTableDOs = pdtSkuSizeTableDOMapper.selectBySizeTemplateId(model.getSizeTemplateId());
				if (sizeTableDOs == null || sizeTableDOs.size() <=0) {
					pdtSkuSizeTemplateDOMapper.deleteByPrimaryKey(model.getSizeTemplateId());
				}
			}
		});
		defineCommonService.clearProductDefineCache();
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.SizeService#getSizeTemplatesWithStat()
	 */
	@Override
	public Collection<SizeTemplateWrapBean> getSizeTemplatesWithStat() {
		List<QuerySizeTemplateDOWithSizeTableResultMap> resultMaps = this.pdtSkuSizeTableDOMapper.selectSizeTemplate_SizeTable_ResultMap();
		if (resultMaps == null || resultMaps.size() <=0) {
			return null;
		}
		Map<Integer,SizeTemplateWrapBean> wrapBeanMap = new LinkedHashMap<Integer,SizeTemplateWrapBean>();
		Map<Integer, SizeTemplate> sizeTemplateMap = new LinkedHashMap<Integer, SizeTemplate>();
		
		for (QuerySizeTemplateDOWithSizeTableResultMap rm : resultMaps) {
			SizeTemplate sizeTemplate = null;
			if (sizeTemplateMap.containsKey(rm.getSizeTemplateId())) {
				sizeTemplate = sizeTemplateMap.get(rm.getSizeTemplateId());
			}else{
				sizeTemplate = new SizeTemplate();
				sizeTemplate.setId(rm.getTemplateId());
				sizeTemplate.setTemplateName(rm.getTemplateName());
				sizeTemplate.setTemplateContent(rm.getTemplateContent());
				sizeTemplate.setSystemable(BoolUtil.parseInt(rm.getSystemable()));
				sizeTemplate.setWaistlineable(BoolUtil.parseInt(rm.getWaistlineable()));
				sizeTemplate.setWeightable(BoolUtil.parseInt(rm.getWeightable()));
				sizeTemplate.setHipable(BoolUtil.parseInt(rm.getHipable()));
				sizeTemplate.setChestable(BoolUtil.parseInt(rm.getChestable()));
				sizeTemplate.setHeightable(BoolUtil.parseInt(rm.getHeightable()));
				sizeTemplate.setShoulderable(BoolUtil.parseInt(rm.getShoulderable()));
				
				//sizeTemplateMap初始化
				sizeTemplateMap.put(rm.getSizeTemplateId(), sizeTemplate);
				//SizeTemplateWrapBean初始化
				SizeTemplateWrapBean bean = new SizeTemplateWrapBean();
				bean.setSizeTemplate(sizeTemplate);
				wrapBeanMap.put(rm.getTemplateId(), bean);
			}
			if (rm.getId() != null) {
				Size size = new Size(sizeTemplate);
				size.setId(rm.getId());
				size.setWaistlineable(BoolUtil.parseInt(rm.getWaistlineable()));
				size.setWaistlineEnd(rm.getWaistlineEnd());
				size.setWaistlineStart(rm.getWaistlineStart());
				
				size.setWeightable(BoolUtil.parseInt(rm.getWeightable()));
				size.setWeightEnd(rm.getWeightEnd());
				size.setWeightStart(rm.getWeightStart());
				
				size.setHipable(BoolUtil.parseInt(rm.getHipable()));
				size.setHipEnd(rm.getHipEnd());
				size.setHipStart(rm.getHipStart());
				
				size.setChestable(BoolUtil.parseInt(rm.getChestable()));
				size.setChestEnd(rm.getChestEnd());
				size.setChestStart(rm.getChestStart());
				
				size.setHeightable(BoolUtil.parseInt(rm.getHeightable()));
				size.setHeightEnd(rm.getHeightEnd());
				size.setHeightStart(rm.getHeightStart());
				
				size.setShoulderable(BoolUtil.parseInt(rm.getShoulderable()));
				size.setShoulderEnd(rm.getShoulderEnd());
				size.setShoulderStart(rm.getShoulderStart());
				
				size.setSizeName(rm.getSizeName());
				wrapBeanMap.get(rm.getTemplateId()).addSize(size);
			}
		}
		Collection<SizeTemplateWrapBean> sizeTemplateWrapBeans = wrapBeanMap.values();
		for (SizeTemplateWrapBean bean : sizeTemplateWrapBeans) {
			bean.getSizeTemplate().setSizeCount(bean.getSizes().size());
			bean.setUdable(this.defineCommonService.validateAllowUDOperateSizeTemplate(bean.getSizeTemplate().getId()));
		}
		return sizeTemplateWrapBeans;
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.SizeService#getSizeTemplate(int)
	 */
	@Override
	public SizeTemplate getSizeTemplate(final int id) {
		PDTSkuSizeTemplateDO model = this.pdtSkuSizeTemplateDOMapper.selectByPrimaryKey(id);
		if (model  == null) {
			return null;
		}
		return BeanUtil.toSizeTemplate(model);                                                             
	}               
	
	@Override
	public Size getSize(final int sizeId) {
		final String key = PDCache.getSizeKey(sizeId);
		Size loadSize = ZKUtil.executeMutex(key, new InterProcessMutexCallback<Size>() {
			@Override
			public Size doInAcquireMutex(CuratorFramework curatorFramework) {
				PDTSkuSizeTableDO model = pdtSkuSizeTableDOMapper.selectByPrimaryKey(sizeId);
				if (model == null) {
					return null;
				}
				SizeTemplate sizeTemplate = getSizeTemplate(model.getSizeTemplateId());
				if (sizeTemplate == null) {
					throw new IllegalArgumentException();
				}
				Size size = BeanUtil.toSize(model, sizeTemplate);
				ServiceUtil<Size, Integer> serviceUtil = new ServiceUtil<Size, Integer>();
				serviceUtil.setObjectToRedis(redisTemplate, JupinRedisRef.Jupin_Core, key, size);
				return size;
			}
			
			@Override
			public Size doInNotAcquireMutex(CuratorFramework curatorFramework) {
				try {
					TimeUnit.SECONDS.sleep(Constants.ZK_BUSINESS_EXECUTE_WAITING_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return getSize(sizeId);
			}
		});
		return loadSize;
	}
	
	@Override
	public SizeTemplate getSizeTemplateAndValidate(int id) {
		SizeTemplate loadSizeTemplate = this.getSizeTemplate(id);
		if (loadSizeTemplate == null) {
			throw new IllegalArgumentException();
		}
		return loadSizeTemplate;
	}

	@Override
	public Collection<Size> getSizes(SizeTemplate sizeTemplate) {
		MyAssert4Business.objectInitialized(sizeTemplate);
		List<PDTSkuSizeTableDO> tableDOs = pdtSkuSizeTableDOMapper.selectBySizeTemplateId(sizeTemplate.getId());
		if (tableDOs == null || tableDOs.size() <=0) {
			return null;
		}
		Collection<Size> sizes = new ArrayList<Size>();
		for (PDTSkuSizeTableDO model : tableDOs) {
			Size size = BeanUtil.toSize(model, sizeTemplate);
			sizes.add(size);
		}
		return sizes;
	}

	@Override
	public boolean existSizeName(SizeTemplate sizeTemplate, String sizeName) {
		MyAssert4Business.objectInitialized(sizeTemplate);
		MyAssert4Business.hasText(sizeName);
		PDTSkuSizeTableDO model = this.pdtSkuSizeTableDOMapper.selectBySizeTemplateId_SizeName(sizeTemplate.getId(), sizeName);
		if (model != null) {
			return true;
		}
		return false;
	}
	
}
                                                          