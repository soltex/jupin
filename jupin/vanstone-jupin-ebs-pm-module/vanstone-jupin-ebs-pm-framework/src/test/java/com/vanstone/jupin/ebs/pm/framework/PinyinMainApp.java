/**
 * 
 */
package com.vanstone.jupin.ebs.pm.framework;

import org.apache.commons.lang.StringUtils;

import com.vanstone.jupin.common.Constants;

/**
 * @author shipeng
 *
 */
public class PinyinMainApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "　Jean-Charles De Castelbajac";
//		System.out.println(PinyinUtil.cnstr2pinyinstr(str));
//		System.out.println(PinyinUtil.firstLetterOfString(str));
//		System.out.println(StringUtils.replace(str, "adis", "mm"));
		System.out.println(StringUtils.replaceChars(str, Constants.BRAND_NAME_CHARS, ""));
	}
	
}
