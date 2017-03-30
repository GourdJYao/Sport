package com.yaojian.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.yaojian.model.User;
import com.yaojian.serverinterface.BaseService;

@Controller
@RequestMapping("/file")
public class UploadFileController {
	
	@ResponseBody 
	@RequestMapping("/upload")
	public Map<String,Object> upload(HttpServletRequest request,
			HttpServletResponse response) throws IllegalStateException,
			IOException {
		
		User user=(User) request.getSession().getAttribute("user");
		
		String pathDir = "/files/" +user.getUsername()+"/";  
		System.out.println("pathDir:"+pathDir);
         /**得到图片保存目录的真实路径**/      
		String logoRealPathDir = request.getSession().getServletContext().getRealPath(pathDir);
		System.out.println("logoRealPathDir:"+logoRealPathDir);
		File logoSaveFile = new File(logoRealPathDir);       
	    if(!logoSaveFile.exists()){       
	         logoSaveFile.mkdirs();
	    }
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		String fileName="";
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 记录上传过程起始时的时间，用来计算上传时间
				int pre = (int) System.currentTimeMillis();
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if (myFileName.trim() != "") {
						System.out.println(myFileName);
						// 重命名上传后的文件名
						 fileName = (UUID.randomUUID()+""+pre)+myFileName.substring(myFileName.lastIndexOf("."),myFileName.length());
						// 定义上传路径
						String path = logoSaveFile+"\\" + fileName;
						System.out.println("path:"+path);
						File localFile = new File(path);
						file.transferTo(localFile);
					}
				}
				// 记录上传该文件后的时间
				int finaltime = (int) System.currentTimeMillis();
				System.out.println(finaltime - pre);
			}

		}
		System.out.println("pathDir+fileName:"+(pathDir+fileName));
		BaseService baseService=new BaseService();
		HashMap<String,Object> jsonMap=new HashMap<String, Object>();
		jsonMap.put("photoUrl", pathDir.substring(1)+fileName);
		return baseService.getResposeBody("UPLOAD_FILE_REQ", jsonMap);
	}
}
