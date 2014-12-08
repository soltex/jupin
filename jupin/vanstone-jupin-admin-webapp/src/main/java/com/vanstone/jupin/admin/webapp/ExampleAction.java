/**
 * 
 */
package com.vanstone.jupin.admin.webapp;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author shipeng
 */
@Controller
public class ExampleAction {

	@RequestMapping("/add-example")
	public String addFormExample(@ModelAttribute("form")ExampleForm exampleForm) {
		return "/add-example";
	}
	
	@RequestMapping("/add-example-action")
	public String addExampleAction(@ModelAttribute("form")ExampleForm exampleForm) {
		for (String str : exampleForm.getStrings()) {
			System.out.println(str);
		}
		return null;
	}
	
	@RequestMapping("/index-example")
	public String indexExample(ModelMap modelMap, @ModelAttribute("password")String password, HttpServletRequest servletRequest) {
//		modelMap.put("username", "shipeng");
//		
//		System.out.println(" =========================================== ");
//		Map<String, Object> map = servletRequest.getParameterMap();
//		for (Map.Entry<String, Object> entry : map.entrySet()) {
//			System.out.println(entry.getKey() + " == " + entry.getValue());
//		}
//		System.out.println(" =========================================== ");
//		Enumeration<String> names = servletRequest.getAttributeNames();
//		if (names != null) {
//			while (names.hasMoreElements()) {
//				String name = names.nextElement();
//				System.out.println(name);
//			}
//		}
		return "forward:/index1";
	}
	
	@RequestMapping("/index1")
	public String index1(ModelMap modelMap,@ModelAttribute("password")String password, @ModelAttribute("username")String username, HttpServletRequest servletRequest) {
		
		System.out.println("username === " + username);
		Collection<Object> values = modelMap.values();
		if (values != null) {
			for (Object o : values) {
				System.out.println(o);
			}
		}else{
			System.out.println("values is null");
		}
		System.out.println("username1 === " + password);
		System.out.println("servletrequest username = " + servletRequest.getAttribute("username"));
		return null;
	}
}
