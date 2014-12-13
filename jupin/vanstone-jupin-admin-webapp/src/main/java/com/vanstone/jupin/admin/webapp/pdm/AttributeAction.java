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

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.business.lang.EnumUtils;
import com.vanstone.common.util.web.PageInfo;
import com.vanstone.jupin.admin.webapp.AdminBaseAction;
import com.vanstone.jupin.business.sdk.adminservice.pdm.DefineManager;
import com.vanstone.jupin.business.sdk.common.CommonSDKManager;
import com.vanstone.jupin.common.Constants;
import com.vanstone.jupin.ecs.product.define.attribute.AbstractAttribute;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4Enum;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4EnumValue;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4Text;
import com.vanstone.jupin.ecs.product.define.attribute.AttributeBuilder;
import com.vanstone.jupin.ecs.product.define.attribute.AttributeType;
import com.vanstone.jupin.ecs.product.define.services.AttributeCondition;
import com.vanstone.jupin.ecs.product.define.services.AttributeService;
import com.vanstone.jupin.ecs.product.define.services.ProductCategoryService;
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
	private ProductCategoryService categoryService;
	
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
	public ViewCommandObject updateEnumAttributeAction(ModelMap modelMap, @ModelAttribute("attributeForm")AttributeForm attributeForm) {
		
		Boolean listshowable = attributeForm.getListshowable() != null ? attributeForm.getListshowable() : false;
		Boolean requiredable = attributeForm.getRequiredable() != null ? attributeForm.getRequiredable() : false;
		Boolean multiselectable = attributeForm.getMultiselectable() != null ? attributeForm.getMultiselectable() : false;
		Boolean searchable = attributeForm.getSearchable() != null ? attributeForm.getSearchable() : false;
		
		this.defineManager.updateBaseEnumAttr(attributeForm.getId(), attributeForm.getAttributeName(), attributeForm.getAttributeDescription(), listshowable, requiredable, multiselectable, searchable);
		ViewCommandObject commandObject = ViewCommandHelper.createSuccessObject("编辑属性基本信息成功，请点击返回按钮进行检索属性！");
		return commandObject;
	}
	
	@RequestMapping("/view-enum-value/{valueID}")
	public String viewEnumValue(@PathVariable("valueID") int valueID, @ModelAttribute("attributeForm")AttributeForm attributeForm, ModelMap modelMap) {
		Attr4EnumValue attr4EnumValue = this.commonSDKManager.getAttr4EnumValueAndValidate(valueID);
		attributeForm.setValueId(attr4EnumValue.getId());
		attributeForm.setObjectText(attr4EnumValue.getObjectText());
		modelMap.put("attr4EnumValue",attr4EnumValue);
		return "/pdm/view-enum-value";
	}
	
	@RequestMapping("/update-enum-value-action")
	@ResponseBody
	public DialogViewCommandObject updateEnumValueAction(@ModelAttribute("attributeForm")AttributeForm attributeForm, ModelMap modelMap) {
		try {
			this.defineManager.updateBaseAttr4EnumValue(attributeForm.getValueId(), attributeForm.getObjectText());
		} catch (ObjectDuplicateException e) {
			DialogViewCommandObject commandObject = ViewCommandHelper.createErrorDialog(false);
			commandObject.setMessage("枚举属性值重复，请检查！");
			return commandObject;
		}
		DialogViewCommandObject commandObject = ViewCommandHelper.createSuccessDialog(true);
		commandObject.setMessage("修改枚举属性值成功！");
		commandObject.setForwardUrl("/pdm/view-enum-attribute/" + this.commonSDKManager.getAttr4EnumValueAndValidate(attributeForm.getValueId()).getAttr4Enum().getId());
		return commandObject;
	}
	
	@RequestMapping("/delete-enum-value-action/{valueID}")
	@ResponseBody
	public ViewCommandObject deleteEnumValueAction(@PathVariable("valueID")int valueID) {
		Attr4EnumValue attr4EnumValue = this.commonSDKManager.getAttr4EnumValueAndValidate(valueID);
		this.attributeService.deleteAttr4EnumValue(attr4EnumValue);
		if (attr4EnumValue.getAttr4Enum().getValues().size()  == 1) {
			//当前属性以删除
			ViewCommandObject viewCommandObject = ViewCommandHelper.createSuccessObject("删除枚举【属性值】成功,当前枚举下以不存在属性值，属性被一并删除，请重新添加属性！");
			viewCommandObject.setForwardUrl("/pdm/search-attributes/" + attr4EnumValue.getAttr4Enum().getAttributeType().getCode());
			return viewCommandObject;
		}else {
			ViewCommandObject viewCommandObject = ViewCommandHelper.createSuccessObject("删除枚举【属性值】成功！");
			viewCommandObject.setForwardUrl("/pdm/view-enum-attribute/" + attr4EnumValue.getAttr4Enum().getId());
			return viewCommandObject;
		}
	}
	
	@RequestMapping("/add-enum-value/{attributeID}")
	public String addEnumValue(@PathVariable("attributeID")int attributeID, @ModelAttribute("attributeForm")AttributeForm attributeForm , ModelMap modelMap) {
		Attr4Enum attr4Enum = this.commonSDKManager.getAttr4EnumAndValidate(attributeID);
		modelMap.put("attr4Enum", attr4Enum);
		attributeForm.setId(attr4Enum.getId());
		return "/pdm/add-enum-value";
	}
	
	@RequestMapping("/add-enum-value-action")
	@ResponseBody
	public DialogViewCommandObject addEnumValueAction(@ModelAttribute("attributeForm")AttributeForm attributeForm, ModelMap modelMap) {
		Attr4Enum attr4Enum = this.commonSDKManager.getAttr4EnumAndValidate(attributeForm.getId());
		try {
			defineManager.appendAttr4EnumValue(attributeForm.getId(), attributeForm.getObjectText());
		} catch (ObjectDuplicateException e) {
			DialogViewCommandObject viewCommandObject = ViewCommandHelper.createErrorDialog(false);
			viewCommandObject.setMessage("当前枚举属性值已存在，请检查后重新填写！");
			return viewCommandObject;
		}
		DialogViewCommandObject viewCommandObject = ViewCommandHelper.createSuccessDialog(true);
		viewCommandObject.setMessage("添加枚举属性值成功！");
		viewCommandObject.setForwardUrl("/pdm/view-enum-attribute/" + attr4Enum.getId());
		return  viewCommandObject;
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
	
	@RequestMapping("/top-enumvalue-action/{valueID}")
	@ResponseBody
	public ViewCommandObject topEnumvalueAction(@PathVariable("valueID")int valueID) {
		Attr4EnumValue attr4EnumValue = this.commonSDKManager.getAttr4EnumValueAndValidate(valueID);
		attributeService.topSortAttr4EnumValue(attr4EnumValue);
		ViewCommandObject viewCommandObject = ViewCommandHelper.createSuccessObject("更新置顶排序成功！");
		viewCommandObject.setForwardUrl("/pdm/view-enum-attribute/" + attr4EnumValue.getAttr4Enum().getId());
		return viewCommandObject;
	}
	
	@RequestMapping("/up-enumvalue-action/{valueID}")
	@ResponseBody
	public ViewCommandObject upEnumvalueAction(@PathVariable("valueID")int valueID) {
		Attr4EnumValue attr4EnumValue = this.commonSDKManager.getAttr4EnumValueAndValidate(valueID);
		attributeService.upSortAttr4EnumValue(attr4EnumValue);
		ViewCommandObject viewCommandObject = ViewCommandHelper.createSuccessObject("更新向上排序成功！");
		viewCommandObject.setForwardUrl("/pdm/view-enum-attribute/" + attr4EnumValue.getAttr4Enum().getId());
		return viewCommandObject;
	}
	
	@RequestMapping("/down-enumvalue-action/{valueID}")
	@ResponseBody
	public ViewCommandObject downEnumvalueAction(@PathVariable("valueID")int valueID) {
		Attr4EnumValue attr4EnumValue = this.commonSDKManager.getAttr4EnumValueAndValidate(valueID);
		attributeService.downSortAttr4EnumValue(attr4EnumValue);
		ViewCommandObject viewCommandObject = ViewCommandHelper.createSuccessObject("更新向下排序成功！");
		viewCommandObject.setForwardUrl("/pdm/view-enum-attribute/" + attr4EnumValue.getAttr4Enum().getId());
		return viewCommandObject;
	}
	
	@RequestMapping("/bottom-enumvalue-action/{valueID}")
	@ResponseBody
	public ViewCommandObject bottomEnumvalueAction(@PathVariable("valueID")int valueID) {
		Attr4EnumValue attr4EnumValue = this.commonSDKManager.getAttr4EnumValueAndValidate(valueID);
		attributeService.bottomSortAttr4EnumValue(attr4EnumValue);
		ViewCommandObject viewCommandObject = ViewCommandHelper.createSuccessObject("更新置底排序成功！");
		viewCommandObject.setForwardUrl("/pdm/view-enum-attribute/" + attr4EnumValue.getAttr4Enum().getId());
		return viewCommandObject;
	}
	
}
