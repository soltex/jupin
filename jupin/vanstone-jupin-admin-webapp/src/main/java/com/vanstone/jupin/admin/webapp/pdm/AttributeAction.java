/**
 * 
 */
package com.vanstone.jupin.admin.webapp.pdm;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vanstone.business.lang.EnumUtils;
import com.vanstone.jupin.admin.webapp.AdminBaseAction;
import com.vanstone.jupin.business.sdk.adminservice.pdm.DefineManager;
import com.vanstone.jupin.business.sdk.common.CommonSDKManager;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4Enum;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4Text;
import com.vanstone.jupin.ecs.product.define.attribute.AttributeBuilder;
import com.vanstone.jupin.ecs.product.define.attribute.AttributeType;
import com.vanstone.jupin.ecs.product.define.services.AttributeService;
import com.vanstone.jupin.ecs.product.define.services.CategoryService;
import com.vanstone.webframework.dwz.DialogViewCommandObject;
import com.vanstone.webframework.dwz.ViewCommandHelper;
import com.vanstone.webframework.dwz.ViewCommandObject;

/**
 * @author shipeng
 */
@Controller("attributeAction")
@RequestMapping("/pdm")
public class AttributeAction extends AdminBaseAction {
	
	@Autowired
	private CommonSDKManager commonSDKManager;
	@Autowired
	private AttributeService attributeService;
	@Autowired
	private DefineManager defineManager;
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/add-attribute")
	public String addAttribute(ModelMap modelMap, @ModelAttribute("attributeForm")AttributeForm attributeForm) {
		AttributeType[] attributeTypes = AttributeType.values();
		modelMap.put("attributeTypes", attributeTypes);
		attributeForm.setAttributeTypeCode(AttributeType.Text.getCode());
		return "/pdm/add-attribute";
	}
	
	@RequestMapping("/add-attribute-action")
	@ResponseBody
	public DialogViewCommandObject addAttributeAction(ModelMap modelMap, @ModelAttribute("attributeForm")AttributeForm attributeForm) {
		AttributeType attributeType = EnumUtils.getByCode(attributeForm.getAttributeTypeCode(), AttributeType.class);
		if (attributeType.equals(AttributeType.Text)) {
			Attr4Text attribute = AttributeBuilder.newText(attributeForm.getAttributeName(), attributeForm.isListshowable(), attributeForm.isRequiredable());
			attribute.setAttributeDescription(attributeForm.getAttributeDescription());
			this.attributeService.addAttr4Text(attribute);
		}else{
			String[] valueArray = StringUtils.split(attributeForm.getEnumValues(), "\r\n");
			Set<String> values = new LinkedHashSet<String>();
			List<String> tempList = Arrays.asList(valueArray);
			values.addAll(tempList);
			Attr4Enum attribute = AttributeBuilder.newBaseEnum(attributeForm.getAttributeName(), attributeForm.isListshowable(), attributeForm.isRequiredable(), attributeForm.isSearchable(), attributeForm.isMultiselectable(), values);
			attribute.setAttributeDescription(attributeForm.getAttributeDescription());
			this.attributeService.addAttr4Enum(attribute);
		}
		DialogViewCommandObject commandObject = ViewCommandHelper.createSuccessDialog(true);
		commandObject.setForwardUrl("/pdm/search-attributes");
		return commandObject;
	}
	
	@RequestMapping("/search-attributes")
	public String searchAttributes(@ModelAttribute("attributeForm")AttributeForm attributeForm, ModelMap modelMap) {
		return "/pdm/search-attributes";
	}
	
	@RequestMapping("/update-attribute")
	public String updateAttribute(@PathVariable("id")int attributeID, ModelMap modelMap, @ModelAttribute("attributeForm")AttributeForm attributeForm) {
		return "/pdm/update-attribute";
	}
	
	@RequestMapping("/update-attribute-action")
	@ResponseBody
	public ViewCommandObject updateAttributeAction(ModelMap modelMap, @ModelAttribute("attributeForm")AttributeForm attributeForm) {
		
		return null;
	}
	
	@RequestMapping("/delete-attribute-action/{id}")
	@ResponseBody
	public ViewCommandObject deleteAttributeAction(@PathVariable("id")Integer id, ModelMap modelMap) {
		
		return null;
	}
	
}
