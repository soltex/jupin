/**
 * 
 */
package com.vanstone.jupin.admin.webapp.pdm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.common.util.web.PageInfo;
import com.vanstone.fs.FSException;
import com.vanstone.fs.FSFile;
import com.vanstone.fs.FSType;
import com.vanstone.jupin.admin.webapp.AdminBaseAction;
import com.vanstone.jupin.business.sdk.adminservice.pdm.DefineManager;
import com.vanstone.jupin.business.sdk.adminservice.pdm.ImportBrandFileFormatException;
import com.vanstone.jupin.business.sdk.common.CommonSDKManager;
import com.vanstone.jupin.common.Constants;
import com.vanstone.jupin.common.ImageFormatException;
import com.vanstone.jupin.common.util.UploadUtil;
import com.vanstone.jupin.ecs.product.define.Brand;
import com.vanstone.jupin.ecs.product.define.services.BrandService;
import com.vanstone.jupin.ecs.product.define.services.ExistProductsNotAllowWriteException;
import com.vanstone.webframework.dwz.ViewCommandHelper;
import com.vanstone.webframework.dwz.ViewCommandObject;
import com.vanstone.webframework.utils.FSManagerExt;

/**
 * @author shipeng
 */
@RequestMapping("/pdm")
@Controller("brandAction")
public class BrandAction extends AdminBaseAction {
	
	@Autowired
	private BrandService brandService;
	@Autowired
	private DefineManager defineManager;
	@Autowired
	private CommonSDKManager commonSDKManager;
	
	@RequestMapping("/search-brands")
	public String searchBrands(@ModelAttribute("brandForm")BrandForm brandForm, ModelMap modelMap) {
		PageInfo<Brand> pageInfo = this.defineManager.searchBrands(brandForm.getCategoryID(), brandForm.getKey(), brandForm.getPageNum(), Constants.ADMIN_DEFAULT_PAGESIZE);
		modelMap.put("pageInfo", pageInfo);
		return "/pdm/search-brands";
	}
	
	@RequestMapping("/add-brand")
	public String addBrand(ModelMap modelMap, @ModelAttribute("brandForm")BrandForm brandForm) {
		return "/pdm/add-brand";
	}
	
	@RequestMapping("/add-brand-action")
	@ResponseBody
	public ViewCommandObject addBrandAction(@ModelAttribute("brandForm")BrandForm brandForm, ModelMap modelMap){
		FSFile logoFsFile = null;
		if (UploadUtil.multipartFileExist(brandForm.getLogoMultipartFile())) {
			try {
				logoFsFile = FSManagerExt.getInstance().uploadBySpring(brandForm.getLogoMultipartFile(), FSType.Temporary);
			} catch (FSException e) {
				e.printStackTrace();
				ViewCommandObject viewCommandObject = ViewCommandHelper.createErrorObject("图片文件格式错误，请联系管理员！");
				return viewCommandObject;
			}
		}
		try {
			defineManager.addBrand(brandForm.getBrandName(), brandForm.getBrandNameEN(), logoFsFile, brandForm.getContent());
		} catch (ImageFormatException e) {
			e.printStackTrace();
			ViewCommandObject viewCommandObject = ViewCommandHelper.createErrorObject("图片文件格式错误，请联系管理员！");
			return viewCommandObject;
		} catch (ObjectDuplicateException e) {
			ViewCommandObject viewCommandObject = ViewCommandHelper.createErrorObject("品牌信息以及存在，请在列表中直接查找！");
			return viewCommandObject;
		}
		ViewCommandObject object = ViewCommandHelper.createSuccessObject("品牌信息增加成功");
		object.setForwardUrl("/pdm/search-brands");
		return object;
	}
	
	@RequestMapping("/batch-add-brands")
	public String batchAddBrands(@ModelAttribute("brandForm")BrandForm brandForm, ModelMap modelMap) {
		return "/pdm/batch-add-brands";
	}
	
	@RequestMapping("/batch-add-brands-action")
	@ResponseBody
	public ViewCommandObject batchAddBrandsAction(@ModelAttribute("brandForm")BrandForm brandForm, ModelMap modelMap) {
		if (!UploadUtil.multipartFileExist(brandForm.getBatchImportFile())) {
			ViewCommandObject viewCommandObject = ViewCommandHelper.createErrorObject("请选择数据文件！");
			return viewCommandObject;
		}
		try {
			FSFile fsFile = FSManagerExt.getInstance().uploadBySpring(brandForm.getBatchImportFile(), FSType.Temporary);
			defineManager.batchImportBrands(fsFile,true);
			ViewCommandObject object = ViewCommandHelper.createSuccessObject("品牌信息正在异步导入，导入完成后，会通知您，请等待！");
			object.setForwardUrl("/pdm/search-brands");
			return object;
		} catch (FSException e) {
			ViewCommandObject viewCommandObject = ViewCommandHelper.createErrorObject("文件格式错误！");
			return viewCommandObject;
		} catch (ImportBrandFileFormatException e) {
			ViewCommandObject viewCommandObject = ViewCommandHelper.createErrorObject("数据文件错误，请检查！");
			return viewCommandObject;
		}
	}
	
