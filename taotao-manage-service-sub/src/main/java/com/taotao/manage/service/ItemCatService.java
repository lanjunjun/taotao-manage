package com.taotao.manage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.taotao.manage.pojo.ItemCat;

@Service
public class ItemCatService extends BaseService<ItemCat>{

/*	@Autowired
	private ItemCatMapper itemCatMapper;
	
	@Override
	public Mapper<ItemCat> getMapper() {
		return this.itemCatMapper;
	}*/

	/**
	 * 根据父id查询商品类目
	 * @param parentId
	 * @return
	 */
	public List<ItemCat> queryItemCatByParentId(Long parentId) {
		ItemCat param = new ItemCat();
		param.setParentId(parentId);
		List<ItemCat> list = super.queryListByWhere(param);
		return list;
	}

}
