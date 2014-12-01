/**
 * 
 */
package com.vanstone.jupin.admin.webapp.pdm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vanstone.jupin.admin.webapp.AdminBaseAction;

/**
 * @author shipeng
 */
@Controller("pdmIndexAction")
@RequestMapping("/pdm")
public class IndexAction extends AdminBaseAction {

	@RequestMapping("/index")
	public String index(ModelMap modelMap) {
		return "/pdm/index";
	}
	
}
