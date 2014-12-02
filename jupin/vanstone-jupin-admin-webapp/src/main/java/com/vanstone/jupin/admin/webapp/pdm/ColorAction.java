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

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.jupin.business.sdk.common.CommonSDKManager;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Color;
import com.vanstone.jupin.ecs.product.define.services.ColorTableService;
import com.vanstone.jupin.ecs.product.define.services.DefineCommonService;
import com.vanstone.jupin.ecs.product.define.services.ExistProductsNotAllowWriteException;
import com.vanstone.webframework.dwz.DialogViewCommandObject;
import com.vanstone.webframework.dwz.ViewCommandHelper;
import com.vanstone.webframework.dwz.ViewCommandObject;

/**
 * @author shipeng
 */
@Controller("colorAction")
@RequestMapping("/pdm")
public class ColorAction {
	
	@Autowired
	private ColorTableService colorTableService;
	@Autowired
	private CommonSDKManager commonSDKManager;
	@Autowired
	private DefineCommonService defineCommonService;
	
	@RequestMapping("/view-colors")
	public String viewColors(ModelMap modelMap,@ModelAttribute("colorForm")ColorForm colorForm) {
		Collection<Color> colors = this.colorTableService.getColors();
		modelMap.put("colors", colors);
		modelMap.put("udcolor", defineCommonService.validateAllowUDOperateColor());
		return "/pdm/view-colors";
	}
	
	@RequestMapping("/add-color-action")
	@ResponseBody
	public ViewCommandObject addColorAction(ModelMap modelMap,@ModelAttribute("colorForm")ColorForm colorForm) {
		Color color = new Color();
		color.setColorCSS(colorForm.getColorCSS());
		color.setColorName(colorForm.getColorName());
		color.setColorRGB(colorForm.getColorRGB());
		try {
			this.colorTableService.addColor(color);
		} catch (ObjectDuplicateException e) {
			return ViewCommandHelper.createErrorObject("颜色值【" + colorForm.getColorName() + "】重复，请确认后重新填写！");
		}
		ViewCommandObject object = ViewCommandHelper.createSuccessObject("添加颜色值成功！");
		object.setForwardUrl("/pdm/view-colors");
		return object;
	}
	
	@RequestMapping("/update-color/{id}")
	public String updateColor(ModelMap modelMap,@PathVariable("id")Integer id, @ModelAttribute("colorForm")ColorForm colorForm) {
		Color color = commonSDKManager.getAndValidateColor(colorForm.getId());
		colorForm.setId(color.getId());
		colorForm.setColorName(color.getColorName());
		colorForm.setColorCSS(color.getColorCSS());
		colorForm.setColorRGB(color.getColorRGB());
		
		return "/pdm/update-color";
	}
	
	@RequestMapping("/update-color-action")
	@ResponseBody
	public DialogViewCommandObject updateColorAction(ModelMap modelMap, @ModelAttribute("colorForm")ColorForm colorForm) {
		Color color = this.commonSDKManager.getAndValidateColor(colorForm.getId());
		color.setColorCSS(colorForm.getColorCSS());
		color.setColorName(colorForm.getColorName());
		color.setColorRGB(colorForm.getColorRGB());
		try {
			this.colorTableService.updateColor(color);
		} catch (ExistProductsNotAllowWriteException e) {
			DialogViewCommandObject object = ViewCommandHelper.createErrorDialog(false);
			object.setMessage("当前颜色值已在产品中使用，如要强制修改，请联系管理员！");
			return object;
		} catch (ObjectDuplicateException e) {
			DialogViewCommandObject object = ViewCommandHelper.createErrorDialog(false);
			object.setMessage("颜色值【" + colorForm.getColorName() + "】重复，请确认后重新填写！");
			return object;
		}
		DialogViewCommandObject object = ViewCommandHelper.createSuccessDialog(true);
		object.setMessage("修改颜色成功！");
		object.setForwardUrl("/pdm/view-colors");
		return object;
	}
	
	@RequestMapping("/delete-color-action/{id}")
	@ResponseBody
	public ViewCommandObject deleteColorAction(@PathVariable("id")Integer id) {
		this.commonSDKManager.getAndValidateColor(id);
		try {
			this.colorTableService.deleteColor(id);
		} catch (ExistProductsNotAllowWriteException e) {
			ViewCommandObject object = ViewCommandHelper.createErrorObject();
			object.setMessage("当前颜色值已在产品中使用，如要强制修改，请联系管理员！");
			return object;
		}
		ViewCommandObject object = ViewCommandHelper.createSuccessObject("删除颜色表成功！");
		object.setForwardUrl("/pdm/view-colors");
		return object;
	}
	
}
	