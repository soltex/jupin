/**
 * 
 */
package com.vanstone.jupin.admin.webapp.pdm;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.business.ObjectHasSubObjectException;
import com.vanstone.jupin.admin.webapp.AdminBaseAction;
import com.vanstone.jupin.business.sdk.adminservice.pdm.define.DefineManager;
import com.vanstone.jupin.business.sdk.adminservice.pdm.define.SizeBean;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Size;
import com.vanstone.jupin.ecs.product.define.attribute.sku.SizeTemplate;
import com.vanstone.jupin.ecs.product.define.attribute.sku.SizeTemplateWrapBean;
import com.vanstone.jupin.ecs.product.define.services.ExistProductsNotAllowWriteException;
import com.vanstone.jupin.ecs.product.define.services.SizeService;
import com.vanstone.webframework.dwz.DialogViewCommandObject;
import com.vanstone.webframework.dwz.ViewCommandHelper;
import com.vanstone.webframework.dwz.ViewCommandObject;

/**
 * @author shipeng
 */
@Controller("sizeAction")
@RequestMapping("/pdm")
public class SizeAction extends AdminBaseAction {
	
	@Autowired
	private SizeService sizeService;
	@Autowired
	private DefineManager defineManager;
	
	@RequestMapping("/view-sizetemplates")
	public String viewSizeTemplates(ModelMap modelMap) {
		Collection<SizeTemplateWrapBean> wrapBeans = this.sizeService.getSizeTemplatesWithStat();
		modelMap.put("wrapBeans", wrapBeans);
		return "/pdm/view-sizetemplates";
	}
	
	@RequestMapping("/add-sizetemplate")
	public String addSizeTemplate(ModelMap modelMap, @ModelAttribute("sizeForm")SizeForm sizeForm) {
		return "/pdm/add-sizetemplate";
	}
	
	@RequestMapping("/add-sizetemplate-action")
	@ResponseBody
	public DialogViewCommandObject addSizeTemplateAction(ModelMap modelMap, @ModelAttribute("sizeForm")SizeForm sizeForm) {
		if (sizeForm.getSizeNames() == null || sizeForm.getSizeNames().size() <=0) {
			DialogViewCommandObject object =  ViewCommandHelper.createErrorDialog(false);
			object.setMessage("请填写尺码标准");
			return object;
		}
		
		Collection<SizeBean> sizebeans = new ArrayList<SizeBean>();
		for (int i=0;i<sizeForm.getSizeNames().size();i++) {
			String sizeName = sizeForm.getSizeNames().get(i);
			if (sizeName == null || sizeName.equals("")) {
				DialogViewCommandObject object =  ViewCommandHelper.createErrorDialog(false);
				object.setMessage("请填写尺码标准");
				return object;
			}
			SizeBean sizeBean = new SizeBean();
			sizeBean.setSizeName(sizeName);
			sizeBean.setWaistlineStart(sizeForm.getWaistlineStarts() != null && sizeForm.getWaistlineStarts().size() >0 ? sizeForm.getWaistlineStarts().get(i) : null);
			sizeBean.setWaistlineEnd(sizeForm.getWaistlineEnds() != null && sizeForm.getWaistlineEnds().size() >0 ? sizeForm.getWaistlineEnds().get(i) : null);
			/** 体重 */
			sizeBean.setWeightStart(sizeForm.getWeightStarts() != null && sizeForm.getWeightStarts().size() >0 ? sizeForm.getWeightStarts().get(i) : null);
			sizeBean.setWeightEnd(sizeForm.getWeightEnds() != null && sizeForm.getWeightEnds().size() >0 ? sizeForm.getWeightEnds().get(i) : null);
			/** 臀围 */
			sizeBean.setHipStart(sizeForm.getHipStarts() != null && sizeForm.getHipStarts().size() >0 ? sizeForm.getHipStarts().get(i) : null);
			sizeBean.setHipEnd(sizeForm.getHipEnds() != null && sizeForm.getHipEnds().size() >0 ? sizeForm.getHipEnds().get(i) : null);
			/** 胸围 */
			sizeBean.setChestStart(sizeForm.getChestStarts() != null && sizeForm.getChestStarts().size() >0 ? sizeForm.getChestStarts().get(i) : null);
			sizeBean.setChestEnd(sizeForm.getChestEnds() != null && sizeForm.getChestEnds().size() >0 ? sizeForm.getChestEnds().get(i) : null);
			/** 身高 */
			sizeBean.setHeightStart(sizeForm.getHeightStarts() != null && sizeForm.getHeightStarts().size() >0 ? sizeForm.getHeightStarts().get(i) : null);
			sizeBean.setHeightEnd(sizeForm.getHeightEnds() != null && sizeForm.getHeightEnds().size() >0 ? sizeForm.getHeightEnds().get(i) : null);
			/** 肩宽 */
			sizeBean.setShoulderStart(sizeForm.getShoulderStarts() != null && sizeForm.getShoulderStarts().size() >0 ? sizeForm.getShoulderStarts().get(i) : null);
			sizeBean.setShoulderEnd(sizeForm.getShoulderEnds() != null && sizeForm.getShoulderEnds().size() >0 ? sizeForm.getShoulderEnds().get(i) : null);
			
			if (!sizeBean.validateSizeInfo()) {
				DialogViewCommandObject object =  ViewCommandHelper.createErrorDialog(false);
				object.setMessage("请填写尺码标准");
				return object;
			}
			
			sizebeans.add(sizeBean);
		}
		try {
			this.defineManager.addSizeTemplate(sizeForm.getTemplateName(), sizeForm.getTemplateContent(), true, sizeForm.isWaistlineable(), sizeForm.isWeightable(), 
					sizeForm.isHipable(), sizeForm.isChestable(), sizeForm.isHeightable(), sizeForm.isShoulderable(), sizebeans);
		} catch (ObjectDuplicateException e) {
			DialogViewCommandObject object =  ViewCommandHelper.createErrorDialog(false);
			object.setMessage("尺码模板名称重复，请重新填写！");
			return object;
		}
		DialogViewCommandObject object =  ViewCommandHelper.createSuccessDialog(true);
		object.setMessage("添加尺码模板成功！");
		object.setForwardUrl("/pdm/view-sizetemplates");
		return object;
	}
	
