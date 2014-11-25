/**
 * 
 */
package com.vanstone.jupin.common.util;

import com.vanstone.business.lang.YesNo;

/**
 * @author shipeng
 *
 */
public class BoolUtil {
	
	/**
	 * 解析成数字类型
	 * @param yesno
	 * @return
	 */
	public static Integer parseBoolean(boolean yesno) {
		if (yesno) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * 解析整数到Boolean类型
	 * @param yesno
	 * @return
	 */
	public static boolean parseInt(Integer yesno) {
		if (yesno == null || yesno != 1) {
			return false;
		}
		return true;
	}
	
	/**
	 * 解析成YesNo
	 * @param yesno
	 * @return
	 */
	public static YesNo parseIntToYesNO(Integer yesno) {
		if (yesno == null || yesno != 1) {
			return YesNo.No;
		}
		return YesNo.Yes;
	}
	
	/**
	 * YesNo -> Int
	 * @param yesNo
	 * @return
	 */
	public static int parseYesNoToInt(YesNo yesNo) {
		if (yesNo == null || yesNo.equals(YesNo.No)) {
			return 0;
		}
		return 1;
	}
}
