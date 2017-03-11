package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
@Service
public class ItemService extends BaseService<Item>{

	@Autowired
	ItemDescService itemDescService;
	/**
	 * 保存商品数据
	 * @param item
	 */
	public void saveItem(Item item,String desc) {
		//保存商品
		item.setId(null);
		item.setStatus(1);
		super.save(item);
		
		//保存商品描述
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		this.itemDescService.save(itemDesc);
	}

}