	@RequestMapping("/delete-sizetemplate-action/{sizeTemplateId}")
	@ResponseBody
	public ViewCommandObject deleteSizeTemplateAction(@PathVariable("sizeTemplateId")Integer id, ModelMap modelMap) {
		SizeTemplate sizeTemplate = this.sizeService.getSizeTemplate(id);
		if (sizeTemplate == null) {
			throw new IllegalArgumentException();
		}
		try {
			this.sizeService.deleteSizeTemplate(id);
		} catch (ExistProductsNotAllowWriteException e) {
			DialogViewCommandObject object =  ViewCommandHelper.createErrorDialog(false);
			object.setMessage("尺码信息不允许修改，请联系管理员！");
			return object;
		}
		DialogViewCommandObject object =  ViewCommandHelper.createSuccessDialog(true);
		object.setMessage("删除尺码模板成功！");
		object.setForwardUrl("/pdm/view-sizetemplates");
		return object;
	}
	
	@RequestMapping("/update-base-sizetemplate/{sizeTemplateId}")
	public String updateBaseSizeTemplate(@PathVariable("sizeTemplateId")Integer sizeTemplateId, @ModelAttribute("sizeForm")SizeForm sizeForm) {
		SizeTemplate sizeTemplate = this.sizeService.getSizeTemplate(sizeTemplateId);
		if (sizeTemplate== null) {
			throw new IllegalArgumentException();
		}
		sizeForm.setSizeTemplateId(sizeTemplate.getId());
		sizeForm.setTemplateName(sizeTemplate.getTemplateName());
		sizeForm.setTemplateContent(sizeTemplate.getTemplateContent());
		
		return "/pdm/update-base-sizetemplate";
	}
	
	@RequestMapping("/update-base-sizetemplate-action")
	@ResponseBody
	public ViewCommandObject updateBaseSizeTemplateAction(@ModelAttribute("sizeForm")SizeForm sizeForm) {
		SizeTemplate sizeTemplate = this.sizeService.getSizeTemplate(sizeForm.getSizeTemplateId());
		if (sizeTemplate== null) {
			throw new IllegalArgumentException();
		}
		
		try {
			this.sizeService.updateBaseSizeTemplateInfo(sizeForm.getSizeTemplateId(), sizeForm.getTemplateName(), sizeForm.getTemplateContent());
		} catch (ExistProductsNotAllowWriteException e) {
			DialogViewCommandObject object =  ViewCommandHelper.createErrorDialog(false);
			object.setMessage("尺码信息不允许修改，请联系管理员！");
			return object;
		} catch (ObjectHasSubObjectException e) {
			DialogViewCommandObject object =  ViewCommandHelper.createErrorDialog(false);
			object.setMessage("尺码模板名称重复，请重新填写！");
			return object;
		}
		DialogViewCommandObject object =  ViewCommandHelper.createSuccessDialog(true);
		object.setMessage("编辑尺码模板基本信息成功！");
		object.setForwardUrl("/pdm/view-sizetemplates");
		return object;
	}
	
