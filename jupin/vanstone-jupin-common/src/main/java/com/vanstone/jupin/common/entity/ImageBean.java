/**
 * 
 */
package com.vanstone.jupin.common.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.commons.io.FilenameUtils;

import com.vanstone.common.util.image.ImagePropertyVO;
import com.vanstone.common.util.image.ImageUtil;
import com.vanstone.jupin.common.ImageFormatException;
import com.vanstone.jupin.common.WeedFSException;
import com.vanstone.weedfs.client.RequestResult;
import com.vanstone.weedfs.client.WeedFile;
import com.vanstone.weedfs.client.impl.WeedFSClient;

/**
 * 图片信息
 * @author shipeng
 */
public class ImageBean {

	/**WeedFile*/
	private WeedFile weedFile;
	/**宽度*/
	private int width;
	/**高度*/
	private int height;
	
	public ImageBean(WeedFile weedFile, int width, int height) {
		this.weedFile = weedFile;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * 创建ImageBean
	 * @param file
	 * @return
	 * @throws FileNotFoundException, ImageFormatException 
	 */
	public static ImageBean create(File file) throws FileNotFoundException, ImageFormatException, WeedFSException {
		ImagePropertyVO vo = null;
		try {
			vo = ImageUtil.getProperty(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new ImageFormatException();
		}
		WeedFSClient weedFSClient = new WeedFSClient();
		RequestResult requestResult = null;
		try {
			requestResult = weedFSClient.upload(file);
		} catch (Exception e) {
			throw new WeedFSException();
		}
		if (requestResult == null || !requestResult.isSuccess()) {
			throw new WeedFSException();
		}
		WeedFile weedFile = new WeedFile(requestResult.getFid(), FilenameUtils.getExtension(file.getName()));
		return new ImageBean(weedFile, vo.getWidth(), vo.getHeight());
	}
	
	public WeedFile getWeedFile() {
		return weedFile;
	}

	public void setWeedFile(WeedFile weedFile) {
		this.weedFile = weedFile;
	}

	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
