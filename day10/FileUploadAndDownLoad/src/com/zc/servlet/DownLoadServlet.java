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
	//寰楀埌瑕佷笅杞界殑鏂囦欢鍚�
	String fileName = req.getParameter("filename");
	//涓婁紶鐨勬枃浠堕兘淇濆瓨鍦ㄤ簡/WEB-INF/upload鐩綍鐨勫瓙鐩綍涓�
	//mybaby1,hululu
	//hjhjhj
	//1
	//2
	//9
	String fileSaveRootPath = this.getServletContext().getRealPath("/WEB-INF/upload");
	//閫氳繃鏂囦欢鍚嶆壘鍑烘枃浠剁殑鎵�鍦ㄧ洰褰�
	String path = FileUtil.makePath(fileName, fileSaveRootPath);
	System.out.println(path+File.separator+fileName);
	//寰楀埌涓嬭浇鐨勬枃浠�
	File file = new File(path+File.separator+fileName);
	//濡傛灉鏂囦欢涓嶅瓨鍦�
	if (!file.exists()) {
		req.setAttribute("message", "鎮ㄨ涓嬭浇鐨勮祫婧愬凡缁忚鍒犻櫎锛侊紒");
		req.getRequestDispatcher("/message.jsp").forward(req, resp);
		return;
	}
	//濡傛灉鏂囦欢瀛樺湪
	String realname = fileName.substring(fileName.indexOf("_")+1);
	//璁剧疆鍝嶅簲澶�
	resp.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(realname,"UTF-8"));
	//璇诲彇瑕佷笅杞界殑鏂囦欢锛屼繚瀛樺埌鏂囦欢杈撳叆娴�
	FileInputStream in = new FileInputStream(path+File.separator+fileName);
	//鍒涘缓杈撳嚭娴�
	OutputStream out= resp.getOutputStream();
	//鍒涘缓缂撳啿鍖�
	byte buffer[] = new byte[1024];
	int len = 0;
	//寰幆灏嗚緭鍏ユ祦涓殑鍐呭璇诲彇鍒扮紦鍐插尯鍐�
	while ((len= in.read(buffer))>0) {
		//杈撳嚭缂撳啿鍖哄唴瀹瑰埌娴忚鍣ㄤ腑锛屽疄鐜版枃浠朵笅杞�
		out.write(buffer,0,len);
	}
	//鍏抽棴鏂囦欢杈撳叆娴�
	in.close();
	//鍏抽棴杈撳嚭娴�
	out.close();
	
	
	


}
}
