package com.taotao.manage.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;

@RequestMapping("item/cat")
@Controller
public class ItemCatController {
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Autowired
	private ItemCatService itemCatService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ItemCat>> queryItemCatByParentId(@RequestParam(value="id",defaultValue="0") Long parentId){
		try {
			List<ItemCat> list = this.itemCatService.queryItemCatByParentId(parentId);
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/**
	 * 查询所有类目，封装到前台ItemCatResult中，前台首页左侧菜单显示
	 * @return
	 */
	@RequestMapping(value="all",method=RequestMethod.GET)
	public ResponseEntity<ItemCatResult> queryItemCatAll(){
		try {
			ItemCatResult itemCatResult = this.itemCatService.queryItemCatAll();
			return ResponseEntity.ok(itemCatResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
//	@RequestMapping(value="all",method=RequestMethod.GET)
//	public ResponseEntity<String> queryItemCatAll(HttpServletRequest request,HttpServletResponse response){
//		try {
//			response.setCharacterEncoding("utf-8");
//			ItemCatResult itemCatResult = this.itemCatService.queryItemCatAll();
//			String callback = request.getParameter("callback");
//			String result = MAPPER.writeValueAsString(itemCatResult);
//			result = callback+"("+result+")";
//			//return ResponseEntity.ok(new String(result.getBytes(), "ISO-8859-1"));
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//	}

}
