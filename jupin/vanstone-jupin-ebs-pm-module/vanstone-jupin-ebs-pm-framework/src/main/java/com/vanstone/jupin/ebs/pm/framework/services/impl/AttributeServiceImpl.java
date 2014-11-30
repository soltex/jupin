/**
 * 
 */
package com.vanstone.jupin.ebs.pm.framework.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.validation.annotation.Validated;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vanstone.business.MyAssert4Business;
import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.business.serialize.GsonCreator;
import com.vanstone.framework.business.services.DefaultBusinessService;
import com.vanstone.framework.business.services.ServiceUtil;
import com.vanstone.jupin.common.Constants;
import com.vanstone.jupin.common.util.BoolUtil;
import com.vanstone.jupin.common.util.InterProcessMutexCallback;
import com.vanstone.jupin.common.util.ZKUtil;
import com.vanstone.jupin.ebs.pm.PDCache;
import com.vanstone.jupin.ebs.pm.framework.persistence.PDTAttributeDefDOMapper;
import com.vanstone.jupin.ebs.pm.framework.persistence.PDTAttributeEnumvalueDOMapper;
import com.vanstone.jupin.ebs.pm.framework.persistence.object.PDTAttributeDefDO;
import com.vanstone.jupin.ebs.pm.framework.persistence.object.PDTAttributeEnumvalueDO;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.AbstractAttribute;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.Attr4Enum;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.Attr4EnumValue;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.Attr4Text;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.AttributeState;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.AttributeType;
import com.vanstone.jupin.ebs.pm.productdefine.services.AttributeService;
import com.vanstone.jupin.ebs.pm.productdefine.services.DefineCommonService;
import com.vanstone.jupin.framework.cache.JupinRedisRef;
import com.vanstone.redis.RedisCallback;
import com.vanstone.redis.RedisCallbackWithoutResult;
import com.vanstone.redis.RedisTemplate;

/**
 * @author shipeng
 */
@Service("attributeService")
@Validated
public class AttributeServiceImpl extends DefaultBusinessService implements AttributeService {

	/***/
	private static final long serialVersionUID = -4523036914761153321L;
	
	private static Logger LOG = LoggerFactory.getLogger(AttributeServiceImpl.class);
	
	@Autowired
	private PDTAttributeDefDOMapper pdtAttributeDefDOMapper;
	@Autowired
	private PDTAttributeEnumvalueDOMapper pdtAttributeEnumvalueDOMapper;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private DefineCommonService defineCommonService;
	
