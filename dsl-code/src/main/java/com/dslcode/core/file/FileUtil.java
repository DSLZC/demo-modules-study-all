package com.dslcode.core.file;

import com.dslcode.core.date.DateUtil;
import com.dslcode.core.random.RandomCode;
import com.dslcode.core.string.StringUtil;
import com.dslcode.core.util.NullUtil;
import org.apache.commons.io.FileExistsException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 文件操作Util
 * @author 董思林
 * @date 2016-07-14
 */
public final class FileUtil {

	/** 文件路径分隔符 */
	public static final char PATH_SEPARATOR = File.separatorChar;
	/** 缓存区大小 */
	private static final int BUFFER_SIZE = 40960;
	
	/**
	 * 获取文件后缀名
	 * @param file
	 * @param dian 后缀是否包含.  如：true —— .text .doc .xml；false —— text doc xml
	 * @return
	 * @throws Exception 
	 */
	public static String suffix(File file, boolean dian) throws Exception{
		return suffix(file.getName(), dian);
	}
	
	/**
	 * 获取文件后缀名
	 * @param pathOrName 文件路径或文件名
	 * @param dian 后缀是否包含.  如：true —— .text .doc .xml；false —— text doc xml
	 * @return
	 */
	public static String suffix(String pathOrName, boolean dian) throws Exception{
		if(NullUtil.isNull(pathOrName)) throw new NullPointerException("文件路径或文件名为空...");
		int index = pathOrName.lastIndexOf(".");
		if(index < 0) throw new Exception("文件路径或文件名错误...");
		return pathOrName.substring(dian ? pathOrName.lastIndexOf(".") : pathOrName.lastIndexOf(".")+1, pathOrName.length());
	}

	/**
	 * 获取新的随机文件名
	 * @param pathOrName
	 * @return
	 */
	public static String getRandomFileName(String pathOrName) throws Exception{
		return StringUtil.append2String(DateUtil.nowStr(DateUtil.yyMMddHHmmssSSS), RandomCode.getNumCode(2), suffix(pathOrName, true));
	}

	/**
	 * 根据文件路径获取该文件
	 * @param sourcePath 文件绝对路径
	 * @param exist 文件存在与否  0必须不存在  1必须存在    其他值不关心
	 * @return
	 * @throws FileNotFoundException
	 */
	public static File getFile(String sourcePath, int exist) throws Exception{
		File sourceFile = new File(sourcePath);
		if(exist == 1 && !sourceFile.exists()) throw new FileNotFoundException("文件不存在...");
		if(exist == 0 && sourceFile.exists()) throw new FileExistsException("文件已经存在...");
		return sourceFile;
	}
	/**
	 * 复制文件
	 * @param sourcePath 源文件绝对路径
	 * @param targetPath 目标文件绝对路径
	 * @param exist 目标文件存在与否  0必须不存在  1必须存在    其他值不关心
	 * @throws Exception
	 */
	public static void copy(String sourcePath, String targetPath, int exist) throws Exception{
		InputStream is = new FileInputStream(getFile(sourcePath, 1));
		copy(is, targetPath, exist);
		if(null != is) is.close();
	}
	
	/**
	 * 复制文件
	 * @param is 源文件输入流
	 * @param targetPath 目标文件绝对路径
	 * @param exist 目标文件存在与否  0必须不存在  1必须存在    其他值不关心
	 * @throws Exception
	 */
	public static void copy(InputStream is, String targetPath, int exist) throws Exception{
		OutputStream os = new FileOutputStream(getFile(targetPath, exist));
		copy(is, os);
		if(null != os) os.close();
	}
	
	/**
	 * 复制文件
	 * @param is 源文件输入流
	 * @param os 目标文件输出流
	 * @throws Exception
	 */
	public static void copy(InputStream is, OutputStream os) throws Exception{
		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;
		while ((bytesRead = is.read(buffer)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.flush();
	}
	
	/**
	 * 重命名文件
	 * @param sourcePath 源文件绝对路径
	 * @param newName 重命名文件的新文件名，注：不需要文件的后缀
	 * @throws Exception
	 */
	public static void rename(String sourcePath, String newName) throws Exception{
		rename(getFile(sourcePath, 1), newName);
	}
	
	/**
	 * 重命名文件
	 * @param sourceFile 源文件
	 * @param newName 重命名文件的新文件名，注：不需要文件的后缀
	 * @throws Exception
	 */
	public static boolean rename(File sourceFile, String newName) throws Exception{
		if(NullUtil.isNull(newName)) throw new NullPointerException("新文件名为空...");
		if(!sourceFile.exists()) throw new FileNotFoundException("源文件不存在...");
		StringBuffer targetPath = new StringBuffer();
		targetPath.append(sourceFile.getParent()).append(PATH_SEPARATOR).append(newName).append(suffix(sourceFile.getName(), true));
		File targetFile = getFile(targetPath.toString(), 0);
		return sourceFile.renameTo(targetFile);
	}
	
	/**
	 * 文件下载
	 * @param sourcePath 源文件绝对路径
	 * @param fileName 文件名称
	 * @param fileType 文件路径
	 * @param response
	 * @throws Exception
	 */
	public static void download(String sourcePath, String fileName, String fileType, HttpServletResponse response) throws Exception{
		InputStream is = new FileInputStream(getFile(sourcePath, 1));
		download(is, fileName, fileType, response);
		if(null != is) is.close();
	}
	
	/**
	 * 文件下载
	 * @param is 源文件输入流
	 * @param fileName 文件名称
	 * @param fileType 文件路径
	 * @param response
	 * @throws Exception
	 */
	public static void download(InputStream is, String fileName, String fileType, HttpServletResponse response) throws Exception{
		response.setContentType(fileType + "; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
		OutputStream os = response.getOutputStream();
		copy(is, os);
		if(null != os) os.close();
	}

	/**
	 * 判断图片尺寸，宽高
	 * @param inputStream
	 * @param width
	 * @param height
	 * @return
	 */
	public static boolean imageWidthAndHeight(InputStream inputStream, int width, int height) throws Exception {
		BufferedImage image = ImageIO.read(inputStream);
		if(NullUtil.isNull(image)) throw new NullPointerException("图片为空...");
		if(image.getWidth() != width) throw new Exception("图片宽度不正确...");
		if(image.getHeight() != height) throw new Exception("图片高度不正确...");
		return true;
	}

}
