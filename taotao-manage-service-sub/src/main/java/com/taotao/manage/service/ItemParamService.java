package com.taotao.manage.service;

import org.springframework.stereotype.Service;

import com.taotao.manage.pojo.ItemParam;


@Service
public class ItemParamService extends BaseService<ItemParam> {

	/**
	 * 根据类目ID查询该类目的模板
	 * @param itemCatId
	 * @return
	 */
	public ItemParam queryItemParamByItemCatId(Long itemCatId) {
		ItemParam itemParam = new ItemParam();
		itemParam.setItemCatId(itemCatId);
		ItemParam result = super.queryOne(itemParam);
		return result;
	}

	/**
	 * 保存
	 * @param itemCatId
	 * @param paramData
	 */
	public void saveItemParam(Long itemCatId, String paramData) {
		ItemParam itemParam = new ItemParam();
		itemParam.setId(null);
		itemParam.setItemCatId(itemCatId);
		itemParam.setParamData(paramData);
		this.save(itemParam);
	}

}
