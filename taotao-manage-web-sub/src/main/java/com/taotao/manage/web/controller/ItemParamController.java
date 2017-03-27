package com.taotao.manage.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemParamService;
import com.taotao.manage.web.bean.EasyUIResult;

@RequestMapping("item/param")
@Controller
public class ItemParamController {

	@Autowired
	private ItemParamService itemParamService;
	/**
	 * 根据类目ID查询该类目的模板
	 * @param itemCatId
	 * @return
	 */
	@RequestMapping(value = "{itemCatId}",method = RequestMethod.GET)
	public ResponseEntity<ItemParam> queryItemParamByItemCatId(@PathVariable("itemCatId")Long itemCatId){
		
		try {
			ItemParam itemParam = this.itemParamService.queryItemParamByItemCatId(itemCatId);
			return ResponseEntity.ok(itemParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/**
	 * 保存模板数据
	 * @param itemCatId
	 * @param paramData
	 * @return
	 */
	@RequestMapping(value = "{itemCatId}",method = RequestMethod.POST)
	public ResponseEntity<Void> saveItemParam(@PathVariable("itemCatId")Long itemCatId,@RequestParam("paramData")String paramData){
		
		try {
			this.itemParamService.saveItemParam(itemCatId,paramData);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	/**
	 * 分页查询规格参数模板
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<EasyUIResult> queryItemParamByPage(@RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="rows",defaultValue="30")Integer rows){
		try {
			PageInfo<ItemParam> pageInfo = this.itemParamService.queryListByPage(null, page, rows);
			EasyUIResult easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
			return ResponseEntity.ok(easyUIResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
