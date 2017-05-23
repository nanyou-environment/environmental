package com.nanyou.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

public class FileManageUtils {

	/**
	 * 下载文件
	 * 
	 * @param response
	 * @param filePath
	 *            文件路径
	 * @param fileName
	 *            文件名称
	 * @throws IOException
	 */
	public static void exportFile(HttpServletResponse response,
			String filePath, String fileName) throws IOException {
		response.setContentType("application/csv;charset=GBK");
		response.setHeader("Content-Disposition", "attachment;  filename="
				+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		// URLEncoder.encode(fileName, "GBK")

		InputStream in = null;
		try {
			in = new FileInputStream(filePath);
			int len = 0;
			byte[] buffer = new byte[1024];
			response.setCharacterEncoding("GBK");
			OutputStream out = response.getOutputStream();
			while ((len = in.read(buffer)) > 0) {
				// out.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF
				// });
				out.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * 删除该目录filePath下的�?��文件
	 * 
	 * @param filePath
	 *            文件目录路径
	 */
	public static void deleteFiles(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					files[i].delete();
				}
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param filePath
	 *            文件目录路径
	 * @param fileName
	 *            文件名称
	 */
	public static void deleteFile(String filePath, String fileName) {
		File file = new File(filePath);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					if (files[i].getName().equals(fileName)) {
						files[i].delete();
						return;
					}
				}
			}
		}
	}
}
