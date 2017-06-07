package com.nanyou.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nanyou.admin.service.RubbishService;
import com.nanyou.common.model.Rubbish;
import com.nanyou.framework.util.DBUtils;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/rubbish/rub")
public class RubbishController {

	@Autowired
	private RubbishService rubbishService;
	
	@RequestMapping(value="/list/{catId}/{currentPage}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据垃圾分类catId显示垃圾(分页)", notes = "")
	public Map listAll(@PathVariable Long catId,@PathVariable Integer currentPage){
		if(currentPage == null) {
			currentPage=1;
		}
		Map params = new HashMap();
		params.put("page", currentPage);
		params.put("catId", catId);
		return rubbishService.listRubbishByCatId(params);
	}
	
	@RequestMapping(value="/create",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "创建垃圾", notes = "{\"catId\":6,\"name\":\"二手手机\",\"icon\":\"http://img2.imgtn.bdimg.com/it/u=467663173,4294283183&fm=26&gp=0.jpg\",\"desc\":\"这是一个测试\"}")
	public Map create(@RequestBody Rubbish rubbish){
		Map result = new HashMap();
		try{
			rubbish.setRubbishId(DBUtils.getNextLongID());
			rubbishService.createRubbish(rubbish);
			result.put("result", true);
		}catch(Exception e){
			e.printStackTrace();
			result.put("result", false);
		}
		return result;
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "更新垃圾", notes = "{\"rubbishId\":7,\"catId\":6,\"name\":\"二手手机\",\"icon\":\"http://img2.imgtn.bdimg.com/it/u=467663173,4294283183&fm=26&gp=0.jpg\",\"desc\":\"这是一个测试\"}")
	public Map update(@RequestBody Rubbish rubbish){
		Map result = new HashMap();
		try{
			rubbishService.updateRubbish(rubbish);
			result.put("result", true);
		}catch(Exception e){
			e.printStackTrace();
			result.put("result", false);
		}
		return result;
	}
	
	@RequestMapping(value="/delete/{rubbishId}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据ID删除垃圾", notes = "")
	public Map update(@PathVariable Long rubbishId){
		Map result = new HashMap();
		try{
			rubbishService.deleteRubbish(rubbishId);
			result.put("result", true);
		}catch(Exception e){
			e.printStackTrace();
			result.put("result", false);
		}
		return result;
	}
	
	@RequestMapping(value="/get/{rubbishId}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据ID得到垃圾", notes = "")
	public Map get(@PathVariable Long rubbishId){
		Map result = new HashMap();
		try{
			result.put("data", rubbishService.getRubbish(rubbishId));
			result.put("result", true);
		}catch(Exception e){
			e.printStackTrace();
			result.put("result", false);
		}
		return result;
	}
	
}
