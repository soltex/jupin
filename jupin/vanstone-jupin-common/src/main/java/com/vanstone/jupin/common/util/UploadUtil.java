/**
 * 
 */
package com.vanstone.jupin.common.util;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vanstone.business.MyAssert4Business;
import com.vanstone.fs.FSException;
import com.vanstone.fs.FSFile;
import com.vanstone.fs.FSType;
import com.vanstone.jupin.common.ImageFormatException;
import com.vanstone.jupin.common.WeedFSException;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.webframework.utils.FSManagerExt;
import com.vanstone.weedfs.client.RequestResult;
import com.vanstone.weedfs.client.WeedFile;
import com.vanstone.weedfs.client.impl.WeedFSClient;

/**
 * @author shipeng
 *
 */
public class UploadUtil {
	
	/**
	 * 文件上传并上传到WeedFS中
	 * @param multipartFile
	 * @return
	 * @throws FSException 
	 */
	public static WeedFile uploadFileThenWeedFS(MultipartFile multipartFile) throws FSException {
		MyAssert4Business.notNull(multipartFile);
		FSFile fsFile = FSManagerExt.getInstance().uploadBySpring(multipartFile, FSType.Temporary);
		WeedFSClient weedFSClient = new WeedFSClient();
		RequestResult requestResult = weedFSClient.upload(fsFile.getFile());
		if (requestResult == null || !requestResult.isSuccess()) {
			return null;
		}
		WeedFile weedFile = new WeedFile(requestResult.getFid(), fsFile.getExtensionName());
		return weedFile;
	}
	
	/**
	 * 批量上传文件并传到WeedFS中
	 * @param multipartFiles
	 * @return
	 * @throws FSException 
	 */
	public static List<WeedFile> batchUploadFilesThenWeedFS(List<MultipartFile> multipartFiles) throws FSException {
		MyAssert4Business.notEmpty(multipartFiles);
		Collection<FSFile> fsFiles = new ArrayList<FSFile>();
		for (MultipartFile multipartFile : multipartFiles) {
			FSFile fsFile = FSManagerExt.getInstance().uploadBySpring(multipartFile, FSType.Temporary);
			fsFiles.add(fsFile);
		}
		List<WeedFile> weedFiles = new ArrayList<WeedFile>();
		WeedFSClient weedFSClient = new WeedFSClient();
		for (FSFile fsFile : fsFiles) {
			RequestResult requestResult = weedFSClient.upload(fsFile.getFile());
			if (requestResult == null || !requestResult.isSuccess()) {
				return null;
			}
			WeedFile weedFile = new WeedFile(requestResult.getFid(), fsFile.getExtensionName());
			weedFiles.add(weedFile);
		}
		return weedFiles;
	}
	
	/**
	 * 图片文件上传到WeedFS中
	 * @param multipartFile
	 * @return
	 * @throws ImageFormatException
	 * @throws FileNotFoundException 
	 * @throws WeedFSException 
	 * @throws FSException 
	 */
	public static ImageBean uploadImageFileThenWeedFS(MultipartFile multipartFile) throws ImageFormatException, WeedFSException, FileNotFoundException, FSException {
		MyAssert4Business.notNull(multipartFile);
		FSFile fsFile = FSManagerExt.getInstance().uploadBySpring(multipartFile, FSType.Temporary);
		return ImageBean.create(fsFile.getFile());
	}
	
	/**
	 * 批量图片文件上传并传到WeedFS中
	 * @param multipartFiles
	 * @return
	 * @throws ImageFormatException
	 * @throws FileNotFoundException 
	 * @throws WeedFSException 
	 * @throws FSException 
	 */
	public static List<ImageBean> batchUploadImageFilesThenWeedFS(List<MultipartFile> multipartFiles) throws ImageFormatException, WeedFSException, FileNotFoundException, FSException {
		MyAssert4Business.notEmpty(multipartFiles);
		Collection<FSFile> fsFiles = new ArrayList<FSFile>();
		for (MultipartFile multipartFile : multipartFiles) {
			FSFile fsFile = FSManagerExt.getInstance().uploadBySpring(multipartFile, FSType.Temporary);
			fsFiles.add(fsFile);
		}
		List<ImageBean> imagebeans = new ArrayList<ImageBean>();
		for (FSFile fsFile : fsFiles) {
			ImageBean imageBean = ImageBean.create(fsFile.getFile());
			imagebeans.add(imageBean);
		}
		return imagebeans;
	}
	
	/**
	 * 判断
	 * @param multipartFile
	 * @return
	 */
	public static boolean multipartFileExist(MultipartFile multipartFile) {
		if (multipartFile != null && multipartFile.getName() != null && !multipartFile.getName().equals("")
				&& multipartFile.getOriginalFilename() != null && !multipartFile.getOriginalFilename().equals("")) {
			return true;
		}
		return false;
	}
	
}
