package com.vanstone.jupin.ebs.pm.framework.services.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.business.ObjectHasSubObjectException;
import com.vanstone.business.serialize.GsonCreator;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.sku.Size;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.sku.SizeTemplate;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.sku.SizeTemplateWrapBean;
import com.vanstone.jupin.ebs.pm.productdefine.services.ExistProductsNotAllowWriteException;
import com.vanstone.jupin.ebs.pm.productdefine.services.SizeService;

@ContextConfiguration(locations = { 
		"classpath*:/core-context.xml",
		"classpath*:META-INF/jupin-*-context-module.xml" }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class SizeServiceImplTest {

	@Autowired
	private SizeService sizeService;
	
	@Test
	public void testAddSizeTemplate() {
		Collection<Size> sizes = new ArrayList<Size>();
		Size s1 = new Size();
		s1.setSizeName("MS");
		s1.setWeightable(true);
		s1.setWeightStart(147);
		s1.setWeightEnd(152);
		
		Size s2 = new Size();
		s2.setSizeName("XS");
		s2.setWeightable(true);
		s2.setWeightStart(152);
		s2.setWeightEnd(157);
		
		Size s3 = new Size();
		s3.setSizeName("XM");
		s3.setWeightable(true);
		s3.setWeightStart(157);
		s3.setWeightEnd(162);
		
		Size s4 = new Size();
		s4.setSizeName("XL");
		s4.setWeightable(true);
		s4.setWeightStart(162);
		s4.setWeightEnd(167);
		
		Size s5 = new Size();
		s5.setSizeName("XXL");
		s5.setWeightable(true);
		s5.setWeightStart(167);
		s5.setWeightEnd(172);
		
		sizes.add(s1);
		sizes.add(s2);
		sizes.add(s3);
		sizes.add(s4);
		sizes.add(s5);
		
		try {
			//体重,臀围,身高
			sizeService.addSizeTemplate("B品牌T恤", null, true, false, true, false, false, false, false, sizes);
		} catch (ObjectDuplicateException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateBaseSizeTemplateInfo() {
		
		SizeTemplate sizeTemplate = this.sizeService.getSizeTemplate(4);
		Gson gson = GsonCreator.createPretty();
		System.out.println(gson.toJson(sizeTemplate));
		
		try {
			this.sizeService.updateBaseSizeTemplateInfo(4, "体恤尺码", "体恤尺码体恤尺码");
		} catch (ExistProductsNotAllowWriteException e) {
			e.printStackTrace();
		} catch (ObjectHasSubObjectException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeleteSizeTemplate() {
		try {
			this.sizeService.deleteSizeTemplate(4);
		} catch (ExistProductsNotAllowWriteException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetSizeTemplates() {
		Collection<SizeTemplate> sizeTemplates = this.sizeService.getSizeTemplates();
		Gson gson = GsonCreator.createPretty();
		System.out.println(gson.toJson(sizeTemplates));
	}

	@Test
	public void testAddSize() {
		Size size = new Size(this.sizeService.getSizeTemplate(5));
		size.setSizeName("XXL");
		size.setWeightable(true);
		size.setWeightStart(172);
		size.setWeightEnd(190);
		try {
			size = sizeService.addSize(size);
			Gson gson = GsonCreator.createPretty();
			System.out.println(gson.toJson(size));
		} catch (ExistProductsNotAllowWriteException e) {
			e.printStackTrace();
		} catch (ObjectDuplicateException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateSize() {
		Size size = this.sizeService.getSize(26);
		size.setWeightEnd(123);
		size.setWeightStart(100);
		try {
			this.sizeService.updateSize(size);
		} catch (ExistProductsNotAllowWriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ObjectDuplicateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetSize() {
		for (int i=21;i<=31;i++) {
			Size size = this.sizeService.getSize(i);
			Gson gson = GsonCreator.createPretty();
			System.out.println(gson.toJson(size));
		}
	}
	
	@Test
	public void testDeleteSize() {
		try {
			this.sizeService.deleteSize(26);
		} catch (ExistProductsNotAllowWriteException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetSizeTemplatesWithStat() {
		Collection<SizeTemplateWrapBean> wrapBeans = this.sizeService.getSizeTemplatesWithStat();
		Gson gson = GsonCreator.createPretty();
		System.out.println(gson.toJson(wrapBeans));
	}

	@Test
	public void testGetSizeTemplate() {
		SizeTemplate sizeTemplate = this.sizeService.getSizeTemplate(5);
		Gson gson = GsonCreator.createPretty();
		System.out.println(gson.toJson(sizeTemplate));
	}

	@Test
	public void testRefreshSizeTables() {
	}
}
