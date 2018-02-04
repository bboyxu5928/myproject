package com.zc.servlet;

import java.io.File;
import java.util.UUID;

public class FileUtil {
	public static String makeFileName(String filename) {
		//2.jpg
		//产生唯一的文件名
		return UUID.randomUUID().toString()+"_"+filename;
	}
	/**
	 * 进行使用hash进行打散存储
	 * filename
	 * savePath 文件存储路径
	 * return 新的存储路径
	 * */
	public static String makePath(String filename,String savePath) {
		//得到文件名的hashcode值,得到就是filename这个字符串对象在内存中地址
		int hashcode = filename.hashCode();
		int dir1 = hashcode&0xf;//0-15
		int dir2 = hashcode&0xf0>>4;
		//构造新的保存目录
		String dir =savePath +File.separator+dir1+File.separator+dir2;//upload\3\5
		File file = new File(dir);
		//如果目录不存在
		if (!file.exists()) {
			//创建目录
			file.mkdirs();
		}
		return dir;
	}
	
	
	
	
}
