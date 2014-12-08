/**
 * 
 */
package com.vanstone.jupin.adminservice.sdk.pdm.define.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.common.MyAssert;
import com.vanstone.common.component.task.TaskManagerFactory;
import com.vanstone.common.util.web.PageInfo;
import com.vanstone.common.util.web.PageUtil;
import com.vanstone.framework.business.services.DefaultBusinessService;
import com.vanstone.fs.FSException;
import com.vanstone.fs.FSFile;
import com.vanstone.jupin.business.sdk.adminservice.pdm.define.DefineManager;
import com.vanstone.jupin.business.sdk.adminservice.pdm.define.ImportBrandFileFormatException;
import com.vanstone.jupin.business.sdk.adminservice.pdm.define.ImportBrandResultBean;
import com.vanstone.jupin.business.sdk.adminservice.pdm.define.SizeBean;
import com.vanstone.jupin.business.sdk.common.CommonSDKManager;
import com.vanstone.jupin.common.ImageFormatException;
import com.vanstone.jupin.common.WeedFSException;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.jupin.common.util.ExcelUtil;
import com.vanstone.jupin.common.util.UploadUtil;
import com.vanstone.jupin.ecs.product.define.Brand;
import com.vanstone.jupin.ecs.product.define.ProductCategoryDetail;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Size;
import com.vanstone.jupin.ecs.product.define.attribute.sku.SizeTemplate;
import com.vanstone.jupin.ecs.product.define.services.BrandService;
import com.vanstone.jupin.ecs.product.define.services.CategoryService;
import com.vanstone.jupin.ecs.product.define.services.ExistProductsNotAllowWriteException;
import com.vanstone.jupin.ecs.product.define.services.SizeService;
import com.vanstone.jupin.messagebox.Message;
import com.vanstone.jupin.messagebox.MessageHelper;
import com.vanstone.jupin.messagebox.MessageLevel;

/**
 * @author shipeng
 */
@Service("sizeManager")
@Validated
public class DefineManagerImpl extends DefaultBusinessService implements DefineManager {
	
	/***/
	private static final long serialVersionUID = -4899067001770580354L;
	
	@Autowired
	private SizeService sizeService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CommonSDKManager commonSDKManager;
	@Autowired
	private BrandService brandService;
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.business.sdk.adminservice.pdm.define.SizeManager#addSizeTemplate(java.lang.String, java.lang.String, boolean, boolean, boolean, boolean, boolean, boolean, boolean, java.util.Collection)
	 */
	@Override
	public SizeTemplate addSizeTemplate(String templateName, String content, boolean systemable, boolean waistlineable,
			boolean weightable, boolean hipable, boolean chestable, boolean heightable, boolean shoulderable,
			Collection<SizeBean> sizeBeans) throws ObjectDuplicateException {
		MyAssert.notNull(sizeBeans);
		MyAssert.notEmpty(sizeBeans);
		Collection<Size> sizes = new ArrayList<Size>();
		for (SizeBean bean : sizeBeans) {
			Size size = new Size();
			size.setSizeName(bean.getSizeName());
			
			size.setWaistlineStart(bean.getWaistlineStart());
			size.setWaistlineEnd(bean.getWaistlineEnd());
			
			size.setWeightStart(bean.getWeightStart());
			size.setWeightEnd(bean.getWeightEnd());
			
			size.setHipStart(bean.getHipStart());
			size.setHipEnd(bean.getHipEnd());
			
			size.setChestStart(bean.getChestStart());
			size.setChestEnd(bean.getChestEnd());
			
			size.setHeightStart(bean.getHeightStart());
			size.setHeightEnd(bean.getHeightEnd());
			
			size.setShoulderStart(bean.getShoulderStart());
			size.setShoulderEnd(bean.getShoulderEnd());
			
			sizes.add(size);
		}
		return this.sizeService.addSizeTemplate(templateName, content, systemable, waistlineable, weightable, hipable, chestable, heightable, shoulderable, sizes);
	}

