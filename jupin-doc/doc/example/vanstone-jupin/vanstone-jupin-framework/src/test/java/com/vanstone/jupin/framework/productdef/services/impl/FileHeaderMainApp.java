/**
 * 
 */
package com.vanstone.jupin.framework.productdef.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author shipeng
 *
 */
public class FileHeaderMainApp {
	
	public static void main(String[] args) throws IOException {
		File file = new File("f:/123.jpg");
		FileInputStream fis = new FileInputStream(file);
		byte[] bytes = new byte[2];
		fis.read(bytes, 0, 2);
		fis.close();
		
		System.out.println(bytes[0]);
		System.out.println(bytes[0] & 0xff);
		
//		System.out.println(Integer.toBinaryString(1));
//		System.out.println(Integer.toBinaryString(-1));
//		System.out.println(Integer.toBinaryString(0xff));
//		System.out.println(bytes[1]);
//		System.out.println(Integer.toBinaryString(-Integer.MAX_VALUE));
//		System.out.println(Integer.MAX_VALUE);
		System.out.println("===");
		//System.out.println((char)bytes[0]);
		
		System.out.println(Byte.MIN_VALUE);
		System.out.println(Byte.MAX_VALUE);
		//System.out.println((int)'A');
	}
	
}
