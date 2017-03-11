package com.taotao.manage.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;
import com.taotao.manage.web.bean.EasyUIResult;

@Controller
@RequestMapping("item")
public class ItemController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	ItemService itemService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveItem(Item item,
			@RequestParam("desc") String desc) {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("新增商品，传入的参数是：item = {},desc = {}", item, desc);
			}
			this.itemService.saveItem(item, desc);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("新增商品成功！itemId = {}", item.getId());
			}
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("新增商品失败，新增商品的信息是：item=" + item.toString(), e);
			}
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 分页查询商品数据
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<EasyUIResult> queryItemListByPage(@RequestParam(value = "page",defaultValue = "1")Integer page,@RequestParam(value = "rows",defaultValue = "30")Integer rows){
		
		try {
			PageInfo<Item> pageInfo = this.itemService.queryItemListByPage(page,rows);
			EasyUIResult easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
			return ResponseEntity.ok().body(easyUIResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
