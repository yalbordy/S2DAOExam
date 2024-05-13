package com.snail.exam.s2dao;

import java.sql.Timestamp;

import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Column;
import org.seasar.dao.annotation.tiger.Id;
import org.seasar.dao.annotation.tiger.IdType;
import org.seasar.dao.annotation.tiger.Ids;

@Bean(table = "CUSTOMER_TBL",timeStampProperty="onUpdate")
public class CustomerBean {
	private String pAddress;

	private int pCreditFacility;

	private double pDiscountRate;

	private long pId;
	
	private String pName;
	
	private Timestamp pOnCreate;

	private Timestamp pOnUpdate;

	@Column("ADDRESS")
	public String getAddress() {
		return pAddress;
	}

	@Column("CREDIT_FACILITY")
	public int getCreditFacility() {
		return pCreditFacility;
	}

	@Column("DISCOUNT_RATE")
	public double getDiscountRate() {
		return pDiscountRate;
	}

	@Column("ID")
	@Ids( {
			@Id(value = IdType.IDENTITY, dbms = "derby"),
			@Id(value = IdType.SEQUENCE, sequenceName = "SEQ_CUSTOMER", dbms = "oracle") })
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

	public void setAddress(String address) {
		this.pAddress = address;
	}

	public void setCreditFacility(int creditFacility) {
		this.pCreditFacility = creditFacility;
	}

	public void setDiscountRate(double discountRate) {
		this.pDiscountRate = discountRate;
	}

	public void setId(long id) {
		this.pId = id;
	}

	public void setName(String name) {
		this.pName = name;
	}

	public void setOnCreate(Timestamp onCreate) {
		pOnCreate = onCreate;
	}

	public void setOnUpdate(Timestamp onUpdate) {
		pOnUpdate = onUpdate;
	}
}
