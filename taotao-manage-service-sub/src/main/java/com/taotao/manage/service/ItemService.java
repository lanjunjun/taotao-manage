package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;

@Service
public class ItemService extends BaseService<Item> {

	@Autowired
	ItemDescService itemDescService;
	
	@Autowired
	private ItemMapper itemMapper;

	/**
	 * 保存商品数据
	 * 
	 * @param item
	 */
	public void saveItem(Item item, String desc) {
		// 保存商品
		item.setId(null);
		item.setStatus(1);
		super.save(item);

		// 保存商品描述
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		this.itemDescService.save(itemDesc);

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
