package com.nanyou.weixin.api.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.nanyou.weixin.api.test.model.People;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/test")
public class SwaggerControllerDemo {

	@RequestMapping(value="/swagger",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "测试swagger接口文档实例,GET方法", notes = "name:\"DingXue\"")
	public JSONObject swaggerTestGet(@RequestParam(value="name",required=true) String name){
		JSONObject jo = new JSONObject();
		jo.put("你的名字叫", name);
		return jo;
	}
	
	@RequestMapping(value="/swagger",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "测试swagger接口文档实例,POST方法", notes = "{\"name\":\"丁薛\",\"sex\":\"男\"}")
	public JSONObject swaggerTestPost(@RequestBody People people){
		JSONObject jo = new JSONObject();
		jo.put("你的名字叫", people.getName());
		jo.put("你的性别为", people.getSex());
		return jo;
	}
	
	@RequestMapping(value="/swaggerFile",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "测试swagger接口文档上传文件", notes = "上传文件")
	public JSONObject swaggerTestFile(@RequestParam MultipartFile file){
		String picName = file.getOriginalFilename();
		JSONObject jo = new JSONObject();
		jo.put("url", picName);
		return jo;
	}
	
}
