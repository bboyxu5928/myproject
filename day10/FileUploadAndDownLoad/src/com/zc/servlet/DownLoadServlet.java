package com.zc.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DownLoadServlet")
public class DownLoadServlet extends HttpServlet{
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	//得到要下载的文件名
	String fileName = req.getParameter("filename");
	//上传的文件都保存在了/WEB-INF/upload目录的子目录下
	String fileSaveRootPath = this.getServletContext().getRealPath("/WEB-INF/upload");
	//通过文件名找出文件的所在目录
	String path = FileUtil.makePath(fileName, fileSaveRootPath);
	System.out.println(path+File.separator+fileName);
	//得到下载的文件
	File file = new File(path+File.separator+fileName);
	//如果文件不存在
	if (!file.exists()) {
		req.setAttribute("message", "您要下载的资源已经被删除！！");
		req.getRequestDispatcher("/message.jsp").forward(req, resp);
		return;
	}
	//如果文件存在
	String realname = fileName.substring(fileName.indexOf("_")+1);
	//设置响应头
	resp.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(realname,"UTF-8"));
	//读取要下载的文件，保存到文件输入流
	FileInputStream in = new FileInputStream(path+File.separator+fileName);
	//创建输出流
	OutputStream out= resp.getOutputStream();
	//创建缓冲区
	byte buffer[] = new byte[1024];
	int len = 0;
	//循环将输入流中的内容读取到缓冲区内
	while ((len= in.read(buffer))>0) {
		//输出缓冲区内容到浏览器中，实现文件下载
		out.write(buffer,0,len);
	}
	//关闭文件输入流
	in.close();
	//关闭输出流
	out.close();
	
	
	


}
}
