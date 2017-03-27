package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;

@Service
public class ItemService extends BaseService<Item> {

	@Autowired
	ItemDescService itemDescService;
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private ItemParamItemService itemParamItemService;

	/**
	 * 保存商品数据
	 * 
	 * @param item
	 * @param paramData 
	 */
	public void saveItem(Item item, String desc, String paramData) {
		// 保存商品
		item.setId(null);
		item.setStatus(1);
		super.save(item);

		// 保存商品描述
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		this.itemDescService.save(itemDesc);

		//保存商品規格參數
		ItemParamItem itemParamItem = new ItemParamItem();
		itemParamItem.setId(null);
		itemParamItem.setItemId(item.getId());
		itemParamItem.setParamData(paramData);
		this.itemParamItemService.save(itemParamItem);
	}

	/**
	 * 根据条件分页查询商品
	 * @param page
	 * @param rows
	 * @return
	 */
	public PageInfo<Item> queryItemListByPage(Integer page, Integer rows) {
		
		Item item = new Item();
		item.setStatus(1);
		PageInfo<Item> pageInfo = null;
		try {
			pageInfo = super.queryListByPage(item, page, rows, "updated desc");
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return pageInfo;
	}

	/**
	 * 更新商品
	 * @param item
	 * @param desc
	 */
	public void updateItem(Item item, String desc) {

		super.updateByIdSelective(item);
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		System.out.println(itemDesc);
		this.itemDescService.updateByIdSelective(itemDesc);
	}

	
	/**
	 * 根据条件分页查询商品
	 * @param page
	 * @param rows
	 * @return
	 */
	/*public PageInfo<Item> queryItemListByPage(Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		//声明查询条件
		Example example = new Example(Item.class);
		//设置排序
		example.setOrderByClause("updated DESC");
		example.createCriteria().andEqualTo("status", 1);
		List<Item> list = this.itemMapper.selectByExample(example);
		PageInfo<Item> pageInfo = new PageInfo<Item>(list);
		return pageInfo;
	}*/
}
