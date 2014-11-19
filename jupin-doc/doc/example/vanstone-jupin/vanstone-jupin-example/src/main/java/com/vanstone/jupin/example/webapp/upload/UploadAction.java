/**
 * 
 */
package com.vanstone.jupin.example.webapp.upload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vanstone.webframework.AbstractBaseSpringAction;

/**
 * @author shipeng
 */
@Controller
@RequestMapping("/upload")
public class UploadAction extends AbstractBaseSpringAction {
		
	@RequestMapping("/")
	public String upload() {
		return "/upload";
	}
	
	@RequestMapping("/upload-action")
	public String uploadAction() {
		return null;
	}
	
}
