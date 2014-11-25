/**
 * 
 */
package com.vanstone.jupin.productdefine.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.validation.annotation.Validated;

import redis.clients.jedis.Jedis;

import com.vanstone.business.MyAssert4Business;
import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.business.ObjectHasSubObjectException;
import com.vanstone.business.def.BusinessObjectKeyBuilder;
import com.vanstone.framework.business.services.DefaultBusinessService;
import com.vanstone.framework.business.services.ServiceUtil;
import com.vanstone.jupin.common.Constants;
import com.vanstone.jupin.common.util.BoolUtil;
import com.vanstone.jupin.common.util.InterProcessMutexCallback;
import com.vanstone.jupin.common.util.ZKUtil;
import com.vanstone.jupin.framework.cache.JupinRedisRef;
import com.vanstone.jupin.productdefine.attr.sku.Size;
import com.vanstone.jupin.productdefine.attr.sku.SizeTemplate;
import com.vanstone.jupin.productdefine.attr.sku.SizeTemplateWrapBean;
import com.vanstone.jupin.productdefine.cache.ProductDefineCacheKey;
import com.vanstone.jupin.productdefine.persistence.PDTSkuSizeTableDOMapper;
import com.vanstone.jupin.productdefine.persistence.PDTSkuSizeTemplateDOMapper;
import com.vanstone.jupin.productdefine.persistence.object.PDTSkuSizeTableDO;
import com.vanstone.jupin.productdefine.persistence.object.PDTSkuSizeTemplateDO;
import com.vanstone.jupin.productdefine.persistence.object.QuerySizeTemplateDOWithSizeTableResultMap;
import com.vanstone.jupin.productdefine.services.CategoryHasProductsException;
import com.vanstone.jupin.productdefine.services.SizeService;
import com.vanstone.redis.RedisCallbackWithoutResult;
import com.vanstone.redis.RedisTemplate;

/**
 * @author shipeng
 */
@Service("sizeService")
@Validated
public class SizeServiceImpl extends DefaultBusinessService implements SizeService {
	
	/***/
	private static final long serialVersionUID = 5077934760251196625L;
	
	private static Logger LOG = LoggerFactory.getLogger(SizeServiceImpl.class);
	
