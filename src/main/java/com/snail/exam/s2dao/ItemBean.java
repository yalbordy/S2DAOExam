package com.snail.exam.s2dao;

import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Column;
import org.seasar.dao.annotation.tiger.Id;
import org.seasar.dao.annotation.tiger.IdType;
import org.seasar.dao.annotation.tiger.Ids;

import com.sun.jmx.snmp.Timestamp;

@Bean(table = "ITEM_TBL",timeStampProperty="onUpdate")
public class ItemBean {
	private int pFullPrice;

	private long pId;

	private String pName;

	private Timestamp pOnCreate;

	private Timestamp pOnUpdate;

	private int pStock;

	@Column("ID")
	@Ids( {
			@Id(value = IdType.IDENTITY, dbms = "derby"),
			@Id(value = IdType.SEQUENCE, sequenceName = "SEQ_ITEM", dbms = "oracle") })
	public long getId() {
		return pId;
	}

	@Column("NAME")
	public String getName() {
		return pName;
	}

	@Column("ON_CREATE")
	public Timestamp getOnCreate() {
		return pOnCreate;
	}

	@Column("ON_UPDATE")
	public Timestamp getOnUpdate() {
		return pOnUpdate;
	}

	@Column("PRICE")
	public int getPrice() {
		return pFullPrice;
	}

	@Column("STOCK")
	public int getStock() {
		return pStock;
	}

	public void setId(long id) {
		pId = id;
	}

	public void setName(String name) {
		pName = name;
	}
	public void setOnCreate(Timestamp onCreate) {
		pOnCreate = onCreate;
	}

	public void setOnUpdate(Timestamp onUpdate) {
		pOnUpdate = onUpdate;
	}
	public void setPrice(int price) {
		pFullPrice = price;
	}

	public void setStock(int stock) {
		pStock = stock;
	}

}
