package com.vanstone.jupin.example.product.services.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vanstone.jupin.example.product.Product;
import com.vanstone.jupin.example.product.services.IProductService;
import com.vanstone.jupin.framework.common.ImageObject;
import com.vanstone.jupin.framework.productdef.AbstractAttribute;
import com.vanstone.jupin.framework.productdef.Attr4Enum;
import com.vanstone.jupin.framework.productdef.Attr4EnumValue;
import com.vanstone.jupin.framework.productdef.Attr4Lang;
import com.vanstone.jupin.framework.productdef.Attr4LangValue;
import com.vanstone.jupin.framework.productdef.EnumValue;
import com.vanstone.jupin.framework.productdef.services.IProductDefService;
import com.vanstone.weedfs.client.WeedFile;

@ContextConfiguration(locations = { 
		"classpath*:/application-context.xml",
		"classpath*:META-INF/vanstone-jupin**context-component.xml" }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductServiceImplTest {

	@Autowired
	private IProductService productService;
	@Autowired
	private IProductDefService productDefService;
	
	@Test
	public void testAddProduct() {
		Product product = new Product();
		product.setProductCategory(this.productDefService.getProductCategory(703));
		
		product.setProductName("苹果（APPLE）iPhone 4S 8G版 3G手机（白色）WCDMA/GSM");
		product.setSubProductName("iPhone中的经典，值得拥有！全新升级的iOS7操作系统！");
		product.setSkuNO("00000001");
		product.setBrief(",已经有了多种传闻显示iPhone 6或许将于今年的9月9日发布。而就在昨天,法国媒体提前曝光的iPhone 6内盒的一张内页,再次暗示了这一时间");
		product.setContent("继配置、谍照相继曝光后，2014年3月外媒又曝光了苹果iPhone6的最新消息，目前富士康已经接到了高达9000万部苹果iPhone6的生产订单。并且有4.7英寸和5.5英寸两种屏幕，4.7英寸的... ");
		product.setMarketPrice(10000);
		product.setPrice(2000);
		product.setPackingList("iPhone 4s × 1 带遥控功能和麦克风的 Apple 耳机 × 1 Dock Connector to USB 线缆 × 1 USB 电源转换器 × 1 包装、说明书、保修卡*1");
		product.setAfterSaleInfo("本产品全国联保，享受三包服务，质保期为：一年质保");
		product.setInventory(100);
		WeedFile wf1 = new WeedFile("123123123123", "jpg");
		ImageObject imageObject1 = new ImageObject();
		imageObject1.setImageWeedFile(wf1);
		imageObject1.setWidth(1000);
		imageObject1.setHeight(200);
		
		WeedFile wf2 = new WeedFile("sdfsdfsdf", "jpg");
		ImageObject imageObject2 = new ImageObject();
		imageObject2.setImageWeedFile(wf2);
		imageObject2.setWidth(3000);
		imageObject2.setHeight(123);
		
		product.addImageObject(imageObject1);
		product.addImageObject(imageObject2);
		
		AbstractAttribute shangpinmaozhongAttribute1 = this.productDefService.getAttribute(25);
		AbstractAttribute tesegongnengAttribute1 = this.productDefService.getAttribute(26);
		AbstractAttribute xinghaobanbenAttribute1 = this.productDefService.getAttribute(27);
		
		Attr4Lang shangpinmaozhongAttribute = (Attr4Lang)shangpinmaozhongAttribute1;
		Attr4Lang tesegongnengAttribute = (Attr4Lang)tesegongnengAttribute1;
		Attr4Enum xinghaobanbenAttribute = (Attr4Enum)xinghaobanbenAttribute1;
		
		Attr4LangValue shangpinmiaoshuValue = new Attr4LangValue();
		shangpinmiaoshuValue.setValue("呵呵呵呵呵呵,你说呢，商品描述啦,操");
		
		Attr4LangValue tesegongnengValue = new Attr4LangValue();
		tesegongnengValue.setValue("如厂家在商品介绍中有售后保障的说明,则此商品按照厂家说明执行售后保障服务");
		
		Attr4EnumValue xinghaobanbenValue = new Attr4EnumValue();
		EnumValue ev = new EnumValue();
		ev.setId(35);
		ev.setObjectValue("i7-4710");
		ev.setSort(0);
		
		xinghaobanbenValue.setEnumValue(ev);
		xinghaobanbenValue.setAliasObjectValue("NB_2014_lenovo");
		
		product.addProductProperty(shangpinmaozhongAttribute, shangpinmiaoshuValue);
		product.addProductProperty(tesegongnengAttribute, tesegongnengValue);
		product.addProductProperty(xinghaobanbenAttribute, xinghaobanbenValue);
		
		this.productService.addProduct(product);
		
	}

	@Test
	public void testgetProduct() {
		String id = "P7NdfuexafWgpk6AEkBUne";
		this.productService.getProduct(id);
	}
}
