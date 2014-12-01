/**
 * 
 */
package com.vanstone.jupin.admin.webapp.pdm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vanstone.jupin.admin.webapp.AdminBaseAction;
import com.vanstone.jupin.ecs.product.define.services.SizeService;

/**
 * @author shipeng
 */
@Controller("categoryAction")
@RequestMapping("/pdm")
public class CategoryAction extends AdminBaseAction {
	
	@Autowired
	private SizeService sizeService;
	
}
