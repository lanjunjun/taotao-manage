package com.taotao.manage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.taotao.common.bean.ItemCatData;
import com.taotao.common.bean.ItemCatResult;
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

	/**
	 * 查询所有类目，封装到前台ItemCatResult中，前台首页左侧菜单显示
	 * @return
	 */
	public ItemCatResult queryItemCatAll() {
		ItemCatResult itemCatResult = new ItemCatResult();
		//查询所有的类目数据，待用
		List<ItemCat> list = super.queryAll();
		//把类目封装到Map中，key为父Id,value子类目的集合
		Map<Long, List<ItemCat>> map = new HashMap<Long, List<ItemCat>>();
		for (ItemCat itemCat : list) {
			if(!map.containsKey(itemCat.getParentId())){
				map.put(itemCat.getParentId(), new ArrayList<ItemCat>());
			}
			map.get(itemCat.getParentId()).add(itemCat);
		}
		
		//把类目数据封装到ItemCatResult
		List<ItemCat> itemCatList1 = map.get(0l);
		itemCatResult.setItemCats(new ArrayList<ItemCatData>());
		//装载一级
		for(ItemCat itemCat1 : itemCatList1){
			ItemCatData itemCatData1 = new ItemCatData();
			itemCatData1.setUrl("/products/"+itemCat1.getId()+".html");
			itemCatData1.setName("<a href='/products/1.html'>"+itemCat1.getName()+"</a>");
			//把该一级类目的子类目装载(装载二级)
			if(itemCat1.getIsParent()){
				List<ItemCat> itemCatList2 = map.get(itemCat1.getId());
				List<ItemCatData> itemCatDataList2 = new ArrayList<ItemCatData>();
				//遍历二级类目（装载三级）
				for(ItemCat itemCat2 : itemCatList2){
					ItemCatData itemCatData2 = new ItemCatData();
					itemCatData2.setUrl("/products/"+itemCat2.getId()+".html");
					itemCatData2.setName(itemCat2.getName());
					
					List<ItemCat> itemCatList3 = map.get(itemCat2.getId());
					List<String> itemCatData3 = new ArrayList<String>();
					//把该二级类目的子类目装载
					if(itemCat2.getIsParent()){
						for (ItemCat itemCat3 : itemCatList3) {
							itemCatData3.add("/products/"+itemCat3.getId()+".html|"+itemCat3.getName());
						}
						itemCatData2.setList(itemCatData3);
					}
					itemCatDataList2.add(itemCatData2);
				}
				itemCatData1.setList(itemCatDataList2);
			}
			itemCatResult.getItemCats().add(itemCatData1);
		}
		return itemCatResult;
	}

}
