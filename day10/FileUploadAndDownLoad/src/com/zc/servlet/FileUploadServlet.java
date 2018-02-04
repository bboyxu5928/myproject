package com.zc.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.catalina.core.ApplicationPart;

@WebServlet("/FileUploadServlet")
@MultipartConfig
public class FileUploadServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String savePath=this.getServletContext().getRealPath("/WEB-INF/upload");
		File file = new File(savePath);
		//判断上传文件的保存目录是否存在
		if (!file.exists()&&!file.isDirectory()) {
			file.mkdirs();
		}
		
		//设置编码
		resp.setContentType("text/html;charset=UTF-8");
		//获得打印输出流
		PrintWriter out = resp.getWriter();
//		//单个文件上传方式
//		//获得jsp页面文件
//		Part p= req.getPart("file");
//		//进行强制转换
//		ApplicationPart ap = (ApplicationPart)p;
//		//获得文件名
//		String fname1 = ap.getSubmittedFileName();
//		//写入文件
//		p.write("C:\\Users\\Administrator\\Desktop\\"+fname1);
//		out.write("文件上传成功");
		//多个文件上传
		Collection<Part> parts = req.getParts();
		//进行遍历
		for (Part part : parts) {
			System.out.println("开始上传"+part.getName());
			//需要进行判断，如果不判断会崩溃的
			if (part.getName().trim().equals("file")&&part.getSize()>0) {
				//进行强制转换
				ApplicationPart ap = (ApplicationPart)part;
				//获得文件名称
				String fname1 = ap.getSubmittedFileName();
				System.out.println(fname1);
				//处理在不同浏览器下的错误
				fname1 = fname1.substring(fname1.lastIndexOf(File.separator)+1);
				//写入文件
				part.write(savePath+File.separator+fname1);
				out.write("文件上传成功");
				
			}else {
				//获得其他参数
				String username = req.getParameter("username");
				System.out.println(username);
			}
		
		}

	}
}
