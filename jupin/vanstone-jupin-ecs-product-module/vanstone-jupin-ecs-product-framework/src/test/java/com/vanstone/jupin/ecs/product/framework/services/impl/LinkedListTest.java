/**
 * 
 */
package com.vanstone.jupin.ecs.product.framework.services.impl;

import java.util.LinkedList;

/**
 * @author shipeng
 *
 */
public class LinkedListTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LinkedList<Integer> ids = new LinkedList<Integer>();
		for (int i=0;i<10;i++) {
			ids.add(i);
		}
		Integer id = null;
		while ((id = ids.pop()) != null) {
			System.out.println(id);
		}
	}

}
