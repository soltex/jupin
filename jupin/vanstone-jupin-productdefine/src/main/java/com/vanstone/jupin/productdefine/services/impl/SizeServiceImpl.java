/**
 * 
 */
package com.vanstone.jupin.productdefine.services.impl;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.validation.annotation.Validated;

import redis.clients.jedis.Jedis;

import com.vanstone.business.MyAssert4Business;
import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.framework.business.services.DefaultBusinessService;
import com.vanstone.jupin.common.util.BoolUtil;
import com.vanstone.jupin.framework.cache.JupinRedisRef;
import com.vanstone.jupin.productdefine.attr.sku.Size;
import com.vanstone.jupin.productdefine.attr.sku.SizeTemplate;
import com.vanstone.jupin.productdefine.attr.sku.SizeTemplateWrapBean;
import com.vanstone.jupin.productdefine.cache.ProductDefineCacheKey;
import com.vanstone.jupin.productdefine.persistence.PDTSkuSizeTableDOMapper;
import com.vanstone.jupin.productdefine.persistence.PDTSkuSizeTemplateDOMapper;
import com.vanstone.jupin.productdefine.persistence.object.PDTSkuSizeTableDO;
import com.vanstone.jupin.productdefine.persistence.object.PDTSkuSizeTemplateDO;
import com.vanstone.jupin.productdefine.services.CategoryHasProductsException;
import com.vanstone.jupin.productdefine.services.SizeService;
import com.vanstone.redis.RedisCallback;
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
	private ProductDefineCommonService productDefineCommonService;
	
	@Override
	public SizeTemplate addSizeTemplate(final String templateName, final String content, 
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
					sizeTableDO.setSizeTemplateId(templateDO.getId());
					sizeTableDO.setWaistlineable(BoolUtil.parseBoolean(waistlineable)); 
					sizeTableDO.setWeightable(BoolUtil.parseBoolean(weightable)); 
					sizeTableDO.setHipable(BoolUtil.parseBoolean(hipable)); 
					sizeTableDO.setChestable(BoolUtil.parseBoolean(chestable));
					sizeTableDO.setHeightable(BoolUtil.parseBoolean(heightable)) ;
					sizeTableDO.setShoulderable(BoolUtil.parseBoolean(shoulderable));
					if (waistlineable) {
						sizeTableDO.setWaistlineEnd(size.getWaislineEnd());
						sizeTableDO.setWaistlineStart(size.getWaislineStart());
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
				return refreshSizeTemplate(templateDO.getId());
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
				if (size.getWaislineEnd() == null || size.getWaislineStart() == null) {
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
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.SizeService#updateSizeTemplate(com.vanstone.jupin.productdefine.attr.sku.SizeTemplate, java.util.Collection)
	 */
	@Override
	public SizeTemplate updateSizeTemplate(final SizeTemplate sizeTemplate, final Collection<Size> sizes) throws ObjectDuplicateException,CategoryHasProductsException {
		MyAssert4Business.objectInitialized(sizeTemplate);
		if (!this.productDefineCommonService.validateProductCategoryBySizeTemplate(sizeTemplate.getId())) {
			throw new CategoryHasProductsException();
		}
		PDTSkuSizeTemplateDO tempModel = this.pdtSkuSizeTemplateDOMapper.selectByTemplateName_NotSelf(sizeTemplate.getId(), sizeTemplate.getTemplateName());
		if (tempModel != null) {
			throw new ObjectDuplicateException();
		}
		final SizeTemplate loadSizeTemplate = this.getSizeTemplateAndValidate(sizeTemplate.getId());
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				
			}
		});
		return null;
	}
	
	@Override
	public SizeTemplate updateBaseSizeTemplateInfo(int id, String templateName, String content) throws CategoryHasProductsException {
		
		return null;
	}
	
	@Override
	public void refreshSizeTables() {
		
	}

	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.SizeService#deleteSizeTemplate(int)
	 */
	@Override
	public SizeTemplate deleteSizeTemplate(int sizeTemplateId)
			throws CategoryHasProductsException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.SizeService#getSizeTemplates()
	 */
	@Override
	public Collection<SizeTemplate> getSizeTemplates() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.SizeService#addSize(com.vanstone.jupin.productdefine.attr.sku.Size)
	 */
	@Override
	public Size addSize(Size size) throws ObjectDuplicateException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.SizeService#updateSize(com.vanstone.jupin.productdefine.attr.sku.Size)
	 */
	@Override
	public Size updateSize(Size size) throws ObjectDuplicateException,
			CategoryHasProductsException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.SizeService#deleteSize(int)
	 */
	@Override
	public void deleteSize(int sizeId) throws CategoryHasProductsException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.SizeService#getSizeTemplatesWithStat()
	 */
	@Override
	public Collection<SizeTemplateWrapBean> getSizeTemplatesWithStat() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.SizeService#getSizeTemplate(int)
	 */
	@Override
	public SizeTemplate getSizeTemplate(final int id) {
		return null;                                                                            
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
	public SizeTemplate getSizeTemplateAndValidate(int id) {
		return null;
	}
	
	@Override
	public SizeTemplate refreshSizeTemplate(int tempateId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
                                                          