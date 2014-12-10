package com.vanstone.jupin.adminservice.pdm.sdk.impl;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.vanstone.business.serialize.GsonCreator;
import com.vanstone.fs.FSFile;
import com.vanstone.fs.FSManager;
import com.vanstone.fs.FSType;
import com.vanstone.jupin.business.sdk.adminservice.pdm.DefineManager;
import com.vanstone.jupin.business.sdk.adminservice.pdm.ImportBrandFileFormatException;
import com.vanstone.jupin.business.sdk.adminservice.pdm.ImportBrandResultBean;

@ContextConfiguration(locations = { 
		"classpath*:/core-context.xml",
		"classpath*:META-INF/jupin**context-module.xml" }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class DefineManagerImplTest {

	@Autowired
	private DefineManager defineManager;
	
	@Test
	public void testAddSizeTemplate() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddSize() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateSize() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateSizes() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchBrands() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddBrand() {
		fail("Not yet implemented");
	}

	@Test
	public void testBatchImportBrands() {
		FSFile fsFile = FSManager.getInstance().getFsFile("/import-brands-template.xls", FSType.Temporary);
		try {
			ImportBrandResultBean bean = defineManager.batchImportBrands(fsFile,false);
			Gson gson = GsonCreator.createPretty();
			System.out.println(gson.toJson(bean));
		} catch (ImportBrandFileFormatException e) {
			e.printStackTrace();
		}
	}

}