	@Override
	public Attr4Text addAttr4Text(final Attr4Text attr4Text) {
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTAttributeDefDO model = new PDTAttributeDefDO();
				model.setAttributeName(attr4Text.getAttributeName());
				model.setAttributeDescription(attr4Text.getAttributeDescription());
				model.setAttributeType(attr4Text.getAttributeType().getCode());
				model.setListshowable(BoolUtil.parseBoolean(attr4Text.isListshowable()));
				model.setRequiredable(BoolUtil.parseBoolean(attr4Text.isRequiredable()));
				pdtAttributeDefDOMapper.insert(model);
				attr4Text.setId(model.getId());
			}
		});
		return attr4Text;
	}

	@Override
	public Attr4Enum addAttr4Enum(final Attr4Enum attr4Enum) {
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTAttributeDefDO model = new PDTAttributeDefDO();
				model.setAttributeName(attr4Enum.getAttributeName());
				model.setAttributeDescription(attr4Enum.getAttributeDescription());
				model.setAttributeType(attr4Enum.getAttributeType().getCode());
				model.setListshowable(BoolUtil.parseBoolean(attr4Enum.isListshowable()));
				model.setRequiredable(BoolUtil.parseBoolean(attr4Enum.isRequiredable()));
				model.setMultiselectable(BoolUtil.parseBoolean(attr4Enum.isMultiselectable()));
				model.setSearchable(BoolUtil.parseBoolean(attr4Enum.isSearchable()));
				pdtAttributeDefDOMapper.insert(model);
				attr4Enum.setId(model.getId());
				int index = 1;
				for (String value : attr4Enum.getValues().values()) {
					PDTAttributeEnumvalueDO valueDo = new PDTAttributeEnumvalueDO();
					valueDo.setAttributeDefId(model.getId());
					valueDo.setObjecttext(value);
					valueDo.setSort(index);
					valueDo.setValueState(AttributeState.Active.getCode());
					pdtAttributeEnumvalueDOMapper.insert(valueDo);
					attr4Enum.putValue(valueDo.getId(), value);
					index++;
				}
			}
		});
		return attr4Enum;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * updateAttr4Text(com.vanstone.jupin.productdefine.attr.Attr4Text)
	 */
	@Override
	public Attr4Text updateAttr4Text(final Attr4Text attr4Text) {
		AbstractAttribute attribute = this.getAttribute(attr4Text.getId());
		if (attribute  == null || !attribute.getAttributeType().equals(AttributeType.Text)) {
			throw new IllegalArgumentException();
		}
		final Attr4Text loadAttr4Text = (Attr4Text)attribute;
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				loadAttr4Text.setAttributeDescription(attr4Text.getAttributeDescription());
				loadAttr4Text.setAttributeName(attr4Text.getAttributeName());
				loadAttr4Text.setListshowable(attr4Text.isListshowable());
				loadAttr4Text.setRequiredable(attr4Text.isRequiredable());
				PDTAttributeDefDO model = new PDTAttributeDefDO();
				model.setAttributeName(loadAttr4Text.getAttributeName());
				model.setAttributeDescription(loadAttr4Text.getAttributeDescription());
				model.setAttributeType(loadAttr4Text.getAttributeType().getCode());
				model.setListshowable(BoolUtil.parseBoolean(loadAttr4Text.isListshowable()));
				model.setRequiredable(BoolUtil.parseBoolean(loadAttr4Text.isRequiredable()));
				pdtAttributeDefDOMapper.updateByPrimaryKeyWithBLOBs(model);
			}
		});
		return loadAttr4Text;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vanstone.jupin.productdefine.services.ProductCategoryService#getAttribute
	 * (int)
	 */
	@Override
	public AbstractAttribute getAttribute(final int id) {
		//Load From Redis
		final String key = PDCache.getAttributeKey(id);
		AbstractAttribute attribute = this.redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallback<AbstractAttribute>() {
			@Override
			public AbstractAttribute doInRedis(Jedis jedis) {
				String value = jedis.get(key);
				if (value == null || value.equals("")) {
					return null;
				}
				Gson gson = GsonCreator.create();
				JsonObject jsonObject = gson.fromJson(value, JsonObject.class);
				String attributeType =jsonObject.get("attributeType").getAsString();
				AbstractAttribute attribute = null;
				if (attributeType.equalsIgnoreCase(AttributeType.Text.toString())) {
					//text type
					attribute = gson.fromJson(jsonObject, Attr4Text.class);
				}else{
					//enum type
					attribute = gson.fromJson(jsonObject, Attr4Enum.class);
				}
				return attribute;
			}
		});
		
		if (attribute != null) {
			return attribute;
		}
		
		return ZKUtil.executeMutex(key, new InterProcessMutexCallback<AbstractAttribute>() {
			@Override
			public AbstractAttribute doInAcquireMutex(CuratorFramework curatorFramework) {
				PDTAttributeDefDO model = pdtAttributeDefDOMapper.selectByPrimaryKey(id);
				if(model.getAttributeType().equals(AttributeType.Text.getCode())) {
					//文本类型
					final Attr4Text attr4Text = new Attr4Text();
					attr4Text.setAttributeDescription(model.getAttributeDescription());
					attr4Text.setAttributeName(model.getAttributeName());
					attr4Text.setId(model.getId());
					attr4Text.setListshowable(BoolUtil.parseInt(model.getListshowable()));
					attr4Text.setRequiredable(BoolUtil.parseInt(model.getRequiredable()));
					redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallbackWithoutResult() {
						@Override
						public void doInRedisWithoutResult(Jedis jedis) {
							Gson gson = GsonCreator.create();
							jedis.set(key, gson.toJson(attr4Text));
						}
					});
					return attr4Text;
				}else {
					//枚举类型
					final Attr4Enum attr4Enum = new Attr4Enum();
					attr4Enum.setAttributeDescription(model.getAttributeDescription());
					attr4Enum.setAttributeName(model.getAttributeName());
					attr4Enum.setId(model.getId());
					attr4Enum.setListshowable(BoolUtil.parseInt(model.getListshowable()));
					attr4Enum.setRequiredable(BoolUtil.parseInt(model.getRequiredable()));
					attr4Enum.setMultiselectable(BoolUtil.parseInt(model.getMultiselectable()));
					attr4Enum.setSearchable(BoolUtil.parseInt(model.getSearchable()));
					
					List<PDTAttributeEnumvalueDO> pdtAttributeEnumvalueDOs =pdtAttributeEnumvalueDOMapper.selectByAttributeDefId(id);
					if (pdtAttributeEnumvalueDOs == null || pdtAttributeEnumvalueDOs.size() <=0) {
						LOG.error("Attribute Def ID {} don't contain enumvalue", id);
						throw new IllegalArgumentException("");
					}
					for (PDTAttributeEnumvalueDO valueDo : pdtAttributeEnumvalueDOs) {
						attr4Enum.putValue(valueDo.getId(), valueDo.getObjecttext());
					}
					redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallbackWithoutResult() {
						@Override
						public void doInRedisWithoutResult(Jedis jedis) {
							Gson gson = GsonCreator.create();
							jedis.set(key, gson.toJson(attr4Enum));
						}
					});
					return attr4Enum;
				}
			}
			
			@Override
			public AbstractAttribute doInNotAcquireMutex(CuratorFramework curatorFramework) {
				try {
					TimeUnit.SECONDS.sleep(Constants.ZK_BUSINESS_EXECUTE_WAITING_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return getAttribute(id);
			}
			
		});
		
	}
	
	@Override
	public Attr4Enum appendAttr4EnumValue(final Attr4Enum attr4Enum, final String objectValue, final Integer sort) throws ObjectDuplicateException {
		MyAssert4Business.objectInitialized(attr4Enum);
		PDTAttributeEnumvalueDO tempModel = this.pdtAttributeEnumvalueDOMapper.selectByAttributeDefId_AttributeName(attr4Enum.getId(), objectValue);
		if (tempModel != null) {
			throw new ObjectDuplicateException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTAttributeEnumvalueDO model = new PDTAttributeEnumvalueDO();
				model.setAttributeDefId(attr4Enum.getId());
				model.setObjecttext(objectValue);
				model.setValueState(AttributeState.Active.getCode());
				if (sort == null) {
					Integer max = pdtAttributeEnumvalueDOMapper.selectMaxSortValueByAttributeDefId(attr4Enum.getId());
					if (max == null) {
						max = Constants.SYS_DEFAULT_SORT;
					}
					model.setSort(max);
				}else{
					model.setSort(sort);
				}
				pdtAttributeEnumvalueDOMapper.insert(model);
			}
		});
		return (Attr4Enum)this.refreshAttribute(attr4Enum.getId());
	}
	
	@Override
	public Attr4Enum updateAttr4EnumValue(final Attr4EnumValue attr4EnumValue) throws ObjectDuplicateException {
		PDTAttributeEnumvalueDO tempModel = this.pdtAttributeEnumvalueDOMapper.selectByAttributeDefId_AttributeName_NotSelf(attr4EnumValue.getAttr4Enum().getId()	, attr4EnumValue.getAttr4Enum().getAttributeName(), attr4EnumValue.getId());
		if (tempModel != null) {
			throw new ObjectDuplicateException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTAttributeEnumvalueDO model = new PDTAttributeEnumvalueDO();
				model.setId(attr4EnumValue.getId());
				model.setObjecttext(attr4EnumValue.getObjectText());
				model.setSort(attr4EnumValue.getSort());
				pdtAttributeEnumvalueDOMapper.updateByPrimaryKeySelective(model);
			}
		});
		return null;
	}
	
	@Override
	public AbstractAttribute refreshAttribute(int attributeId) {
		final String key = PDCache.getAttributeKey(attributeId);
		this.redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallbackWithoutResult() {
			@Override
			public void doInRedisWithoutResult(Jedis jedis) {
				jedis.del(key);
			}
		});
		LOG.info("Refresh Attribute {}", attributeId);
		return this.getAttribute(attributeId);
	}
	
	@Override
	public Attr4EnumValue getAttr4EnumValue(final int enumValueID) {
		final String key = PDCache.getEnumAttributeValueKey(enumValueID);
		final ServiceUtil<Attr4EnumValue, Integer> serviceUtil = new ServiceUtil<Attr4EnumValue, Integer>();
		Attr4EnumValue loadAttr4EnumValue = serviceUtil.getObjectFromRedisByKey(this.redisTemplate, JupinRedisRef.Jupin_Core, Attr4EnumValue.class, key);
		if (loadAttr4EnumValue != null) {
			return loadAttr4EnumValue;
		}
		//从DB中获取
		return ZKUtil.executeMutex(key, new InterProcessMutexCallback<Attr4EnumValue>() {
			@Override
			public Attr4EnumValue doInAcquireMutex(CuratorFramework curatorFramework) {
				//获取到锁，执行操作
				PDTAttributeEnumvalueDO valueModel = pdtAttributeEnumvalueDOMapper.selectByPrimaryKey(enumValueID);
				if (valueModel == null) {
					return null;
				}
				Attr4Enum attr4Enum = (Attr4Enum)getAttribute(enumValueID);
				Attr4EnumValue attr4EnumValue = new Attr4EnumValue();
				attr4EnumValue.setAttr4Enum(attr4Enum);
				attr4EnumValue.setId(valueModel.getId());
				attr4EnumValue.setObjectText(valueModel.getObjecttext());
				attr4EnumValue.setSort(valueModel.getSort());
				
				serviceUtil.setObjectToRedis(redisTemplate, JupinRedisRef.Jupin_Core, key, attr4EnumValue);
				return attr4EnumValue;
			}
			@Override
			public Attr4EnumValue doInNotAcquireMutex(CuratorFramework curatorFramework) {
				try {
					TimeUnit.SECONDS.sleep(Constants.ZK_BUSINESS_EXECUTE_WAITING_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return getAttr4EnumValue(enumValueID);
			}
		});
	}
	
	@Override
	public void deleteAttr4EnumValue(final int enumValueId) {
		final Attr4EnumValue attr4EnumValue = this.getAttr4EnumValue(enumValueId);
		if (attr4EnumValue == null) {
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtAttributeEnumvalueDOMapper.deleteByPrimaryKey(enumValueId);
				if (attr4EnumValue.getAttr4Enum().getValues().size() == 1) {
					pdtAttributeDefDOMapper.deleteByPrimaryKey(attr4EnumValue.getAttr4Enum().getId());
				}
			}
		});
	}
	
	@Override
	public Attr4Enum updateBaseAttr4Enum(final Attr4Enum attr4Enum) {
		final Attr4Enum loadAttr4Enum = (Attr4Enum)this.getAttribute(attr4Enum.getId());
		if (loadAttr4Enum == null) {
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				loadAttr4Enum.setId(attr4Enum.getId());
				loadAttr4Enum.setAttributeName(attr4Enum.getAttributeName());
				loadAttr4Enum.setAttributeDescription(attr4Enum.getAttributeDescription());
				loadAttr4Enum.setListshowable(attr4Enum.isListshowable());
				loadAttr4Enum.setRequiredable(attr4Enum.isRequiredable());
				loadAttr4Enum.setMultiselectable(attr4Enum.isMultiselectable());
				loadAttr4Enum.setSearchable(attr4Enum.isSearchable());
				PDTAttributeDefDO model = new PDTAttributeDefDO();
				model.setId(loadAttr4Enum.getId());
				model.setAttributeName(loadAttr4Enum.getAttributeName());
				model.setAttributeDescription(loadAttr4Enum.getAttributeDescription());
				model.setAttributeType(loadAttr4Enum.getAttributeType().getCode());
				model.setListshowable(BoolUtil.parseBoolean(loadAttr4Enum.isListshowable()));
				model.setRequiredable(BoolUtil.parseBoolean(loadAttr4Enum.isRequiredable()));
				model.setMultiselectable(BoolUtil.parseBoolean(loadAttr4Enum.isMultiselectable()));
				model.setSearchable(BoolUtil.parseBoolean(loadAttr4Enum.isSearchable()));
				pdtAttributeDefDOMapper.updateByPrimaryKeyWithBLOBs(model);
			}
		});
		return (Attr4Enum)this.refreshAttribute(loadAttr4Enum.getId());
	}
	
	@Override
	public Map<Integer, AbstractAttribute> getAttributesByIDsMap(final Collection<Integer> ids) {
		if (ids == null || ids.size() <=0) {
			return null;
		}
		Map<Integer, AbstractAttribute> dataMap = this.redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallback<Map<Integer, AbstractAttribute>>() {
			@Override
			public Map<Integer, AbstractAttribute> doInRedis(Jedis jedis) {
				List<String> keies = new ArrayList<String>();
				for (Integer id : ids) {
					keies.add(PDCache.getAttributeKey(id));
				}
				List<String> values = jedis.mget(keies.toArray(new String[keies.size()]));
				Map<Integer, AbstractAttribute> dataMap = new LinkedHashMap<Integer, AbstractAttribute>();
				int index = 0;
				for (Integer id : ids) {
					String value = values.get(index);
					AbstractAttribute attribute = null;
					if (value == null || value.equals("")) {
						attribute = getAttribute(id);
					}else{
						Gson gson = GsonCreator.create();
						JsonObject jsonObject = gson.fromJson(value, JsonObject.class);
						String attributeType =jsonObject.get("attributeType").getAsString();
						if (attributeType.equalsIgnoreCase(AttributeType.Text.toString())) {
							//text type
							attribute = gson.fromJson(jsonObject, Attr4Text.class);
						}else{
							//enum type
							attribute = gson.fromJson(jsonObject, Attr4Enum.class);
						}
					}
					dataMap.put(id, attribute);
					index++;
				}
				return dataMap;
			}
		});
		return dataMap;
	}
	
}