	@RequestMapping("/delete-brand-action/{brandId}")
	@ResponseBody
	public ViewCommandObject deleteBrandAction(@PathVariable("brandId")Integer brandId,@RequestParam(value="p",required=false)Integer p) {
		Brand brand = this.commonSDKManager.getBrandAndValidate(brandId);
		try {
			this.brandService.deleteBrand(brandId);
		} catch (ExistProductsNotAllowWriteException e) {
			ViewCommandObject viewCommandObject = ViewCommandHelper.createErrorObject("品牌信息不允许删除，请联系管理员！");
			return viewCommandObject;
		}
		ViewCommandObject viewCommandObject = ViewCommandHelper.createSuccessObject("【" + brand.getBrandName() + "】品牌信息已被删除！");
		viewCommandObject.setForwardUrl("/pdm/search-brands");
		return viewCommandObject;
	}
	
	@RequestMapping("/view-base-brand/{brandId}")
	public String viewBaseBrand(@PathVariable("brandId")Integer brandId, @ModelAttribute("brandForm")BrandForm brandForm, ModelMap modelMap) {
		Brand brand = this.commonSDKManager.getBrandAndValidate(brandId);
		brandForm.setBrandName(brand.getBrandName());
		brandForm.setBrandNameEN(brand.getBrandNameEN());
		brandForm.setContent(brand.getContent());
		brandForm.setBrandId(brand.getId());
		
		modelMap.put("brand", brand);
		return "/pdm/view-base-brand";
	}
	
	@RequestMapping("/update-base-brand-action")
	@ResponseBody
	public ViewCommandObject updateBaseBrandAction(@ModelAttribute("brandForm")BrandForm brandForm, ModelMap modelMap) {
		try {
			this.brandService.updateBrandBaseInfo(brandForm.getBrandId(), brandForm.getBrandName(), brandForm.getBrandNameEN(), brandForm.getContent(), false);
		} catch (ExistProductsNotAllowWriteException e) {
			ViewCommandObject viewCommandObject = ViewCommandHelper.createErrorObject("当前品牌信息不允许修改，如需修改，请联系管理员！");
			return viewCommandObject;
		} catch (ObjectDuplicateException e) {
			ViewCommandObject viewCommandObject = ViewCommandHelper.createErrorObject("品牌信息以及存在，请在列表中直接查找！");
			return viewCommandObject;
		}
		ViewCommandObject object = ViewCommandHelper.createSuccessObject("品牌信息增加成功");
		object.setForwardUrl("/pdm/search-brands");
		return object;
	}
	
	@RequestMapping("/view-brand-logo/{brandId}")
	public String viewBrandLogo(@PathVariable("brandId")Integer brandId, @ModelAttribute("brandForm")BrandForm brandForm, ModelMap modelMap) {
		Brand brand = this.commonSDKManager.getBrandAndValidate(brandId);
		brandForm.setBrandId(brand.getId());
		
		modelMap.put("brand", brand);
		
		return "/pdm/view-brand-logo";
	}
	
	@RequestMapping("/update-brand-logo-action")
	@ResponseBody
	public ViewCommandObject updateBrandLogoAction(@ModelAttribute("brandForm")BrandForm brandForm, ModelMap modelMap) {
		FSFile logoFsFile = null;
		if (UploadUtil.multipartFileExist(brandForm.getLogoMultipartFile())) {
			try {
				logoFsFile = FSManagerExt.getInstance().uploadBySpring(brandForm.getLogoMultipartFile(), FSType.Temporary);
			} catch (FSException e) {
				e.printStackTrace();
				ViewCommandObject viewCommandObject = ViewCommandHelper.createErrorObject("图片文件格式错误，请联系管理员！");
				return viewCommandObject;
			}
		}
		try {
			this.defineManager.updateBrandLogoInfo(brandForm.getBrandId(), logoFsFile);
		} catch (ImageFormatException e) {
			e.printStackTrace();
		} catch (ExistProductsNotAllowWriteException e) {
			e.printStackTrace();
		} catch (ObjectDuplicateException e) {
			e.printStackTrace();
		}
		ViewCommandObject object = ViewCommandHelper.createSuccessObject("品牌信息增加成功");
		object.setForwardUrl("/pdm/search-brands");
		return object;
	}
	
}