	@RequestMapping("/add-size/{sizeTemplateId}")
	public String addSize(@PathVariable("sizeTemplateId")Integer sizeTemplateId, @ModelAttribute("sizeForm")SizeForm sizeForm, ModelMap modelMap) {
		SizeTemplate sizeTemplate = this.sizeService.getSizeTemplate(sizeTemplateId);
		if (sizeTemplate== null) {
			throw new IllegalArgumentException();
		}
		modelMap.put("sizeTemplate", sizeTemplate);
		sizeForm.setWaistlineable(sizeTemplate.isWaistlineable());
		sizeForm.setWeightable(sizeTemplate.isWeightable());
		sizeForm.setHipable(sizeTemplate.isHipable());
		sizeForm.setChestable(sizeTemplate.isChestable());
		sizeForm.setHeightable(sizeTemplate.isHeightable());
		sizeForm.setShoulderable(sizeTemplate.isShoulderable());
		sizeForm.setSizeTemplateId(sizeTemplate.getId());
		
		return "/pdm/add-size";
	}
	
	@RequestMapping("/add-size-action")
	@ResponseBody
	public DialogViewCommandObject addSizeAction(@ModelAttribute("sizeForm")SizeForm sizeForm, ModelMap modelMap) {
		SizeTemplate sizeTemplate = this.sizeService.getSizeTemplate(sizeForm.getSizeTemplateId());
		if (sizeTemplate== null) {
			throw new IllegalArgumentException();
		}
		SizeBean sizeBean = new SizeBean();
		sizeBean.setSizeName(sizeForm.getSizeName());
		sizeBean.setWaistlineStart(sizeForm.getWaistlineStart());;
		sizeBean.setWaistlineEnd(sizeForm.getWaistlineEnd());
		sizeBean.setWeightStart(sizeForm.getWeightStart());;
		sizeBean.setWeightEnd(sizeForm.getWeightEnd());
		sizeBean.setHipStart(sizeForm.getHipStart());
		sizeBean.setHipEnd(sizeForm.getHipEnd());
		sizeBean.setChestStart(sizeForm.getChestStart());
		sizeBean.setChestEnd(sizeForm.getChestEnd());
		sizeBean.setHeightStart(sizeForm.getHeightStart());
		sizeBean.setHeightEnd(sizeForm.getHeightEnd());
		sizeBean.setShoulderStart(sizeForm.getShoulderStart());
		sizeBean.setShoulderEnd(sizeForm.getShoulderEnd());
		sizeBean.setSizeTemplateId(sizeForm.getSizeTemplateId());
		try {
			this.defineManager.addSize(sizeBean);
		} catch (ExistProductsNotAllowWriteException e) {
			DialogViewCommandObject object =  ViewCommandHelper.createErrorDialog(false);
			object.setMessage("尺码信息不允许修改，请联系管理员！");
			return object;
		} catch (ObjectDuplicateException e) {
			DialogViewCommandObject object =  ViewCommandHelper.createErrorDialog(false);
			object.setMessage("尺码模板名称重复，请重新填写！");
			return object;
		}
		DialogViewCommandObject object =  ViewCommandHelper.createSuccessDialog(true);
		object.setMessage("新建尺码信息成功！");
		object.setForwardUrl("/pdm/view-sizetemplates");
		return object;
	}
	
	@RequestMapping("/delete-size-action/{sizeId}")
	@ResponseBody
	public ViewCommandObject deleteSizeAction(@PathVariable("sizeId")Integer sizeId) {
		Size size = this.sizeService.getSize(sizeId);
		if (size == null) {
			throw new IllegalArgumentException();
		}
		try {
			this.sizeService.deleteSize(sizeId);
		} catch (ExistProductsNotAllowWriteException e) {
			DialogViewCommandObject object =  ViewCommandHelper.createErrorDialog(false);
			object.setMessage("尺码信息不允许修改，请联系管理员！");
			return object;
		}
		DialogViewCommandObject object =  ViewCommandHelper.createSuccessDialog(true);
		object.setMessage("编辑尺码模板基本信息成功！");
		object.setForwardUrl("/pdm/view-sizetemplates");
		return object;
	}
	
