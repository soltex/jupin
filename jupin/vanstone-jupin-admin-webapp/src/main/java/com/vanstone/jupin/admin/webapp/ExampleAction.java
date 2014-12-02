/**
 * 
 */
package com.vanstone.jupin.admin.webapp;

import org.springframework.stereotype.Controller;
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
}
