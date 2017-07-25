package com.dslcode.core.file;

import com.dslcode.core.date.DateUtil;
import com.dslcode.core.io.StreamUtil;
import com.dslcode.core.random.RandomCode;
import com.dslcode.core.string.StringUtil;
import org.apache.commons.io.FileExistsException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

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
	 * @param isContainsDian 后缀是否包含.  如：true —— .text .doc .xml；false —— text doc xml
	 * @return
	 * @throws Exception 
	 */
	public static String suffix(File file, boolean isContainsDian) throws Exception{
		return suffix(file.getName(), isContainsDian);
	}
	
	/**
	 * 获取文件后缀名
	 * @param pathOrName 文件路径或文件名
	 * @param isContainsDian 后缀是否包含.  如：true —— .text .doc .xml；false —— text doc xml
	 * @return
	 */
	public static String suffix(String pathOrName, boolean isContainsDian) throws Exception{
		if(StringUtil.isNull(pathOrName)) throw new NullPointerException("文件路径或文件名为空...");
		int index = pathOrName.lastIndexOf(".");
		if(index < 0) throw new Exception("文件路径或文件名错误...");
		return pathOrName.substring(isContainsDian ? pathOrName.lastIndexOf(".") : pathOrName.lastIndexOf(".")+1, pathOrName.length());
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
	 * @param option 文件操作模式
	 * @return
	 * @throws FileNotFoundException
	 */
	public static File getFile(String sourcePath, StandardOpenOption option) throws Exception{
		File sourceFile = new File(sourcePath);
		if(option == StandardOpenOption.READ && !sourceFile.exists()) throw new FileNotFoundException("文件不存在...");
		if(option == StandardOpenOption.CREATE_NEW && sourceFile.exists()) throw new FileExistsException("文件已经存在...");
		return sourceFile;
	}
	/**
	 * 复制文件
	 * @param sourcePath 源文件绝对路径
	 * @param targetPath 目标文件绝对路径
	 * @param option 目标文件模式
	 * @throws Exception
	 */
	public static void copy(String sourcePath, String targetPath, StandardOpenOption option) throws Exception{
		InputStream is = new FileInputStream(getFile(sourcePath, option));
		copy(is, targetPath, option);
		if(null != is) is.close();
	}
	
	/**
	 * 复制文件
	 * @param is 源文件输入流
	 * @param targetPath 目标文件绝对路径
	 * @param option 目标文件模式
	 * @throws Exception
	 */
	public static void copy(InputStream is, String targetPath, StandardOpenOption option) throws Exception{
		OutputStream os = new FileOutputStream(getFile(targetPath, option));
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
		// NIO 方式
		if (is instanceof FileInputStream && os instanceof FileOutputStream){
			FileChannel inChannel = null;
			FileChannel outChannel = null;
			try {
				// 获取通道
				inChannel = ((FileInputStream)is).getChannel();
				outChannel = ((FileOutputStream)os).getChannel();
				// 分配指定大小的缓冲区
				ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
				// 将通道中的数据存入缓冲区中
				while(inChannel.read(byteBuffer) != -1){
					byteBuffer.flip();	// 切换读取数据的模式
					outChannel.write(byteBuffer);	// 将缓冲区中的数据写入通道中
					byteBuffer.clear();	//清空缓冲区
				}
			} finally {
				StreamUtil.close(inChannel, outChannel);
			}
		} else {
			// 传统流方式
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead;
			while ((bytesRead = is.read(buffer)) != -1) os.write(buffer, 0, bytesRead);
			os.flush();
		}

	}
	
	/**
	 * 重命名文件
	 * @param sourcePath 源文件绝对路径
	 * @param newName 重命名文件的新文件名，注：不需要文件的后缀
	 * @throws Exception
	 */
	public static void rename(String sourcePath, String newName) throws Exception{
		rename(getFile(sourcePath, StandardOpenOption.READ), newName);
	}
	
	/**
	 * 重命名文件
	 * @param sourceFile 源文件
	 * @param newName 重命名文件的新文件名，注：不需要文件的后缀
	 * @throws Exception
	 */
	public static boolean rename(File sourceFile, String newName) throws Exception{
		if(StringUtil.isNull(newName)) throw new NullPointerException("新文件名为空...");
		if(!sourceFile.exists()) throw new FileNotFoundException("源文件不存在...");
		StringBuffer targetPath = new StringBuffer();
		targetPath.append(sourceFile.getParent()).append(PATH_SEPARATOR).append(newName).append(suffix(sourceFile.getName(), true));
		File targetFile = getFile(targetPath.toString(), StandardOpenOption.CREATE_NEW);
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
		InputStream is = new FileInputStream(getFile(sourcePath, StandardOpenOption.READ));
		download(is, fileName, fileType, response);
		StreamUtil.close(is);
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
		StreamUtil.close(os);
	}

}
