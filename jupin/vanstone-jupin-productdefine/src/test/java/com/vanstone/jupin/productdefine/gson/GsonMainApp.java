/**
 * 
 */
package com.vanstone.jupin.productdefine.gson;

import java.util.ArrayList;
import java.util.Collection;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.vanstone.business.serialize.GsonCreator;
import com.vanstone.jupin.productdefine.attr.sku.Color;

/**
 * @author shipeng
 *
 */
public class GsonMainApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Collection<Color> colors = new ArrayList<Color>();
		Color c1 = new Color();
		c1.setId(1);
		c1.setColorName("红色");
		
		Color c2 = new Color();
		c2.setId(1);
		c2.setColorName("红色");
		
		Color c3 = new Color();
		c3.setId(1);
		c3.setColorName("红色");
		
		colors.add(c1);
		colors.add(c2);
		colors.add(c3);
		
		Gson gson = GsonCreator.create();
		String json = gson.toJson(colors);
		System.out.println(json);
		
		Collection<Color> loadColors = gson.fromJson(json, (new TypeToken<Collection<Color>>() {
			/***/
			private static final long serialVersionUID = -6714381475122992434L;}).getType());
		for (Color color : loadColors) {
			System.out.println(color.getColorName());
		}
	}
	
}
