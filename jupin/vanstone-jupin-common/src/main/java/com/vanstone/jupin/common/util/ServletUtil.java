/**
 * 
 */
package com.vanstone.jupin.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet工具类
 * @author shipeng
 */
public class ServletUtil {
	
	/**
	 * 写入输出数据
	 * @param servletResponse
	 * @param text 
	 * @param charset
	 * @return
	 */
	public static void write(HttpServletResponse servletResponse,String text, String charset ) {
		if (servletResponse == null) {
			throw new IllegalArgumentException();
		}
		if (charset != null && !"".equals(charset)) {
			servletResponse.setContentType("text/html;charset=" +charset);
		}
		try {
			servletResponse.getWriter().print(text);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 默认编码格式为UTF-8
	 * @param servletResponse
	 * @param text
	 */
	public static void write(HttpServletResponse servletResponse,String text) {
		write(servletResponse, text, "utf-8");
	}
	
	/**
	 * 获取HttpServletRequest RequestBody值
	 * @param servletRequest
	 * @return
	 */
	public static String readRequestBody(HttpServletRequest servletRequest) {
		if (servletRequest == null) {
			throw new IllegalArgumentException();
		}
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			ServletInputStream inputStream = servletRequest.getInputStream();
			reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
			String tmp = null;
			while ((tmp = reader.readLine()) != null) {
				sb.append(tmp);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		return sb.toString();
	}
}
