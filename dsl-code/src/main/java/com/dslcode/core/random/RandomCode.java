package com.dslcode.core.random;

import java.util.Random;

/**
 * 生成随机字符串类
 * @author 董思林
 * @date 2016-07-13
 */
public class RandomCode {

	/** 所有的数字以及所有字母字符数组*/
	private static final char[] NUM_LETTER_ALL = {'1','2','3','4','5','6','7','8','9','q','w','e','r','t','y','u','i','p','a','s','d','f','g','h','j','k','z','x','c','v','b','n','m','Q','W','E','R','T','Y','U','I','P','K','J','H','G','F','D','S','A','Z','X','C','V','B','N','M'}; 
	/** 所有的数字以及所有字母字符数组的长度*/
	private static final int NUM_LETTER_ALL_LEN= 57;
	/** 所有的数字以及小写字母字符数组*/
	private static final char[] NUM_LETTER_L = {'1','2','3','4','5','6','7','8','9','q','w','e','r','t','y','u','i','p','a','s','d','f','g','h','j','k','z','x','c','v','b','n','m'}; 
	/** 所有的数字以及小写字母字符数组的长度*/
	private static final int NUM_LETTER_L_LEN= 33;
	/** 所有的数字字符数组*/
	private static final char[] NUM_ALL = {'1','2','3','4','5','6','7','8','9','0'}; 
	/** 所有的数字字符数组的长度*/
	private static final int NUM_ALL_LEN= 10;
	/** 小写字母字符数组*/
	private static final char[] LETTER_L = {'q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m'}; 
	/** 小写字母字符数组的长度*/
	private static final int LETTER_L_LEN= 26;
	/** 随机对象 */
	public static final Random random = new Random();
	
	/**
	 * 获取随机数字和所有字母混合字符串
	 * @param len
	 * @return
	 */
	public static String getNumLetterCode(int len){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<len; i++){
			sb.append(NUM_LETTER_ALL[random.nextInt(NUM_LETTER_ALL_LEN)]);
		}
		return sb.toString();
	}
	
	/**
	 * 获取随机数字和小写字母混合字符串
	 * @param len
	 * @return
	 */
	public static String getNumLetterLowerCode(int len){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<len; i++){
			sb.append(NUM_LETTER_L[random.nextInt(NUM_LETTER_L_LEN)]);
		}
		return sb.toString();
	}
	
	/**
	 * 获取随机小写字母混合字符串
	 * @param len
	 * @return
	 */
	public static String getLetterLowerCode(int len){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<len; i++){
			sb.append(LETTER_L[random.nextInt(LETTER_L_LEN)]);
		}
		return sb.toString();
	}
	
	/**
	 * 获取随机纯数字符串
	 * @param len
	 * @return
	 */
	public static String getNumCode(int len){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<len; i++){
			sb.append(NUM_ALL[random.nextInt(NUM_ALL_LEN)]);
		}
		return sb.toString();
	}

}
