package com.zc.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/UploadHandleServlet")
public class UploadHandleServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub11
		//得到上传文件的保存路径，将上传文件存到WEB-INF目录下，也可以放到指定目录下
		String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
		File file = new File(savePath);
		//判断上传文件的保存目录是否存在。
		if (!file.exists()&&!file.isDirectory()) {
			//如果不存在就进行创建
			file.mkdirs();
		}
		//消息提示
		String message="";
		//加载上传组件
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//1创建文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			//2解决上传文件中文乱码
			upload.setHeaderEncoding("UTF-8");
			//监听文件上传进度(新增)
			upload.setProgressListener(new ProgressListener() {
				
				@Override
				public void update(long arg0, long arg1, int arg2) {
					// TODO Auto-generated method stub
					System.out.println("文件大小："+arg1+"当前已经处理："+arg0);
				}
			});
			
			
			
			
			//3判断提交上来的数据是否是上传表单数据
			if (!ServletFileUpload.isMultipartContent(req)) {
				//如果不是，则按照传统方式进行获取
				return;
			}
			
			//设置单个文件大小的最大值 1MB(新增)
			upload.setFileSizeMax(1024*1024);
			//设置上传文件总量的最大值  最大值=同时上传多个文件的大小的最大值的和(新增)
			upload.setSizeMax(1024*1024*10);
			
			
			
			//4 使用ServletFileUpload解析器解析上传数据
			List<FileItem> list = upload.parseRequest(req);
			//遍历
			for (FileItem fileItem : list) {
				//如果fileItem中封装的是普通输入项的数据
				if (fileItem.isFormField()) {
					//获得其中的name
					String name= fileItem.getFieldName();
					//解决普通项输入数据中文乱码问题
					String value = fileItem.getString("UTF-8");
					System.out.println(name+"~~~"+value);
				
				}else {
					//如果fileitem中封装是上传文件，得到上传文件
					String fileName = fileItem.getName();
					if (fileName==null||fileName.trim().equals("")) {
						//文件名为null或者去掉空格后，是为空，则跳转本次循环
						continue;
					}
					//注意：不同浏览器提交的文件名是不一样的有些是带路径的，有些是单纯文件名
					//处理只有文件名
					fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
					File filePath = new File(savePath+File.separator+fileName);
					//在这里写入2种办法
					//第一种直接写入，不管其中过程
//					fileItem.write(filePath);
					//第二种创建输入输出流
					InputStream in = fileItem.getInputStream();
					//得到文件保存的名称（新增）
					String saveFilename = FileUtil.makeFileName(fileName);
					//得到文件的保存目录（新增）
					String realSavePath = FileUtil.makePath(saveFilename, savePath);
					
					
					
					//创建文件输出流
					FileOutputStream out = new FileOutputStream(realSavePath+File.separator+saveFilename);
					//创建缓冲流
					byte buffer[] = new byte[1024];
					//判断流是否读取完成
					int len = 0;
					//循环写入
					while ((len=in.read(buffer))>0) {
						out.write(buffer, 0, len);
					}
					//关闭流
					in.close();
					out.close();
					//删除处理文件上传时候的临时文件
					fileItem.delete();
					message="文件上传成功";
				}
			}

		}catch (FileUploadBase.FileSizeLimitExceededException e) {
			// TODO: handle exception
			message="单个文件超出最大值";
		}catch (FileUploadBase.SizeLimitExceededException e) {
			// TODO: handle exception
			message="上传文件总的大小超出限制的最大值";
		}
		
		catch (Exception e) {
			// TODO: handle exception
			message="文件上传失败";
		}
		req.setAttribute("message", message);
		req.getRequestDispatcher("/message.jsp").forward(req, resp);
	}

}
