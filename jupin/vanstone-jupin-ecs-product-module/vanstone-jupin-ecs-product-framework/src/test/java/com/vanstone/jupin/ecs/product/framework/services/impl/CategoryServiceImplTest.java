package com.vanstone.jupin.ecs.product.framework.services.impl;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vanstone.jupin.ecs.product.define.attribute.AbstractAttribute;
import com.vanstone.jupin.ecs.product.define.services.AttributeService;

@ContextConfiguration(locations = { 
		"classpath*:/core-context.xml",
		"classpath*:META-INF/jupin**context-module.xml" }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class CategoryServiceImplTest {

	@Test
	public void testAddProductCategoryProductCategory() {
	}

	@Test
	public void testAddProductCategoryProductCategoryCollectionOfAbstractAttribute() {
	}
	
	@Test
	public void testGetProductCategoryDetail() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProductCategoryDetailAndValidate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProductCategoriesOfLevel1() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateBaseProductCategoryInfo() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateParentProductCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateProductCategoryCoverImage() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteProductCategoryCoverImage() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteProductCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddAttributesToProductCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddAttributeToProductCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testAttributeExistInProductCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testAttributesExistInProductCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteAttributeInProductCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testRefreshProductCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateExistProductState() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProductCategories() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTotalProductCategories() {
		fail("Not yet implemented");
	}

	@Test
	public void testValidateAllowUDOperateCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProductCategoriesMap() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAttribute() {
		int id = 35;
		AbstractAttribute attribute = this.attributeService.getAttribute(id);
		System.out.println(attribute);
	}
	
	@Autowired
	private AttributeService attributeService;
	
}
