package com.nanyou.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nanyou.admin.service.RubbishCatService;
import com.nanyou.common.model.RubbishCat;
import com.nanyou.framework.util.DBUtils;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/rubbish/cat")
public class RubbishCatController {
	
	@Autowired
	private RubbishCatService rubbishCatService;
	
	@RequestMapping(value="/listAll",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "显示所有垃圾分类列表", notes = "")
	public List<RubbishCat> listAll(){
		return rubbishCatService.listAllRubbishCat();
	}
	
	@RequestMapping(value="/listAll/{currentPage}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "分页显示所有垃圾分类列表", notes = "")
	public Map listAll(@PathVariable Integer currentPage){
		if(currentPage == null) {
			currentPage=1;
		}
		Map params = new HashMap();
		params.put("page", currentPage);
		return rubbishCatService.listRubbishCat(params);
	}
	
	@RequestMapping(value="/create",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "创建垃圾分类列表", notes = "{\"name\":\"报纸瓶子\",\"desc\":\"这是一个测试\"}")
	public Map create(@RequestBody RubbishCat rubbishCat){
		Map result = new HashMap();
		try{
			rubbishCat.setCatId(DBUtils.getNextLongID());
			rubbishCatService.createRubbishCat(rubbishCat);
			result.put("result", true);
		}catch(Exception e){
			e.printStackTrace();
			result.put("result", false);
		}
		return result;
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "更新垃圾分类列表", notes = "{\"catId\":\"1\",\"name\":\"报纸瓶子\",\"desc\":\"这是一个测试\"}")
	public Map update(@RequestBody RubbishCat rubbishCat){
		Map result = new HashMap();
		try{
			rubbishCatService.updateRubbishCat(rubbishCat);
			result.put("result", true);
		}catch(Exception e){
			e.printStackTrace();
			result.put("result", false);
		}
		return result;
	}
	
	@RequestMapping(value="/delete/{catId}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据ID删除垃圾分类", notes = "")
	public Map update(@PathVariable Long catId){
		Map result = new HashMap();
		try{
			rubbishCatService.deleteRubbishCat(catId);
			result.put("result", true);
		}catch(Exception e){
			e.printStackTrace();
			result.put("result", false);
		}
		return result;
	}

}
