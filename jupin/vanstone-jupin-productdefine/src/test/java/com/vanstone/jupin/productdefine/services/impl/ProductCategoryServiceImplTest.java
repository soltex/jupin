package com.vanstone.jupin.productdefine.services.impl;

import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.business.serialize.GsonCreator;
import com.vanstone.jupin.productdefine.ProductCategory;
import com.vanstone.jupin.productdefine.attr.AbstractAttribute;
import com.vanstone.jupin.productdefine.attr.Attr4Enum;
import com.vanstone.jupin.productdefine.attr.Attr4Text;
import com.vanstone.jupin.productdefine.attr.AttributeBuilder;
import com.vanstone.jupin.productdefine.serializer.GsonCreatorOfProductDefine;
import com.vanstone.jupin.productdefine.services.ExistProductsNotAllowWriteException;
import com.vanstone.jupin.productdefine.services.CategoryHasChildCategoriesException;
import com.vanstone.jupin.productdefine.services.CategoryService;

@ContextConfiguration(locations = { 
		"classpath*:/core-context.xml",
		"classpath*:META-INF/jupin-*-context-module.xml" }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductCategoryServiceImplTest {

	@Autowired
	private CategoryService productCategoryService;
	
	@Test
	public void testAddProductCategoryProductCategory() {
		
		ProductCategory parentProductCategory = this.productCategoryService.getProductCategoryDetail(1);
		ProductCategory pc = new ProductCategory();
		pc.setParentProductCategory(parentProductCategory);
		pc.setCategoryName("女装");
		try {
			productCategoryService.addProductCategory(pc);
		} catch (ExistProductsNotAllowWriteException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddProductCategoryProductCategoryCollectionOfAbstractAttribute() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetProductCategoryDetail() {
		ProductCategory category = this.productCategoryService.getProductCategoryDetail(4);
		Gson gson = GsonCreator.createPretty();
		System.out.println(gson.toJson(category));
	}

	@Test
	public void testgetProductCategoriesOfLevel1() {
		Collection<ProductCategory> pcs = this.productCategoryService.getProductCategoriesOfLevel1();
		Gson gson = GsonCreatorOfProductDefine.create();
		System.out.println(gson.toJson(pcs));
	}
	
	@Test
	public void testGetProductCategoryDetailAndValidate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRootProductCategoryDetail() {
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
		AbstractAttribute attribute1 = this.productCategoryService.getAttribute(1);
		AbstractAttribute attribute2 = this.productCategoryService.getAttribute(2);
		AbstractAttribute attribute3 = this.productCategoryService.getAttribute(3);
		
		ProductCategory productCategory = this.productCategoryService.getProductCategoryDetail(4);
		try {
			this.productCategoryService.addAttributeToProductCategory(productCategory.getId(), attribute1);
			this.productCategoryService.addAttributeToProductCategory(productCategory.getId(), attribute2);
			this.productCategoryService.addAttributeToProductCategory(productCategory.getId(), attribute3);
		} catch (CategoryHasChildCategoriesException e) {
			e.printStackTrace();
		} catch (ObjectDuplicateException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddAttributeToProductCategory() {
		
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
	public void testForceDeleteProductCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testClearAllProductCategoriesInCache() {
		fail("Not yet implemented");
	}

	@Test
	public void testRefreshProductCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testRefreshRootProductCategory() {
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
	public void testAddAttr4Text() {
		Attr4Text attr4Text = AttributeBuilder.newText("产地", true, true);
		this.productCategoryService.addAttr4Text(attr4Text);
		
		Attr4Text attr4Text2 = AttributeBuilder.newText("净重", false, true);
		this.productCategoryService.addAttr4Text(attr4Text2);
	}

	@Test
	public void testAddAttr4Enum() {
		Attr4Enum attr4Enum1 = AttributeBuilder.newBaseEnum("材质", true, true, true, false);
		Set<String> values1 = new LinkedHashSet<String>();
		values1.add("纯棉");
		values1.add("棉纱");
		values1.add("丝棉");
		values1.add("纸面");
		this.productCategoryService.addAttr4Enum(attr4Enum1, values1);
	}

	@Test
	public void testUpdateAttr4Text() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAttribute() {
		Gson gson = GsonCreator.create();
		AbstractAttribute attr1 = this.productCategoryService.getAttribute(1);
		System.out.println(gson.toJson(attr1));
		
		AbstractAttribute attr2 = this.productCategoryService.getAttribute(2);
		System.out.println(gson.toJson(attr2));
		
		AbstractAttribute attr3 = this.productCategoryService.getAttribute(3);
		System.out.println(gson.toJson(attr3));
		
	}

	@Test
	public void testAppendAttr4EnumValue() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testUpdateAttr4EnumValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testRefreshAttribute() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAttr4EnumValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteAttr4EnumValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateBaseAttr4Enum() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAttributesByIDsMap() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBrands() {
		fail("Not yet implemented");
	}

}
