/**
 * 
 */
package com.vanstone.jupin.admin.webapp.productdefine;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vanstone.jupin.admin.webapp.AdminBaseAction;
import com.vanstone.webframework.dwz.DWZDialogObject;
import com.vanstone.webframework.dwz.DWZObject;

/**
 * @author shipeng
 */
@Controller("productDefineAction")
@RequestMapping("/productdefine")
public class ProductDefineAction extends AdminBaseAction {
	
	private ColorService colorService;
	
	//======================================Color======================================//
	@RequestMapping("/view-colors")
	public String viewColors() {
		return null;
	}
	
	@RequestMapping("/add-color-action")
	public DWZObject addColorAction() {
		return null;
	}
	
	@RequestMapping("/update-color")
	public String updateColor() {
		return null;
	}
	
	@RequestMapping("/update-color-action")
	public DWZDialogObject updateColorAction() {
		return null;
	}
	
	@RequestMapping("/delete-color-action")
	public DWZDialogObject deleteColorAction() {
		return null;
	}
	
	
}
