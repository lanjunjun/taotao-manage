package com.taotao.manage.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.BasePojo;

public abstract class BaseService<T extends BasePojo> {
	
	public abstract Mapper<T> getMapper();
	
	private Class<T> clazz;
	
	
	@SuppressWarnings("unchecked")
	public BaseService() {
		//获取父类的类型
		Type type = this.getClass().getGenericSuperclass();
		//需要使用getActualTypeArguments，需要强转为ParameterizedType
		ParameterizedType pType = (ParameterizedType) type;
		//获取泛型T的class
		this.clazz = (Class<T>) pType.getActualTypeArguments()[0];
	}

	/**
	 * 根据Id查询数据
	 * @param id
	 * @return
	 */
	public T queryById(Long id){
		T t = this.getMapper().selectByPrimaryKey(id);
		return t;
	}
	
	/**
	 * 查询所有数据
	 * @return
	 */
	public List<T> queryAll(){
		List<T> list = this.getMapper().select(null);
		return list;
	}
	
	/**
	 * 查询数据总条数
	 * @return
	 */
	public Integer queryAllCount(){
		Integer count = this.getMapper().selectCount(null);
		return count;
	}

	/**
	 * 根据条件查询数据
	 * @param t
	 * @return
	 */
	public List<T> queryListByWhere(T t){
		List<T> list = this.getMapper().select(t);
		return list;
	}
	
	/**
	 * 根据条件分页查询数据
	 * @param t
	 * @param page
	 * @param rows
	 * @return
	 */
	public PageInfo<T> queryListByPage(T t,Integer page,Integer rows){
		PageHelper.startPage(page, rows);
		List<T> list = this.getMapper().select(t);
		PageInfo<T> pageInfo = new PageInfo<T>(list);
		return pageInfo;
	}
	/**
	 * 根据条件查询一条数据
	 * @param t
	 * @return
	 */
	public T queryOne(T t){
		//T t = this.getMapper().selectOne(t);
		T result = this.getMapper().select(t).get(0);
		return result;
	}
	
	/**
	 * 保存数据
	 * @param t
	 * @return
	 */
	public Integer save(T t){
		t.setCreated(new Date());
		t.setUpdated(t.getCreated());
		Integer count = this.getMapper().insert(t);
		return count;
	}
	
	/**
	 * 保存,忽略空字段
	 * @param t
	 * @return
	 */
	public Integer saveSelective(T t){
		t.setCreated(new Date());
		t.setUpdated(t.getCreated());
		Integer count = this.getMapper().insertSelective(t);
		return count;
	}
	
	/**
	 * 更新数据
	 * @param t
	 * @return
	 */
	public Integer updateById(T t){
		t.setUpdated(t.getCreated());
		int count = this.getMapper().updateByPrimaryKey(t);
		return count;
	}
	
	/**
	 * 更新数据，忽略空字段
	 * @param t
	 * @return
	 */
	public Integer updateByIdSelective(T t){
		t.setUpdated(t.getCreated());
		Integer count = this.getMapper().updateByPrimaryKeySelective(t);
		return count;
	}
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public Integer deleteById(Long id){
		Integer count = this.getMapper().deleteByPrimaryKey(id);
		return count;
	}
	
	/**
	 * 根据ids批量删除
	 * @param ids
	 * @return
	 */
	public Integer deleteByIds(List<Object> ids){
		//声明条件
		Example example = new Example(this.clazz);
		//设置条件
		example.createCriteria().andIn("id", ids);
		Integer count  = this.getMapper().deleteByExample(example);
		return count;
	}
}
