/**
 * 
 */
package com.example.myproject.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 类的描述
 * </p>
 *
 * @author zhenglz 2016年11月25日
 *
 */
public class ZipUtil {

	public static File[] unzip(String zip, String fileNameCharset) throws ZipException {
		File zipFile = new File(zip);
		File parentDir = zipFile.getParentFile();
		return unzip(zipFile, parentDir.getAbsolutePath(), fileNameCharset);
	}

	public static File[] unzip(File zipFile, String dest) throws ZipException {
		return unzip(zipFile, dest, null, null);
	}

	public static File[] unzip(File zipFile, String dest, String fileNameCharset) throws ZipException {
		return unzip(zipFile, dest, fileNameCharset, null);
	}

	public static File[] unzip(File zipFile, String dest, String fileNameCharset, String passwd) throws ZipException {
		ZipFile zFile = new ZipFile(zipFile);

		if (StringUtils.isNotEmpty(fileNameCharset)) {
			zFile.setFileNameCharset(fileNameCharset);
		}

		if (!zFile.isValidZipFile()) {
			throw new ZipException("压缩文件不合法,可能被损坏.");
		}
		File destDir = new File(dest);
		if (destDir.isDirectory() && !destDir.exists()) {
			destDir.mkdir();
		}
		if (zFile.isEncrypted()) {
			zFile.setPassword(passwd.toCharArray());
		}
		zFile.extractAll(dest);

		@SuppressWarnings("unchecked")
		List<FileHeader> headerList = zFile.getFileHeaders();
		List<File> extractedFileList = new ArrayList<File>();
		for (FileHeader fileHeader : headerList) {
			if (!fileHeader.isDirectory()) {
				extractedFileList.add(new File(destDir, fileHeader.getFileName()));
			}
		}
		File[] extractedFiles = new File[extractedFileList.size()];
		extractedFileList.toArray(extractedFiles);
		return extractedFiles;
	}
}
