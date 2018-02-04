package com.zc.servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ListFileServlet")
public class ListFileServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//获取上传文件的目录
		String uploadFilePath = this.getServletContext().getRealPath("/WEB-INF/upload");
		//储存要下载的文件名
		Map<String, String> fileNameMap= new HashMap<>();
		System.out.println(uploadFilePath);
		//递归遍历所有文件名称
		listFile(new File(uploadFilePath), fileNameMap);
		//将Map集合发送的jsp界面		
		req.setAttribute("fileNameMap", fileNameMap);
		//跳转
		
		req.getRequestDispatcher("/listfile.jsp").forward(req, resp);
	}
	/**
	 * 递归遍历所有文件
	 * */
	public void listFile(File file,Map<String, String>map) {
		if (!file.exists()) {
			file.mkdirs();
		}
		//如果file代表的是不是一个文件而是一个目录
		if (!file.isFile()) {
			//列出该目录下所有文件和目录
			File files[] =file.listFiles();
			//遍历files[] 数组
			for (File f : files) {
				//执行递归
				listFile(f, map);
			}
		}else {
				/*
				 * 处理文件名 上传后文件是以uuid_文件名形式需要重新命名的，去掉文件名的uuid_部分
				 * file.getName().indexOf("_")检索字符串第一次出现"_"的位置
				 * */
				String readlName = file.getName().substring(file.getName().indexOf("_")+1);
				map.put(file.getName(), readlName);
			}
		}
}








