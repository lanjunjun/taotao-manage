package com.taotao.manage.web.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manage.service.PropertiesService;
import com.taotao.manage.web.bean.PicUploadResult;

@Controller
@RequestMapping("pic/upload")
public class PicUploadController {
	
	//操作json对象
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Autowired
	private PropertiesService propertiesService;
	
	//图片格式
	private static final String[] IMAGE_TYPE = {".jpg",".jpeg",".png",".bmp",".gif"};

	@RequestMapping(method = RequestMethod.POST,produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String upload(@RequestParam("uploadFile")MultipartFile uploadFile) throws IllegalStateException, IOException{
		
		Boolean flag = false;
		//图片后缀的校验
		for(String type : IMAGE_TYPE){
			if(StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)){
				flag = true;
				break;
			}
		}
		PicUploadResult picUploadResult = new PicUploadResult();
		if(!flag){
			//上传成功error为0，失败为1
			picUploadResult.setError(1);
			String result = MAPPER.writeValueAsString(picUploadResult);
			return result;
		}
		//获取保存到服务器的地址
		String filePath = getFilePath(uploadFile.getOriginalFilename());
		File file = new File(filePath);
		//把上传的图片保存到服务器中
		uploadFile.transferTo(file);
		
		flag = false;
		//校验图片是否有宽和高
		try {
			BufferedImage image = ImageIO.read(file);
			if(image!=null){
				picUploadResult.setWidth(String.valueOf(image.getWidth()));
				picUploadResult.setHeight(String.valueOf(image.getHeight()));
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//如果校验失败，删除上传的图片
		if(!flag){
			file.delete();
		}
		//校验成功，error为0
		picUploadResult.setError(0);
		String picUrl = StringUtils.replace(StringUtils.substringAfter(filePath, this.propertiesService.IMAGE_FILE_PATH), "\\", "/");
		picUploadResult.setUrl(this.propertiesService.IMAGE_BASE_URL+picUrl);
		System.out.println(picUploadResult.getUrl());
		String result = MAPPER.writeValueAsString(picUploadResult);
		return result;
	}

	/**
	 * 获取保存到服务器的地址
	 * @param originalFilename
	 * @return
	 */
	//E:\taotao-upload\\image\\2017\\03\\12\\201703122119ssSSSSXXX.jpg
	private String getFilePath(String originalFilename) {
		String baseFolder = this.propertiesService.IMAGE_FILE_PATH + File.separator+"image";
		//拼接具体存放的地址
		String fileFolder = baseFolder + File.separator + new DateTime().toString("yyyy") 
				+ File.separator + new DateTime().toString("MM")
				+ File.separator + new DateTime().toString("dd");
		File file = new File(fileFolder);
		if(!file.isDirectory()){
			//创建文件夹
			file.mkdirs();
		}
		//图片名
		String fileName = new DateTime().toString("yyyyMMddssSSSS") + RandomUtils.nextInt(1000, 9999)+"."+StringUtils.substringAfterLast(originalFilename, ".");
		return fileFolder + File.separator + fileName;
	}
}
