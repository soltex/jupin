/**
 * 
 */
package com.vanstone.jupin.adminservice.pdm.sdk.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.common.MyAssert;
import com.vanstone.common.component.task.TaskManagerFactory;
import com.vanstone.common.util.web.PageInfo;
import com.vanstone.common.util.web.PageUtil;
import com.vanstone.framework.business.services.DefaultBusinessService;
import com.vanstone.fs.FSFile;
import com.vanstone.jupin.business.sdk.adminservice.pdm.DefineManager;
import com.vanstone.jupin.business.sdk.adminservice.pdm.ImportBrandFileFormatException;
import com.vanstone.jupin.business.sdk.adminservice.pdm.ImportBrandResultBean;
import com.vanstone.jupin.business.sdk.adminservice.pdm.SizeBean;
import com.vanstone.jupin.business.sdk.adminservice.pdm.ValidateDefineBean;
import com.vanstone.jupin.business.sdk.common.CommonSDKManager;
import com.vanstone.jupin.common.Constants;
import com.vanstone.jupin.common.ImageFormatException;
import com.vanstone.jupin.common.WeedFSException;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.jupin.common.util.ExcelUtil;
import com.vanstone.jupin.ecs.product.define.Brand;
import com.vanstone.jupin.ecs.product.define.ProductCategoryDetail;
import com.vanstone.jupin.ecs.product.define.attribute.AbstractAttribute;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4Enum;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4EnumValue;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Size;
import com.vanstone.jupin.ecs.product.define.attribute.sku.SizeTemplate;
import com.vanstone.jupin.ecs.product.define.services.AttributeCondition;
import com.vanstone.jupin.ecs.product.define.services.AttributeService;
import com.vanstone.jupin.ecs.product.define.services.BrandService;
import com.vanstone.jupin.ecs.product.define.services.ProductCategoryService;
import com.vanstone.jupin.ecs.product.define.services.ExistProductsNotAllowWriteException;
import com.vanstone.jupin.ecs.product.define.services.SizeService;
import com.vanstone.jupin.messagebox.Message;
import com.vanstone.jupin.messagebox.MessageHelper;
import com.vanstone.jupin.messagebox.MessageLevel;

/**
 * @author shipeng
 */
@Service("defineManager")
@Validated
public class DefineManagerImpl extends DefaultBusinessService implements DefineManager , MessageSourceAware {
	
	/***/
	private static final long serialVersionUID = -4899067001770580354L;
	
	private static Logger LOG = LoggerFactory.getLogger(DefineManagerImpl.class);
	
	@Autowired
	private SizeService sizeService;
	@Autowired
	private ProductCategoryService categoryService;
	@Autowired
	private CommonSDKManager commonSDKManager;
	@Autowired
	private BrandService brandService;
	@Autowired
	private AttributeService attributeService;
	
	private MessageSource messageSource;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
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
			productCategoryDetail = this.commonSDKManager.getProductCategoryDetailAndValidate(categoryID);
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
	public Brand addBrand(String brandName, String brandNameEN, FSFile logoFSFile, String content) throws ImageFormatException, ObjectDuplicateException {
		MyAssert.hasText(brandName);
		Brand brand = new Brand();
		brand.setBrandName(brandName);
		brand.setBrandNameEN(brandNameEN);
		brand.setContent(content);
		if (logoFSFile != null) {
			try {
				ImageBean imageBean = ImageBean.create(logoFSFile.getFile());
				brand.setLogoImage(imageBean);
			} catch (WeedFSException e) {
				throw new ImageFormatException(e);
			} catch (FileNotFoundException e) {
				throw new ImageFormatException(e);
			}
		}
		return this.brandService.addBrand(brand);
	}

