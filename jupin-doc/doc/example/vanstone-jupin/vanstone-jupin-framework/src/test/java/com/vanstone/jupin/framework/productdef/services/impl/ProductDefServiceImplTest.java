package com.vanstone.jupin.framework.productdef.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;
import com.vanstone.business.serialize.GsonCreator;
import com.vanstone.jupin.framework.JupinGsonCreator;
import com.vanstone.jupin.framework.cache.JupinRedisCache;
import com.vanstone.jupin.framework.productdef.AbstractAttribute;
import com.vanstone.jupin.framework.productdef.Attr4Enum;
import com.vanstone.jupin.framework.productdef.Attr4Lang;
import com.vanstone.jupin.framework.productdef.AttributeScope;
import com.vanstone.jupin.framework.productdef.EnumValue;
import com.vanstone.jupin.framework.productdef.ProductCategory;
import com.vanstone.jupin.framework.productdef.services.IProductDefService;
import com.vanstone.redis.RedisCallback;
import com.vanstone.redis.RedisTemplate;

@ContextConfiguration(locations = { 
		"classpath*:/application-context.xml",
		"classpath*:META-INF/vanstone-jupin**context-component.xml" }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductDefServiceImplTest extends TestCase {
	
	@Autowired
	private IProductDefService productDefService;
	@Autowired
	private RedisTemplate redisTemplate;
	
	private static String[] dajiadian = new String[] {
		"大 家 电,平板电视,空调,冰箱,洗衣机,家庭影院,DVD,迷你音响,烟机/灶具,热水器,消毒柜/洗碗机,酒柜/冷柜,家电配件",
		"生活电器,电风扇,冷风扇,净化器,饮水机,净水设备,挂烫机/熨斗,吸尘器,电话机,插座,收录/音机,清洁机,加湿器,除湿/干衣机,取暖电器,其它生活电器",
		"厨房电器,电压力锅,电饭煲,豆浆机,面包机,咖啡机,微波炉,料理/榨汁机,电烤箱,电磁炉,电饼铛/烧烤盘,煮蛋器,酸奶机,电水壶/热水瓶,电炖锅,多用途锅,果蔬解毒机,其它厨房电器",
		"个护健康,剃须刀,剃/脱毛器,口腔护理,电吹风,美容美发,按摩器,按摩椅,足浴盆,血压计,健康秤/厨房秤,血糖仪,体温计,计步器/脂肪检测仪,其它健康电器",
		"五金家装,电动工具,手动工具,仪器仪表,浴霸/排气扇,灯具,LED灯,洁身器,水槽龙头,淋浴花洒,厨卫,五金家具,五金门铃,电气开关,插座,电工,电料,监控,安防,电线/线缆"
	};
	
	@Test
	public void testAddProductCategory() {
		for (String str : dajiadian) {
			String[] categoryNames = StringUtils.split(str, ",");
			ProductCategory pc1 = new ProductCategory();
			pc1.setCategoryName(categoryNames[0]);
			this.productDefService.addProductCategory(pc1, null);
			
			int length = categoryNames.length;
			for (int i=1;i<length;i++) {
				ProductCategory pc = new ProductCategory();
				pc.setCategoryName(categoryNames[i]);
				pc.setParentProductCategory(pc1);
				this.productDefService.addProductCategory(pc, null);
			}
		}
	}
	
	@Test
	public void addProductCategoryInstance() {
		ProductCategory pc1 = new ProductCategory();
		pc1.setCategoryName("电脑、办公");
		pc1 = this.productDefService.addProductCategory(pc1, null);
		
		Attr4Lang shangpinmaozhongAttr4Lang = new Attr4Lang(AttributeScope.Product);
		shangpinmaozhongAttr4Lang.setAttributeName("商品毛重");
		
		Attr4Lang tesegongenngAttr4Lang = new Attr4Lang(AttributeScope.Product);
		tesegongenngAttr4Lang.setAttributeName("特色功能");
		
		ProductCategory pc2 = new ProductCategory();
		pc2.setParentProductCategory(pc1);
		pc2.setCategoryName("商品整机");
		Collection<AbstractAttribute> aas = new ArrayList<AbstractAttribute>();
		aas.add(shangpinmaozhongAttr4Lang);
		aas.add(tesegongenngAttr4Lang);
		
		pc2 = this.productDefService.addProductCategory(pc2, aas);
		
		ProductCategory pc3 = new ProductCategory();
		pc3.setParentProductCategory(pc2);
		pc3.setCategoryName("游戏本");
		
		Attr4Lang xiankaAttr4Lang = new Attr4Lang(AttributeScope.Product);
		xiankaAttr4Lang.setAttributeName("显卡");
		
		Attr4Lang cpuAttr4Lang = new Attr4Lang(AttributeScope.Product);
		cpuAttr4Lang.setAttributeName("CPU");
		
		Attr4Lang yingpanAttr4Lang = new Attr4Lang(AttributeScope.Product);
		yingpanAttr4Lang.setAttributeName("硬盘");
		
		Attr4Enum banbenAttr4Enum = new Attr4Enum(AttributeScope.Sku);
		banbenAttr4Enum.setAttributeName("型号版本");
		
		EnumValue i54210EnumValue = new EnumValue();
		i54210EnumValue.setObjectValue("i5-4210");
		
		banbenAttr4Enum.addEnumValue(i54210EnumValue);
		
		EnumValue i74710EnumValue = new EnumValue();
		i74710EnumValue.setObjectValue("i7-4710");
		
		banbenAttr4Enum.addEnumValue(i74710EnumValue);
		
		List<AbstractAttribute> pc3Attributes = new ArrayList<AbstractAttribute>();
		pc3Attributes.add(banbenAttr4Enum);
		
		this.productDefService.addProductCategory(pc3, pc3Attributes);
	}
	
	@Test
	public void testinitDefaultProductCategory() {
		this.productDefService.initDefaultProductCategory();
	}
	
	@Test
	public void testUpdateBaseProductCategoryInfo() {
		this.redisTemplate.executeInRedis(JupinRedisCache.jupin, new RedisCallback<ProductCategory>() {

			@Override
			public ProductCategory doInRedis(Jedis jedis) {
				String value = jedis.get("jupin.productdef.category.id_703");
				System.out.println(value);
				Gson gson = JupinGsonCreator.createGson4Attribute();
				ProductCategory pc = gson.fromJson(value, ProductCategory.class);
				System.out.println(pc.getCategoryName());
				
				Gson gson1 = JupinGsonCreator.createPretty();
				System.out.println(gson1.toJson(pc));
				
				return null;
			}
			
		});
	}
	
	public void testUpdateCoverImageOfProductCategory() {
		fail("Not yet implemented");
	}

	public void testUpdateProductCategoryAttributes() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProductCategory() {
		int cid =703;
		ProductCategory pc = this.productDefService.getProductCategory(cid);
		System.out.println(GsonCreator.getPrettyString(pc));
	}
	
	@Test
	public void testRefreshAllProductCategories() {
		this.productDefService.refreshAllProductCategories();
	}
	
	@Test
	public void testsearchAttributes() {
		Collection<AbstractAttribute> attributes = this.productDefService.searchAttributes("21", 0);
		for (AbstractAttribute attribute : attributes) {
			System.out.println("id : " + attribute.getId() + " : " + attribute.getAttributeName());
		}
	}
	
	@Test
	public void testAddAttribute() {
//		Attr4Lang attr4Lang = new Attr4Lang(AttributeScope.Product);
//		attr4Lang.setAttributeName("产地");
//		attr4Lang.setDescription("产品生产产地");
//		this.productDefService.addAttribute(attr4Lang);
		
		Attr4Enum colorAttr4Enum = new Attr4Enum(AttributeScope.Sku);
		colorAttr4Enum.setAttributeName("颜色");
		colorAttr4Enum.setDescription("商品颜色");
		colorAttr4Enum.setSearchable(true);
		
		String[] colors = new String[] {
				"鸨色",
				"赤白橡",
				"油色",
				"绀桔梗",
				"踯躅色",
				"肌色",
				"伽罗色",
				"花色",
				"桜色",
				"橙色",
				"青丹",
				"瑠璃色"
		};
		for (String color : colors) {
			EnumValue ev = new EnumValue();
			ev.setObjectValue(color);
			colorAttr4Enum.addEnumValue(ev);
		}
		this.productDefService.addAttribute(colorAttr4Enum);
	}
	
	@Test
	public void testGetAttribute() {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for (int i=0;i<10000;i++) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					int id = 4;
					for (int i=0;i<100;i++) {
						AbstractAttribute attribute = productDefService.getAttribute(id);
						System.out.println(GsonCreator.getPrettyString(attribute));
					}
				}
			});
		}
		try {
			TimeUnit.SECONDS.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void testUpdateBaseAttribute() {
		fail("Not yet implemented");
	}

	public void testDeleteAttribute() {
		fail("Not yet implemented");
	}

	public void testAddEnumValue() {
		fail("Not yet implemented");
	}

	public void testDeleteEnumValue() {
		fail("Not yet implemented");
	}

	public void testUpdateEnumValue() {
		fail("Not yet implemented");
	}

}
