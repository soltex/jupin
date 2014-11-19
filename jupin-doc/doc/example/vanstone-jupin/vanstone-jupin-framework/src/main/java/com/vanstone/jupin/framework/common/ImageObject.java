/**
 * 
 */
package com.vanstone.jupin.framework.common;

import com.vanstone.weedfs.client.WeedFile;

/**
 * 图片对象
 * @author shipeng
 */
public class ImageObject {
	/** WeedFile文件*/
	private WeedFile imageWeedFile;
	/** 图片文件宽度*/
	private int width;
	/** 图片文件高度*/
	private int height;
	
	public WeedFile getImageWeedFile() {
		return imageWeedFile;
	}

	public void setImageWeedFile(WeedFile imageWeedFile) {
		this.imageWeedFile = imageWeedFile;
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
