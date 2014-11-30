package com.vanstone.jupin.ebs.pm.framework.services.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.jupin.ebs.pm.productdefine.Brand;
import com.vanstone.jupin.ebs.pm.productdefine.services.BrandService;

@ContextConfiguration(locations = { 
		"classpath*:/core-context.xml",
		"classpath*:META-INF/jupin-*-context-module.xml" }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class BrandServiceImplTest {

	@Autowired
	private BrandService brandService;
	
	@Test
	public void testAddBrandBrand() throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet("http://www.pop-fashion.com/brand.php?column_name=style_graphic");
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = httpClient.execute(httpGet);
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			String content = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
			Document document = Jsoup.parse(content);
			Elements elements = document.getElementsByTag("span");
			for (Element element : elements) {
				String brandName = element.text();
				if (brandName == null || brandName.equals("")) {
					continue;
				}
				System.out.println(brandName);
				Brand brand = new Brand();
				brand.setBrandName(brandName);
				try {
					brandService.addBrand(brand);
				} catch (ObjectDuplicateException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public void testAddBrandBrandCollectionOfProductCategory() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetBrand() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBrandsMap() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteBrandLogoImage() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateBrandBaseInfo() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateBrandLogoInfo() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteBrand() {
		fail("Not yet implemented");
	}

	@Test
	public void testForceDeleteBrand() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBrandsWithStat() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTotalBrands() {
		fail("Not yet implemented");
	}

	@Test
	public void testgetBrandsWithStat() {
		Collection<Brand> brands = this.brandService.getBrandsWithStat(null, "S", 0, 100);
		for (Brand brand : brands) {
			System.out.println(brand.getBrandName());
		}
	}
	
	@Test
	public void testgetBrandsByPrefix() {
		Collection<Brand> brands = this.brandService.getBrandsByPrefix( "Sab", 100);
		for (Brand brand : brands) {
			System.out.println(brand.getBrandName());
		}
	}
}
