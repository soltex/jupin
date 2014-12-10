/**
 * 
 */
package com.vanstone.jupin.adminservice.auth.sdk;

import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.vanstone.business.MyAssert4Business;

/**
 * 权限配置信息
 * @author shipeng
 */
public class AuthConf {
	
	/**配置文件名称*/
	private static final String FILTER_CONF_PATH = "jupin-adminservice-filter.xml";
	
	private static Logger LOG = LoggerFactory.getLogger(AuthConf.class);
	
	/**统一路径解析器*/
	private static final PathMatcher PATH_MATCHER = new AntPathMatcher();
	
	private static final class FilterConfInstance {
		private static final AuthConf instance = new AuthConf();
		static {
			instance.init();
		}
	}
	
	/**登录页面，进行跳转页面*/
	private String loginPage;
	/**排除页面,以下页面不被认证，直接通过,支持通配符表达式*/
	private Set<String> excludePages = new LinkedHashSet<String>();
	
	public static AuthConf getInstance() {
		return FilterConfInstance.instance;
	}
	
	@SuppressWarnings("unchecked")
	private void init() {
		InputStream is = AuthConf.class.getResourceAsStream(FILTER_CONF_PATH);
		LOG.debug("Load Filter Conf xml from classpath.{}", FILTER_CONF_PATH);
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(is);
		} catch (DocumentException e) {
			throw new ExceptionInInitializerError(e);
		}
		Element rootElement = document.getRootElement();
		//登录页面信息
		Element loginpageElement = rootElement.element("loginpage");
		if (loginpageElement == null) {
			throw new ExceptionInInitializerError();
		}
		this.loginPage = loginpageElement.getText();
		if (this.loginPage == null || this.loginPage.equals("") || this.loginPage.trim().equals("")) {
			this.loginPage = this.loginPage.trim();
		}
		this.excludePages.add(this.loginPage + "*");
		//排除页面信息
		Element excludesElement = rootElement.element("excludes");
		if (excludesElement == null) {
			LOG.warn("Excludes Not Found. Are you sure ? ");
			return ;
		}
		List<Element> elements = excludesElement.elements("excludepage");
		if (elements == null || elements.size() <=0) {
			LOG.warn("ExcludePage Node Not Found. Are you sure ? ");
			return;
		}
		for (Element excludeElement : elements) {
			String temp = excludeElement.getText();
			if (temp != null && !temp.equals("") && !temp.trim().equals("")) {
				this.excludePages.add(temp);
			}
		}
	}
	
	/**
	 * 获取登录页面
	 * @return
	 */
	public String getLoginPage() {
		return loginPage;
	}
	
	/**
	 * 获取排除页面
	 * @return
	 */
	public Set<String> getExcludePages() {
		return excludePages;
	}
	
	/**
	 * 是否存在排除页面
	 * @return
	 */
	public boolean existExcludePage() {
		return this.excludePages.size() != 0;
	}
	
	/**
	 * 是否排除
	 * @param requestPath
	 * @return
	 */
	public boolean isExclude(String requestPath) {
		MyAssert4Business.notNull(requestPath);
		if (existExcludePage()) {
			return true;
		}
		for (String exclude : this.excludePages) {
			boolean match = PATH_MATCHER.match(exclude, requestPath);
			if (match) {
				return true;
			}
		}
		return false;
	}
	
}