	@RequestMapping("/update-size/{sizeId}")
	public String updateSize(@PathVariable("sizeId")Integer sizeId, @ModelAttribute("sizeForm")SizeForm sizeForm, ModelMap modelMap) {
		Size size = this.sizeService.getSize(sizeId);
		if (size== null) {
			throw new IllegalArgumentException();
		}
		SizeTemplate sizeTemplate = size.getSizeTemplate();
		modelMap.put("sizeTemplate", sizeTemplate);
		
		sizeForm.setWaistlineable(sizeTemplate.isWaistlineable());
		sizeForm.setWaistlineStart(size.getWaistlineStart());
		sizeForm.setWaistlineEnd(size.getWaistlineEnd());
		
		sizeForm.setWeightable(sizeTemplate.isWeightable());
		sizeForm.setWeightStart(size.getWeightStart());
		sizeForm.setWeightEnd(size.getWeightEnd());
		
		sizeForm.setHipable(sizeTemplate.isHipable());
		sizeForm.setHipStart(size.getHipStart());
		sizeForm.setHipEnd(size.getHipEnd());
		
		sizeForm.setChestable(sizeTemplate.isChestable());
		sizeForm.setChestStart(size.getChestStart());
		sizeForm.setChestEnd(size.getChestEnd());
		
		sizeForm.setHeightable(sizeTemplate.isHeightable());
		sizeForm.setHeightStart(size.getHeightStart());
		sizeForm.setHeightEnd(size.getHeightEnd());
		
		sizeForm.setShoulderable(sizeTemplate.isShoulderable());
		sizeForm.setShoulderStart(size.getShoulderStart());
		sizeForm.setShoulderEnd(size.getShoulderEnd());
		
		sizeForm.setSizeId(size.getId());
		sizeForm.setSizeName(size.getSizeName());
		System.out.println(sizeForm.getSizeName());
		return "/pdm/update-size";
	}
	
	@RequestMapping("/update-size-action")
	@ResponseBody
	public ViewCommandObject updateSizeAction(@ModelAttribute("sizeForm")SizeForm sizeForm, ModelMap modelMap) {
		Size size = this.sizeService.getSize(sizeForm.getSizeId());
		if (size== null) {
			throw new IllegalArgumentException();
		}
		SizeBean sizeBean = new SizeBean();
		sizeBean.setSizeName(sizeForm.getSizeName());
		sizeBean.setWaistlineStart(sizeForm.getWaistlineStart());;
		sizeBean.setWaistlineEnd(sizeForm.getWaistlineEnd());
		sizeBean.setWeightStart(sizeForm.getWeightStart());;
		sizeBean.setWeightEnd(sizeForm.getWeightEnd());
		sizeBean.setHipStart(sizeForm.getHipStart());
		sizeBean.setHipEnd(sizeForm.getHipEnd());
		sizeBean.setChestStart(sizeForm.getChestStart());
		sizeBean.setChestEnd(sizeForm.getChestEnd());
		sizeBean.setHeightStart(sizeForm.getHeightStart());
		sizeBean.setHeightEnd(sizeForm.getHeightEnd());
		sizeBean.setShoulderStart(sizeForm.getShoulderStart());
		sizeBean.setShoulderEnd(sizeForm.getShoulderEnd());
		sizeBean.setSizeTemplateId(size.getSizeTemplate().getId());
		sizeBean.setId(size.getId());
		
		try {
			this.defineManager.updateSize(sizeBean);
		} catch (ExistProductsNotAllowWriteException e) {
			DialogViewCommandObject object =  ViewCommandHelper.createErrorDialog(false);
			object.setMessage("尺码信息不允许修改，请联系管理员！");
			return object;
		} catch (ObjectDuplicateException e) {
			DialogViewCommandObject object =  ViewCommandHelper.createErrorDialog(false);
			object.setMessage("尺码模板名称重复，请重新填写！");
			return object;
		}
		DialogViewCommandObject object =  ViewCommandHelper.createSuccessDialog(true);
		object.setMessage("更新尺码信息成功！");
		object.setForwardUrl("/pdm/view-sizetemplates");
		return object;
	}
	
}
