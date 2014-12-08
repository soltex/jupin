/**
 * 
 */
package com.vanstone.jupin.common.sdk.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanstone.business.MyAssert4Business;
import com.vanstone.jupin.business.sdk.common.CommonSDKManager;
import com.vanstone.jupin.ecs.product.define.Brand;
import com.vanstone.jupin.ecs.product.define.ProductCategoryDetail;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Color;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Size;
import com.vanstone.jupin.ecs.product.define.attribute.sku.SizeTemplate;
import com.vanstone.jupin.ecs.product.define.services.BrandService;
import com.vanstone.jupin.ecs.product.define.services.CategoryService;
import com.vanstone.jupin.ecs.product.define.services.ColorTableService;
import com.vanstone.jupin.ecs.product.define.services.SizeService;
import com.vanstone.jupin.messagebox.Constants;
import com.vanstone.jupin.messagebox.Message;
import com.vanstone.jupin.messagebox.MessageBox;
import com.vanstone.jupin.messagebox.MessageBoxManager;

/**
 * @author shipeng
 */
@Service("commonSDKManager")
public class CommonSDKManagerImpl implements CommonSDKManager {

	@Autowired
	private ColorTableService colorTableService;
	@Autowired
	private SizeService sizeService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private CategoryService categoryService;
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.business.sdk.common.CommonSDKManager#getAndValidateColor(int)
	 */
	@Override
	public Color getColorAndValidate(int id) {
		Color color = this.colorTableService.getColor(id);
		MyAssert4Business.notNull(color);
		return color;
	}
	
	@Override
	public SizeTemplate getSizeTemplateAndValidate(int id) {
		SizeTemplate sizeTemplate = this.sizeService.getSizeTemplate(id);
		MyAssert4Business.notNull(sizeTemplate);
		return sizeTemplate;
	}
	
	@Override
	public Size getSizeAndValidate(int id) {
		Size size = this.sizeService.getSize(id);
		MyAssert4Business.notNull(size);
		return size;
	}

	@Override
	public Brand getBrandAndValidate(int id) {
		Brand brand = this.brandService.getBrand(id);
		MyAssert4Business.notNull(brand);
		return brand;
	}

	@Override
	public ProductCategoryDetail getAndValidateProductCategoryDetail(int id) {
		ProductCategoryDetail productCategoryDetail = this.categoryService.getProductCategoryDetail(id);
		MyAssert4Business.notNull(productCategoryDetail);
		return productCategoryDetail;
	}

	@Override
	public Message readMessage() {
		return readMessage(null, null);
	}
	
	@Override
	public Collection<Message> readAllMessages() {
		return readAllMessages(null, null);
	}
	
	@Override
	public Message readMessage(String messageBoxGroup, String messageBoxName) {
		if (messageBoxGroup == null) {
			messageBoxGroup = Constants.DEFAULT_MESSGEBOX_GROUP;
		}
		if (messageBoxName == null || messageBoxName.equals("")) {
			messageBoxName = Constants.DEFAULT_MESSGEBOX_NAME;
		}
		MessageBox messageBox = MessageBoxManager.getInstance().getMessageBox(messageBoxGroup, messageBoxName);
		if (messageBox == null) {
			return null;
		}
		return messageBox.read();
	}

	@Override
	public Collection<Message> readAllMessages(String messageBoxGroup, String messageBoxName) {
		if (messageBoxGroup == null) {
			messageBoxGroup = Constants.DEFAULT_MESSGEBOX_GROUP;
		}
		if (messageBoxName == null || messageBoxName.equals("")) {
			messageBoxName = Constants.DEFAULT_MESSGEBOX_NAME;
		}
		MessageBox messageBox = MessageBoxManager.getInstance().getMessageBox(messageBoxGroup, Constants.DEFAULT_MESSGEBOX_NAME);
		if (messageBox == null) {
			return null;
		}
		Collection<Message> messages = new ArrayList<Message>();
		while (true) {
			Message message = messageBox.read();
			if (message == null) {
				break;
			}
			messages.add(message);
		}
		if (messages == null || messages.size() <= 0) {
			return null;
		}
		return messages;
	}
	
}
