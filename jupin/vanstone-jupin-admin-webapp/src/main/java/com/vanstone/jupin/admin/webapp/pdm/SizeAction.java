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
import org.springframework.web.bind.annotation.ResponseBody;

import com.vanstone.jupin.admin.webapp.AdminBaseAction;
import com.vanstone.jupin.ecs.product.define.attribute.sku.SizeTemplateWrapBean;
import com.vanstone.jupin.ecs.product.define.services.SizeService;
import com.vanstone.webframework.dwz.DWZObject;

/**
 * @author shipeng
 */
@Controller("sizeAction")
@RequestMapping("/pdm")
public class SizeAction extends AdminBaseAction {
	
	@Autowired
	private SizeService sizeService;
	
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
	public DWZObject addSizeTemplateAction(ModelMap modelMap, @ModelAttribute("sizeForm")SizeForm sizeForm) {
		return null;
	}
	
	@RequestMapping("/delete-sizetemplate-action/{sizeTemplateId}")
	@ResponseBody
	public DWZObject deleteSizeTemplateAction(@PathVariable("sizeTemplateId")Integer id, ModelMap modelMap) {
		return null;
	}
	
}
