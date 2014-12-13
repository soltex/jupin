/**
 * 
 */
package com.vanstone.jupin.admin.webapp.pdm;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vanstone.fs.FSException;
import com.vanstone.fs.FSFile;
import com.vanstone.fs.FSType;
import com.vanstone.jupin.admin.webapp.AdminBaseAction;
import com.vanstone.jupin.business.sdk.adminservice.pdm.DefineManager;
import com.vanstone.jupin.business.sdk.common.CommonSDKManager;
import com.vanstone.jupin.common.ImageFormatException;
import com.vanstone.jupin.common.util.UploadUtil;
import com.vanstone.jupin.ecs.product.define.ProductCategoryDetail;
import com.vanstone.jupin.ecs.product.define.attribute.sku.SizeTemplate;
import com.vanstone.jupin.ecs.product.define.services.AttributeService;
import com.vanstone.jupin.ecs.product.define.services.ExistProductsNotAllowWriteException;
import com.vanstone.jupin.ecs.product.define.services.ProductCategoryService;
import com.vanstone.jupin.ecs.product.define.services.SizeService;
import com.vanstone.webframework.dwz.ViewCommandHelper;
import com.vanstone.webframework.dwz.ViewCommandObject;
import com.vanstone.webframework.utils.FSManagerExt;

/**
 * @author shipeng
 */
@Controller("productCategoryAction")
@RequestMapping("/pdm")
public class ProductCategoryAction extends AdminBaseAction {
	
	@Autowired
	private CommonSDKManager commonSDKManager;
	@Autowired
	private ProductCategoryService categoryService;
	@Autowired
	private AttributeService attributeService;
	@Autowired
	private DefineManager defineManager;
	@Autowired
	private SizeService sizeService;
	
	@RequestMapping("/add-productcategory")
	public String addProductCategory(@ModelAttribute("productCategoryForm")ProductCategoryForm productCategoryForm, ModelMap modelMap) {
		Collection<ProductCategoryDetail> productCategoryDetailsOfRoot = this.categoryService.getProductCategoriesOfLevel1();
		Collection<SizeTemplate> sizeTemplates = this.sizeService.getSizeTemplates();
		
		modelMap.put("productCategoryDetailsOfRoot", productCategoryDetailsOfRoot);
		modelMap.put("sizeTemplates", sizeTemplates);
		
		return "/pdm/add-productcategory";
	}

	private Integer parseParentProductCategoryID(ProductCategoryForm productCategoryForm) {
		if (productCategoryForm == null) {
			return null;
		}
		if (productCategoryForm.getLevel6() != null) {
			return productCategoryForm.getLevel6();
		}
		if (productCategoryForm.getLevel5() != null) {
			return productCategoryForm.getLevel5();
		}
		if (productCategoryForm.getLevel4() != null) {
			return productCategoryForm.getLevel4();
		}
		if (productCategoryForm.getLevel3() != null) {
			return productCategoryForm.getLevel3();
		}
		if (productCategoryForm.getLevel2() != null) {
			return productCategoryForm.getLevel2();
		}
		if (productCategoryForm.getLevel1() != null) {
			return productCategoryForm.getLevel1();
		}
		return null;
	}
	
	@RequestMapping("add-productcategory-action")
	@ResponseBody
	public ViewCommandObject addProductCategoryAction(@ModelAttribute("productCategoryForm")ProductCategoryForm productCategoryForm, ModelMap modelMap) {
		
		FSFile coverFSFile = null;
		if (UploadUtil.multipartFileExist(productCategoryForm.getCoverImageFile())) {
			try {
				coverFSFile = FSManagerExt.getInstance().uploadBySpring(productCategoryForm.getCoverImageFile(), FSType.Temporary);
			} catch (FSException e) {
				ViewCommandObject viewCommandObject = ViewCommandHelper.createErrorObject("图片文件格式错误，请重新选择！");
				return viewCommandObject;
			}
		}
		try {
			this.defineManager.addProductCategoryDetail(parseParentProductCategoryID(productCategoryForm), productCategoryForm.getCategoryName(), productCategoryForm.getDescription(), coverFSFile, productCategoryForm.getSort(), productCategoryForm.isSkuColor(), productCategoryForm.getSizeTemplateId(), productCategoryForm.getAttributeIds());
		} catch (ImageFormatException e) {
			ViewCommandObject viewCommandObject = ViewCommandHelper.createErrorObject("图片文件格式错误，请重新选择！");
			return viewCommandObject;
		} catch (ExistProductsNotAllowWriteException e) {
			ViewCommandObject viewCommandObject = ViewCommandHelper.createErrorObject("当前品类信息不允许操作，如强制操作，请联系管理员！");
			return viewCommandObject;
		}
		ViewCommandObject commandObject = ViewCommandHelper.createSuccessObject("添加品类信息成功！");
		//commandObject.setForwardUrl("/pdm/search-productcategories");
		return commandObject;
	}
	
	@RequestMapping("/search-productcategories") 
	public String searchProductCategories(@ModelAttribute("productCategoryForm")ProductCategoryForm productCategoryForm, ModelMap modelMap) {
		return "/pdm/search-productcategories";
	}
	
	@RequestMapping("/view-productcategory-baseinfo/{productCategoryID}")
	public String viewProductCategoryBaseInfo(@PathVariable("productCategoryID")int id, @ModelAttribute("productCategoryForm")ProductCategoryForm productCategoryForm, ModelMap modelMap) {
		
		return null;
	}
	
	@RequestMapping("/view-productcategory-cover/{productCategoryID}")
	public String viewProductCategoryCover(@PathVariable("productCategoryID")int id, @ModelAttribute("productCategoryForm")ProductCategoryForm productCategoryForm, ModelMap modelMap) {
		
		return null;
	}
	
	@RequestMapping("/view-productcategory-attributes/{productCategoryID}")
	public String viewProductCategoryAttributes(@PathVariable("productCategoryID")int id, @ModelAttribute("productCategoryForm")ProductCategoryForm productCategoryForm, ModelMap modelMap) {
		
		return null;
	}
	
	@RequestMapping("/view-productcategory-brands/{productCategoryID}")
	public String viewProductCategoryBrands(@PathVariable("productCategoryID")int id, @ModelAttribute("productCategoryForm")ProductCategoryForm productCategoryForm, ModelMap modelMap) {
		
		return null;
	}
	
	@RequestMapping("/view-productcategory-parent/{productCategoryID}")
	public String viewProductCategoryParent(@PathVariable("productCategoryID")int id, @ModelAttribute("productCategoryForm")ProductCategoryForm productCategoryForm, ModelMap modelMap) {
		
		return null;
	}
	
	@RequestMapping("/delete-productcategory-action/{productCategoryID}")
	@ResponseBody
	public ViewCommandObject deleteProductCategoryAction(@PathVariable("productCategoryID")int id) {
		ViewCommandObject commandObject = ViewCommandHelper.createSuccessObject("添加品类信息成功！");
		return commandObject; 
	}
	
	@RequestMapping("/select-byparentid")
	public String selectByParentId(@RequestParam("parentid")Integer parentId, @RequestParam("no")Integer no, ModelMap modelMap) {
		ProductCategoryDetail detail = this.commonSDKManager.getProductCategoryDetailAndValidate(parentId);
		modelMap.put("productCategoryDetail", detail);
		modelMap.put("no", no);
		return "/pdm/select-byparentid";
	}
}