	@Autowired
	private PDTSkuSizeTableDOMapper pdtSkuSizeTableDOMapper;
	@Autowired
	private PDTSkuSizeTemplateDOMapper pdtSkuSizeTemplateDOMapper;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private ProductDefineCommonService productDefineCommonService;
	
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
		return this.execute(new TransactionCallback<SizeTemplate>() {
			@Override
			public SizeTemplate doInTransaction(TransactionStatus arg0) {
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
				return getSizeTemplate(templateDO.getId());
			}
		});
	}
	
	@Override
	public SizeTemplate updateSizeTemplate(final int id, final boolean systemable,
			final boolean waistlineable, final boolean weightable, final boolean hipable,
			final boolean chestable, final boolean heightable, final boolean shoulderable,
			final @NotEmpty Collection<Size> sizes)
			throws ObjectDuplicateException, CategoryHasProductsException {
		SizeTemplate sizeTemplate = this.getSizeTemplate(id);
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
		if (!_validateSizeCollectionData(waistlineable, weightable, hipable, chestable, heightable, shoulderable, sizes)) {
			throw new IllegalArgumentException("waistlineable or weightable or hipable or chestable or heightable or shoulderable not illegal, please check");
		}
		return this.execute(new TransactionCallback<SizeTemplate>() {
			@Override
			public SizeTemplate doInTransaction(TransactionStatus arg0) {
				PDTSkuSizeTemplateDO templateDO = new PDTSkuSizeTemplateDO();
				templateDO.setId(id);
				templateDO.setSystemable(BoolUtil.parseBoolean(systemable));
				templateDO.setWaistlineable(BoolUtil.parseBoolean(waistlineable)); 
				templateDO.setWeightable(BoolUtil.parseBoolean(weightable)); 
				templateDO.setHipable(BoolUtil.parseBoolean(hipable)); 
				templateDO.setChestable(BoolUtil.parseBoolean(chestable)); 
				templateDO.setHeightable(BoolUtil.parseBoolean(heightable)); 
				templateDO.setShoulderable(BoolUtil.parseBoolean(shoulderable));
				//修改模板数据
				pdtSkuSizeTemplateDOMapper.updateByPrimaryKeySelective(templateDO);
				pdtSkuSizeTableDOMapper.deleteBySizeTemplateId(templateDO.getId());
				
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
				return getSizeTemplate(templateDO.getId());
			}
		});
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
	public SizeTemplate updateBaseSizeTemplateInfo(final int id, final String templateName, final String content) throws ObjectHasSubObjectException, CategoryHasProductsException {
		final SizeTemplate loadSizeTemplate = this.getSizeTemplateAndValidate(id);
		if (!this.productDefineCommonService.validateProductCategoryBySizeTemplate(id)) {
			throw new CategoryHasProductsException();
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
		refreshSizeTables();
		return getSizeTemplate(id);
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.SizeService#deleteSizeTemplate(int)
	 */
	@Override
	public void deleteSizeTemplate(final int sizeTemplateId) throws CategoryHasProductsException {
		this.getSizeTemplateAndValidate(sizeTemplateId);
		if(!this.productDefineCommonService.validateProductCategoryBySizeTemplate(sizeTemplateId)) {
			throw new CategoryHasProductsException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtSkuSizeTableDOMapper.deleteBySizeTemplateId(sizeTemplateId);
				pdtSkuSizeTemplateDOMapper.deleteByPrimaryKey(sizeTemplateId);
			}
		});
		refreshSizeTables();
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
	public Size addSize(final Size size) throws ObjectDuplicateException,CategoryHasProductsException {
		MyAssert4Business.objectInitialized(size.getSizeTemplate());
		if (!this.productDefineCommonService.validateProductCategoryBySizeTemplate(size.getSizeTemplate().getId())) {
			throw new CategoryHasProductsException();
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
		refreshSizeTables();
		return size;
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.SizeService#updateSize(com.vanstone.jupin.productdefine.attr.sku.Size)
	 */
	@Override
	public Size updateSize(final Size size) throws ObjectDuplicateException, CategoryHasProductsException {
		MyAssert4Business.objectInitialized(size.getSizeTemplate());
		MyAssert4Business.objectInitialized(size);
		if (!this.productDefineCommonService.validateProductCategoryBySizeTemplate(size.getSizeTemplate().getId())) {
			throw new CategoryHasProductsException();
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
		refreshSizeTables();
		return size;
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.SizeService#deleteSize(int)
	 */
	@Override
	public void deleteSize(final int sizeId) throws CategoryHasProductsException {
		PDTSkuSizeTableDO model = this.pdtSkuSizeTableDOMapper.selectByPrimaryKey(sizeId);
		if (model == null) {
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtSkuSizeTableDOMapper.deleteByPrimaryKey(sizeId);
			}
		});
		refreshSizeTables();
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
				sizeTemplate.setId(rm.getSizeTemplateId());
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
				wrapBeanMap.put(rm.getSizeTemplateId(), bean);
			}
			Size size = new Size(sizeTemplate);
			
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
			wrapBeanMap.get(rm.getSizeTemplateId()).addSize(size);
		}
		Collection<SizeTemplateWrapBean> sizeTemplateWrapBeans = wrapBeanMap.values();
		for (SizeTemplateWrapBean bean : sizeTemplateWrapBeans) {
			bean.getSizeTemplate().setSizeCount(bean.getSizes().size());
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
	
//	private SizeTemplate _getSizeTemplateFromRedis(RedisTemplate redisTemplate, final int id) {
//		MyAssert4Business.notNull(redisTemplate);
//		final String key = ProductDefineCacheKey.getSizeTemplateKey(id);
//		redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallback<SizeTemplate>() {
//			@Override
//			public SizeTemplate doInRedis(Jedis jedis) {
//				if (!jedis.exists(key)) {
//					return null;
//				}
//				//load from redis
//				String[] templateTableDefine = new String[] {
//					"id",
//					"templateName",
//					"templateContent",
//					"systemable",
//					"waistlineable",
//					"weightable",
//					"chestable",
//					"heightable",
//					"shoulderable"
//				};
//				List<String> values = jedis.hmget(key, templateTableDefine);
//				SizeTemplate sizeTemplate = new SizeTemplate();
//				sizeTemplate.setId(Integer.parseInt(values.get(0)));
//				sizeTemplate.setTemplateName(values.get(1));
//				sizeTemplate.setTemplateContent(values.get(2));
//				sizeTemplate.setSystemable(Boolean.parseBoolean(values.get(3)));
//				sizeTemplate.setWaistlineable(Boolean.parseBoolean(values.get(4)));
//				sizeTemplate.setWeightable(Boolean.parseBoolean(values.get(5)));
//				sizeTemplate.setChestable(Boolean.parseBoolean(values.get(6)));
//				sizeTemplate.setHeightable(Boolean.parseBoolean(values.get(7)));
//				sizeTemplate.setShoulderable(Boolean.parseBoolean(values.get(8)));
//				return null;
//			}
//		});
//	}
	
	@Override
	public Size getSize(final int sizeId) {
		Size loadSize = ZKUtil.executeMutex(Constants.buildZKLockMutexNodePath(BusinessObjectKeyBuilder.class2key(Size.class, sizeId)), new InterProcessMutexCallback<Size>() {
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
				serviceUtil.setObjectToRedis(redisTemplate, JupinRedisRef.Jupin_Core, ProductDefineCacheKey.SIZE_CACHE_PREFIX + BusinessObjectKeyBuilder.class2key(Size.class, sizeId), size);
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
	public void refreshSizeTables() {
		this.redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallbackWithoutResult() {
			@Override
			public void doInRedisWithoutResult(Jedis jedis) {
				//清理内置缓冲
				Set<String> keies = jedis.keys(ProductDefineCacheKey.SIZE_CACHE_PREFIX + "*");
				if (keies == null || keies.size() <=0) {
					return;
				}
				jedis.del(keies.toArray(new String[keies.size()]));
				LOG.info("Refreshed Size Cache");
			}
		});
	}
}
                                                          