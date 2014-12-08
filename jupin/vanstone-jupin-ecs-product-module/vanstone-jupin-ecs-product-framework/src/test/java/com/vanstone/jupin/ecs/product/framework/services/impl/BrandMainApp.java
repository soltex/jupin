/**
 * 
 */
package com.vanstone.jupin.ecs.product.framework.services.impl;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author shipeng
 *
 */
public class BrandMainApp {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void main(String[] args) throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		String url = "http://www.pop-fashion.com/brand.php?column_name=style_graphic";
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpGet);
		String content = EntityUtils.toString(response.getEntity());
		Document document = Jsoup.parse(content);
		Elements elements = document.getElementsByTag("span");
		for (Element element : elements) {
			System.out.println(element.text());
		}
	}
	
}