	@Override
	public Brand updateBrandLogoInfo(int brandId, FSFile logoFsFile) throws ImageFormatException, ObjectDuplicateException, ExistProductsNotAllowWriteException {
		MyAssert.notNull(logoFsFile);
		this.commonSDKManager.getBrandAndValidate(brandId);
		try {
			ImageBean imageBean = ImageBean.create(logoFsFile.getFile());
			this.brandService.updateBrandLogoInfo(brandId, imageBean);
		} catch (WeedFSException e) {
			e.printStackTrace();
			throw new ImageFormatException(e);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new ImageFormatException(e);
		}
		return this.commonSDKManager.getBrandAndValidate(brandId);
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
					sb.append(messageSource.getMessage("jupin.adminservice.sdk.pdm.importbrand_success", new Integer[]{resultBean.getSuccessCount()}, null));
					Message message = MessageHelper.createNewMessage(MessageLevel.Info, sb.toString());
					message.send();
				} else {
					StringBuffer sb = new StringBuffer();
					for (String brandName : resultBean.getFailBrandNames()) {
						sb.append(brandName).append("，");
					}
					Message message = MessageHelper.createNewMessage(MessageLevel.Error, messageSource.getMessage("jupin.adminservice.sdk.pdm.importbrand_fail", new Object[]{sb.toString(), resultBean.getSuccessCount(),resultBean.getFailCount() }, null));
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
		for (int i=1;i<rowNo;i++) {
			HSSFRow hssfRow = sheet.getRow(i);
			HSSFCell brandNameHssfCell = hssfRow.getCell(0);
			String brandName = brandNameHssfCell != null ? ExcelUtil.getCellValueAsString(brandNameHssfCell) : null;
			if (brandName == null || brandName.equals("")) {
				continue;
			}
			LOG.debug("Current Excel Total Rows {}, RowNO {}, BrandName {}", rowNo, i, brandName);
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

	@Override
	public void refreshAllProductCategoryDetail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshAllBrands() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshAllAttributes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ValidateDefineBean validateDefineModule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageInfo<AbstractAttribute> searchAttributes(AttributeCondition condition, int pageno, int size) {
		int allrows = this.attributeService.getTotalAttributesByCondition(condition);
		PageUtil<AbstractAttribute> pageUtil = new PageUtil<AbstractAttribute>(allrows, pageno, size);
		Collection<AbstractAttribute> attributes = this.attributeService.getAttributesByCondition(condition, pageUtil.getOffset(), pageUtil.getSize());
		PageInfo<AbstractAttribute> pageInfo = pageUtil.getPageInfo();
		pageInfo.addObjects(attributes);
		return pageInfo;
	}

	@Override
	public Attr4Enum updateBaseEnumAttr(int attributeID, String attributeName, String attributeDescrption,
			boolean listshowable, boolean requiredable, boolean multiselectable, boolean searchable) {
		Attr4Enum attr4Enum = this.commonSDKManager.getAttr4EnumAndValidate(attributeID);
		attr4Enum.setAttributeName(attributeName);
		attr4Enum.setAttributeDescription(attributeDescrption);
		attr4Enum.setListshowable(listshowable);
		attr4Enum.setRequiredable(requiredable);
		attr4Enum.setMultiselectable(multiselectable);
		attr4Enum.setSearchable(searchable);
		return this.attributeService.updateBaseAttr4Enum(attr4Enum);
	}

	@Override
	public Attr4Enum updateBaseAttr4EnumValue(int valueID, String objectText) throws ObjectDuplicateException {
		Attr4EnumValue attr4EnumValue = this.commonSDKManager.getAttr4EnumValueAndValidate(valueID);
		attr4EnumValue.setObjectText(objectText);
		return this.attributeService.updateAttr4EnumValue(attr4EnumValue);
	}

	@Override
	public Attr4Enum appendAttr4EnumValue(final int attributeID, final String objectText) throws ObjectDuplicateException {
		final Attr4Enum attr4Enum = this.commonSDKManager.getAttr4EnumAndValidate(attributeID);
		final int max = this.attributeService.getMaxSortOfAttrEnum(attr4Enum);
		return attributeService.appendAttr4EnumValue(attr4Enum, objectText, max+1);
	}

	@Override
	public ProductCategoryDetail addProductCategoryDetail(Integer parentID, @NotBlank String categoryName, String description, FSFile coverFSFile, Integer sort, boolean skuColor,
			Integer sizeTemplateID, Collection<Integer> attributeIDs) throws ImageFormatException, ExistProductsNotAllowWriteException {
		ProductCategoryDetail parentProductCategoryDetail = null;
		if (parentID != null) {
			parentProductCategoryDetail = this.commonSDKManager.getProductCategoryDetailAndValidate(parentID);
		}
		ImageBean imageBean = null;
		if (coverFSFile != null) {
			try {
				imageBean = ImageBean.create(coverFSFile.getFile());
			} catch (WeedFSException e) {
				throw new ImageFormatException(e);
			} catch (FileNotFoundException e) {
				throw new ImageFormatException(e);
			}
		}
		Collection<AbstractAttribute> attributes = null;
		if (attributeIDs != null && attributeIDs.size() >0) {
			attributes = new ArrayList<AbstractAttribute>();
			for (Integer attributeID : attributeIDs) {
				if (attributeID != null) {
					AbstractAttribute attribute = this.commonSDKManager.getAttributeAndValidate(attributeID);
					attributes.add(attribute);
				}
			}
		}
		if (sort == null) {
			sort = Constants.SYS_DEFAULT_SORT;
		}
		ProductCategoryDetail detail = new ProductCategoryDetail();
		detail.setParentProductCategory(parentProductCategoryDetail);
		detail.setCategoryName(categoryName);
		detail.setDescription(description);
		detail.setCoverImage(imageBean);
		detail.setSkuColor(skuColor);
		SizeTemplate sizeTemplate = null;
		if (sizeTemplateID != null) {
			sizeTemplate = commonSDKManager.getSizeTemplateAndValidate(sizeTemplateID);
			detail.setSizeTemplate(sizeTemplate);
		}
		return this.categoryService.addProductCategory(detail, attributes);
	}
	
}
