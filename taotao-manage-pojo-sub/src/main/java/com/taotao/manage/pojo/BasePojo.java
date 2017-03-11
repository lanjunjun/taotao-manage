package com.taotao.manage.pojo;

import java.util.Date;

public abstract class BasePojo {
	
	private Date created;  //创建时间
	private Date updated;  //更新时间
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
}
