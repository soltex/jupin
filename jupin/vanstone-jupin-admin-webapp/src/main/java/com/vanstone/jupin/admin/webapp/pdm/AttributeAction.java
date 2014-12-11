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
import com.vanstone.common.util.web.PageInfo;
import com.vanstone.jupin.admin.webapp.AdminBaseAction;
import com.vanstone.jupin.business.sdk.adminservice.pdm.DefineManager;
import com.vanstone.jupin.business.sdk.common.CommonSDKManager;
import com.vanstone.jupin.common.Constants;
import com.vanstone.jupin.ecs.product.define.attribute.AbstractAttribute;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4Enum;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4Text;
import com.vanstone.jupin.ecs.product.define.attribute.AttributeBuilder;
import com.vanstone.jupin.ecs.product.define.attribute.AttributeType;
import com.vanstone.jupin.ecs.product.define.services.AttributeCondition;
import com.vanstone.jupin.ecs.product.define.services.AttributeService;
import com.vanstone.jupin.ecs.product.define.services.CategoryService;
import com.vanstone.jupin.ecs.product.define.services.ExistProductsNotAllowWriteException;
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
			Attr4Text attribute = AttributeBuilder.newText(attributeForm.getAttributeName(), attributeForm.getListshowable(), attributeForm.getRequiredable());
			attribute.setAttributeDescription(attributeForm.getAttributeDescription());
			this.attributeService.addAttr4Text(attribute);
		}else{
			String[] valueArray = StringUtils.split(attributeForm.getEnumValues(), "\r\n");
			Set<String> values = new LinkedHashSet<String>();
			List<String> tempList = Arrays.asList(valueArray);
			values.addAll(tempList);
			Attr4Enum attribute = AttributeBuilder.newBaseEnum(attributeForm.getAttributeName(), attributeForm.getListshowable(), attributeForm.getRequiredable(), attributeForm.getSearchable(), attributeForm.getMultiselectable(), values);
			attribute.setAttributeDescription(attributeForm.getAttributeDescription());
			this.attributeService.addAttr4Enum(attribute);
		}
		DialogViewCommandObject commandObject = ViewCommandHelper.createSuccessDialog(true);
		commandObject.setForwardUrl("/pdm/search-attributes/" + attributeType.getCode());
		return commandObject;
	}
	
	@RequestMapping("/search-attributes/{attributeTypeCode}")
	public String searchAttributes(@ModelAttribute("attributeForm")AttributeForm attributeForm, ModelMap modelMap, @PathVariable("attributeTypeCode")Integer attributeTypeCode) {
		AttributeType attributeType = EnumUtils.getByCode(attributeTypeCode, AttributeType.class);
		modelMap.put("attributeType", attributeType);
		
		attributeForm.setAttributeTypeCode(attributeTypeCode);
		
		AttributeCondition condition = null;
		if (attributeType.equals(AttributeType.Text)) {
			//文本条件
			condition = AttributeCondition.createTextTypeCondition(attributeForm.getKey(), attributeForm.getListshowable(), attributeForm.getRequiredable());
		}else {
			condition = AttributeCondition.createEnumTypeConditon(attributeForm.getKey(), attributeForm.getListshowable(), attributeForm.getRequiredable(), attributeForm.getMultiselectable(), attributeForm.getSearchable());
		}
		PageInfo<AbstractAttribute> pageInfo = defineManager.searchAttributes(condition, attributeForm.getPageNum(), Constants.ADMIN_DEFAULT_PAGESIZE);
		modelMap.put("pageInfo", pageInfo);
		return "/pdm/search-attributes";
	}
	
	@RequestMapping("/view-text-attribute/{id}")
	public String viewTextAttribute(@PathVariable("id")int attributeID, ModelMap modelMap, @ModelAttribute("attributeForm")AttributeForm attributeForm) {
		AbstractAttribute attribute = this.commonSDKManager.getAttributeAndValidate(attributeID);
		if (!attribute.getAttributeType().equals(AttributeType.Text)) {
			throw new IllegalArgumentException();
		}
		Attr4Text attr4Text = (Attr4Text)attribute;
		attributeForm.setId(attr4Text.getId());
		attributeForm.setAttributeName(attr4Text.getAttributeName());
		attributeForm.setAttributeDescription(attr4Text.getAttributeDescription());
		attributeForm.setListshowable(attr4Text.isListshowable());
		attributeForm.setRequiredable(attr4Text.isRequiredable());
		
		return "/pdm/view-text-attribute";
	}
	
	@RequestMapping("/update-text-attribute-action")
	@ResponseBody
	public DialogViewCommandObject updateTextAttributeAction(ModelMap modelMap, @ModelAttribute("attributeForm")AttributeForm attributeForm) {
		AbstractAttribute attribute = this.commonSDKManager.getAttributeAndValidate(attributeForm.getId());
		if (attribute == null || !attribute.getAttributeType().equals(AttributeType.Text)) {
			throw new IllegalArgumentException();
		}
		Attr4Text attr4Text = (Attr4Text)attribute;
		attr4Text.setAttributeName(attributeForm.getAttributeName());
		attr4Text.setAttributeDescription(attributeForm.getAttributeDescription());
		attr4Text.setListshowable(attributeForm.getListshowable());
		attr4Text.setRequiredable(attributeForm.getRequiredable());
		
		this.attributeService.updateAttr4Text(attr4Text);
		DialogViewCommandObject commandObject = ViewCommandHelper.createSuccessDialog(true);
		commandObject.setForwardUrl("/pdm/search-attributes/" + attribute.getAttributeType().getCode());
		return commandObject;
	}
	
	@RequestMapping("/view-enum-attribute/{id}")
	public String viewEnumAttribute(@PathVariable("id")int attributeID, ModelMap modelMap, @ModelAttribute("attributeForm")AttributeForm attributeForm) {
		AbstractAttribute attribute = this.commonSDKManager.getAttributeAndValidate(attributeID);
		if (!attribute.getAttributeType().equals(AttributeType.Enum)) {
			throw new IllegalArgumentException();
		}
		Attr4Enum attr4Enum = (Attr4Enum)attribute;
		attributeForm.setId(attr4Enum.getId());
		attributeForm.setAttributeName(attr4Enum.getAttributeName());
		attributeForm.setAttributeDescription(attr4Enum.getAttributeDescription());
		attributeForm.setListshowable(attr4Enum.isListshowable());
		attributeForm.setRequiredable(attr4Enum.isRequiredable());
		attributeForm.setMultiselectable(attr4Enum.isMultiselectable());
		attributeForm.setSearchable(attr4Enum.isSearchable());
		
		modelMap.put("attribute", attr4Enum);
		return "/pdm/view-enum-attribute";
	}
	
	@RequestMapping("/update-enum-attribute-action")
	@ResponseBody
	public DialogViewCommandObject updateEnumAttributeAction(ModelMap modelMap, @ModelAttribute("attributeForm")AttributeForm attributeForm) {
		DialogViewCommandObject commandObject = ViewCommandHelper.createSuccessDialog(false);
		commandObject.setForwardUrl("/pdm/view-enum-attribute/" + attributeForm.getId());
		return commandObject;
	}
	
	@RequestMapping("/delete-attribute-action/{id}")
	@ResponseBody
	public ViewCommandObject deleteAttributeAction(@PathVariable("id")int id, @ModelAttribute("attributeForm")AttributeForm attributeForm, ModelMap modelMap) {
		AbstractAttribute attribute = this.commonSDKManager.getAttributeAndValidate(id);
		try {
			this.attributeService.deleteAttribute(attribute);
		} catch (ExistProductsNotAllowWriteException e) {
			ViewCommandObject viewCommandObject = ViewCommandHelper.createErrorObject("属性信息不允许删除，请联系管理员！");
			return viewCommandObject;
		}
		ViewCommandObject viewCommandObject = ViewCommandHelper.createSuccessObject("属性删除成功！");
		StringBuffer sb = new StringBuffer();
		sb.append("/pdm/search-attributes/" + attribute.getAttributeType().getCode());
		sb.append("?pageNum=").append(attributeForm.getPageNum());
		viewCommandObject.setForwardUrl(sb.toString());
		return viewCommandObject;
	}
	
}
