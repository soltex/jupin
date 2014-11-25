package com.vanstone.jupin.productdefine.services.impl;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.business.serialize.GsonCreator;
import com.vanstone.jupin.productdefine.attr.sku.Color;
import com.vanstone.jupin.productdefine.services.ColorTableService;

@ContextConfiguration(locations = { 
		"classpath*:/core-context.xml",
		"classpath*:META-INF/jupin-*-context-module.xml" }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class ColorTableServiceImplTest {

	@Autowired
	private ColorTableService colorTableService;
	
	@Test
	public void testAddColor() {
		
		Color c1 = new Color();
		c1.setColorName("蓝色");
		Color c2 = new Color();
		c2.setColorName("绿色");
		Color c3 = new Color();
		c3.setColorName("青色");
		Color c4 = new Color();
		c4.setColorName("黄色");
		Color c5 = new Color();
		c5.setColorName("白色");
		Color c6 = new Color();
		c6.setColorName("浅黄色");
		Color c7 = new Color();
		c7.setColorName("浅绿色");
		Color c8 = new Color();
		c8.setColorName("红色");
		
		try {
			colorTableService.addColor(c1);
			colorTableService.addColor(c2);
			colorTableService.addColor(c3);
			colorTableService.addColor(c4);
			colorTableService.addColor(c5);
			colorTableService.addColor(c6);
			colorTableService.addColor(c7);
			colorTableService.addColor(c8);
		} catch (ObjectDuplicateException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateColor() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetColor() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetColorAndValidate() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteColor() {
		fail("Not yet implemented");
	}

	@Test
	public void testForceDeleteColor() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetColors() {
		Collection<Color> colors = this.colorTableService.getColors();
		Gson gson = GsonCreator.createPretty();
		System.out.println(gson.toJson(colors));
	}

	@Test
	public void testRefreshColorTable() {
		fail("Not yet implemented");
	}

}