	@Override
	public Size addSize(SizeBean sizeBean) throws ObjectDuplicateException, ExistProductsNotAllowWriteException {
		MyAssert.notNull(sizeBean);
		SizeTemplate sizeTemplate = this.sizeService.getSizeTemplate(sizeBean.getSizeTemplateId());
		if (sizeTemplate == null) {
			throw new IllegalArgumentException();
		}
		Size size = new Size(sizeTemplate);
		size.setSizeName(sizeBean.getSizeName());
		size.setWaistlineStart(sizeBean.getWaistlineStart());
		size.setWaistlineEnd(sizeBean.getWaistlineEnd());
		size.setWeightStart(sizeBean.getWeightStart());
		size.setWeightEnd(sizeBean.getWeightEnd());
		size.setHipStart(sizeBean.getHipStart());
		size.setHipEnd(sizeBean.getHipEnd());
		size.setChestStart(sizeBean.getChestStart());
		size.setChestEnd(sizeBean.getChestEnd());
		size.setHeightStart(sizeBean.getHeightStart());
		size.setHeightEnd(sizeBean.getHeightEnd());
		size.setShoulderStart(sizeBean.getShoulderStart());
		size.setShoulderEnd(sizeBean.getShoulderEnd());
		
		return this.sizeService.addSize(size);
	}

	@Override
	public Size updateSize(SizeBean sizeBean) throws ObjectDuplicateException, ExistProductsNotAllowWriteException {
		MyAssert.notNull(sizeBean);
		SizeTemplate sizeTemplate = this.sizeService.getSizeTemplate(sizeBean.getSizeTemplateId());
		if (sizeTemplate == null) {
			throw new IllegalArgumentException();
		}
		Size size = new Size(sizeTemplate);
		size.setSizeName(sizeBean.getSizeName());
		size.setWaistlineStart(sizeBean.getWaistlineStart());
		size.setWaistlineEnd(sizeBean.getWaistlineEnd());
		size.setWeightStart(sizeBean.getWeightStart());
		size.setWeightEnd(sizeBean.getWeightEnd());
		size.setHipStart(sizeBean.getHipStart());
		size.setHipEnd(sizeBean.getHipEnd());
		size.setChestStart(sizeBean.getChestStart());
		size.setChestEnd(sizeBean.getChestEnd());
		size.setHeightStart(sizeBean.getHeightStart());
		size.setHeightEnd(sizeBean.getHeightEnd());
		size.setShoulderStart(sizeBean.getShoulderStart());
		size.setShoulderEnd(sizeBean.getShoulderEnd());
		size.setId(sizeBean.getId());
		return this.sizeService.updateSize(size);
	}

	@Override
	public void updateSizes(int sizeTemplateId, boolean systemable,boolean waistlineable, boolean weightable, boolean hipable, boolean chestable, boolean heightable, boolean shoulderable,
			Collection<SizeBean> sizeBeans) throws ObjectDuplicateException, ExistProductsNotAllowWriteException {
		SizeTemplate sizeTemplate = this.sizeService.getSizeTemplate(sizeTemplateId);
		if (sizeTemplate == null) {
			throw new IllegalArgumentException();
		}
		for (SizeBean sizeBean : sizeBeans) {
			if (sizeBean.getId() != null) {
				Size size = this.sizeService.getSize(sizeBean.getId());
				if (size == null) {
					throw new IllegalArgumentException();
				}
			}
		}
		sizeTemplate.setSystemable(systemable);
		sizeTemplate.setWaistlineable(waistlineable);
		sizeTemplate.setWeightable(weightable);
		sizeTemplate.setHipable(hipable);
		sizeTemplate.setChestable(chestable);
		sizeTemplate.setHeightable(heightable);
		sizeTemplate.setShoulderable(shoulderable);
		Collection<Size> sizes = new ArrayList<Size>();
		for (SizeBean bean : sizeBeans) {
			Size size = new Size();
			size.setId(bean.getId());
			size.setSizeName(bean.getSizeName());
			
			size.setWaistlineStart(bean.getWaistlineStart());
			size.setWaistlineEnd(bean.getWaistlineEnd());
			
			size.setWeightStart(bean.getWeightStart());
			size.setWeightEnd(bean.getWeightEnd());
			
			size.setHipStart(bean.getHipStart());
			size.setHipEnd(bean.getHipEnd());
			
			size.setChestStart(bean.getChestStart());
			size.setChestEnd(bean.getChestEnd());
			
			size.setHeightStart(bean.getHeightStart());
			size.setHeightEnd(bean.getHeightEnd());
			
			size.setShoulderStart(bean.getShoulderStart());
			size.setShoulderEnd(bean.getShoulderEnd());
			
			sizes.add(size);
		}
		this.sizeService.updateSizeTemplateInfo(sizeTemplate, sizes);
	}

	@Override
	public PageInfo<Brand> searchBrands(Integer categoryID, String key, int pageno, int size) {
		
		ProductCategoryDetail productCategoryDetail = null;
		if (categoryID != null) {
			productCategoryDetail = this.commonSDKManager.getAndValidateProductCategoryDetail(categoryID);
		}
		int allrows = this.brandService.getTotalBrands(productCategoryDetail, key);
		if (allrows == 0) {
			return null;
		}
		PageUtil<Brand> pageUtil = new PageUtil<Brand>(allrows, pageno, size);
		Collection<Brand> brands = this.brandService.getBrandsWithStat(productCategoryDetail, key, pageUtil.getOffset(), pageUtil.getSize());
		PageInfo<Brand> pageInfo = pageUtil.getPageInfo();
		pageInfo.addObjects(brands);
		return pageInfo;
	}

	@Override
	public Brand addBrand(String brandName, String brandNameEN, MultipartFile logoMultipartFile, String content) throws ImageFormatException, ObjectDuplicateException {
		MyAssert.hasText(brandName);
		Brand brand = new Brand();
		brand.setBrandName(brandName);
		brand.setBrandNameEN(brandNameEN);
		brand.setContent(content);
		if (UploadUtil.multipartFileExist(logoMultipartFile)) {
			try {
				ImageBean imageBean = UploadUtil.uploadImageFileThenWeedFS(logoMultipartFile);
				brand.setLogoImage(imageBean);
			} catch (WeedFSException e) {
				e.printStackTrace();
				throw new ImageFormatException(e);
			} catch (FSException e) {
				e.printStackTrace();
				throw new ImageFormatException(e);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new ImageFormatException(e);
			}
		}
		return this.brandService.addBrand(brand);
	}

	@Override
	public ImportBrandResultBean batchImportBrands(FSFile fsFile, boolean asyn) throws ImportBrandFileFormatException {
		final HSSFWorkbook workbook;
		try {
			workbook = new HSSFWorkbook(fsFile.getInputStream());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new ImportBrandFileFormatException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ImportBrandFileFormatException(e);
		}
		
		if (!asyn) {
			return _importBrandsByPOI(workbook);
		}
		TaskManagerFactory.getTaskManager().executeTask(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				ImportBrandResultBean resultBean = _importBrandsByPOI(workbook);
				if (resultBean.isSuccess()) {
					StringBuffer sb = new StringBuffer();
					sb.append("品牌信息导入成功，成功数量 : ").append(resultBean.getSuccessCount());
					Message message = MessageHelper.createNewMessage(MessageLevel.Info, sb.toString());
					message.send();
				} else {
					StringBuffer sb = new StringBuffer();
					sb.append("品牌信息导入部分失败，请检查原始数据文件，出错信息如下：").append("<br/>");
					for (String brandName : resultBean.getFailBrandNames()) {
						sb.append(brandName).append("，");
					}
					sb.append("总计 ： 成功 ").append(resultBean.getSuccessCount()).append(" 失败 ").append(resultBean.getFailCount());
					Message message = MessageHelper.createNewMessage(MessageLevel.Error, sb.toString());
					message.send();
				}
				return null;
			}
		}, true);
		return null;
	}
	
	private ImportBrandResultBean _importBrandsByPOI(HSSFWorkbook workbook) {
		HSSFSheet sheet = workbook.getSheetAt(0);
		int rowNo = sheet.getLastRowNum();
		ImportBrandResultBean result = new ImportBrandResultBean();
		if (rowNo <= 0) {
			return result;
		}
		System.out.println(rowNo);
		for (int i=1;i<rowNo;i++) {
			HSSFRow hssfRow = sheet.getRow(i);
			HSSFCell brandNameHssfCell = hssfRow.getCell(0);
			String brandName = brandNameHssfCell != null ? ExcelUtil.getCellValueAsString(brandNameHssfCell) : null;
			if (brandName == null || brandName.equals("")) {
				continue;
			}
			System.out.println(rowNo + " -- " + i + " -- " + brandName);
			HSSFCell brandNameENHssfCell = hssfRow.getCell(1);
			String brandNameEN = brandNameENHssfCell != null ? ExcelUtil.getCellValueAsString(brandNameENHssfCell) : null;
			
			HSSFCell contentHssfCell = hssfRow.getCell(2);
			String content = contentHssfCell != null ? ExcelUtil.getCellValueAsString(contentHssfCell) : null;
			try {
				this.addBrand(brandName, brandNameEN, null, content);
				result.incSuccessCount();
			} catch (ImageFormatException e) {
				e.printStackTrace();
				result.addFailBrandName(brandName);
				continue;
			} catch (ObjectDuplicateException e) {
				e.printStackTrace();
				result.addFailBrandName(brandName);
				continue;
			} finally {
				brandNameHssfCell = null;
				brandNameENHssfCell = null;
				contentHssfCell = null;
				hssfRow = null;
			}
		}
		return result;
	}

}
