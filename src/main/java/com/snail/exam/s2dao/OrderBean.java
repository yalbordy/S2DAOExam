package com.snail.exam.s2dao;

import java.sql.Timestamp;

import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Column;
import org.seasar.dao.annotation.tiger.Id;
import org.seasar.dao.annotation.tiger.Ids;
import org.seasar.dao.annotation.tiger.IdType;
import org.seasar.dao.annotation.tiger.Relation;

@Bean(table = "PURCHASE", timeStampProperty = "onUpdate")
public class OrderBean {
    private int pAmount;

    private CustomerBean pCustomerBean;

    private long pId;

    private ItemBean pItemBean;
    
    private int pTradePrice;

    private char pStatus;

	private Timestamp pOnCreate;

	private Timestamp pOnUpdate;

    @Column("AMOUNT")
    public int getAmount() {
        return pAmount;
    }

    public CustomerBean getCustomerBean() {
        return pCustomerBean;
    }

    @Column("ID")
    @Ids( { @Id(value = IdType.IDENTITY, dbms = "derby"),
        @Id(value = IdType.SEQUENCE, sequenceName = "SEQ_PURCHASE", dbms = "oracle") })
    public long getId() {
        return pId;
    }

    @Relation(relationNo = 0, relationKey = "ITEM_ID:ID")
    public ItemBean getItem() {
        return pItemBean;
    }

    @Column("STATUS")
    public char getStatus() {
        return pStatus;
    }

	@Column("ON_CREATE")
	public Timestamp getOnCreate() {
		return pOnCreate;
	}

	@Column("ON_UPDATE")
	public Timestamp getOnUpdate() {
		return pOnUpdate;
	}

    public void setAmount(int amount) {
        pAmount = amount;
    }

    @Relation(relationNo = 0, relationKey = "CUSTOMER_ID:ID")
    public void setCustomerBean(CustomerBean CustomerBean) {
        pCustomerBean = CustomerBean;
    }

    public void setId(long id) {
        pId = id;
    }

    public void setItemBean(ItemBean ItemBean) {
        pItemBean = ItemBean;
    }

    public void setStatus(char status) {
        pStatus = status;
    }

	public void setOnCreate(Timestamp onCreate) {
		pOnCreate = onCreate;
	}

	public void setOnUpdate(Timestamp onUpdate) {
		pOnUpdate = onUpdate;
	}

	public int getTradePrice() {
		return pTradePrice;
	}

	public void setTradePrice(int tradePrice) {
		pTradePrice = tradePrice;
	}

	public ItemBean getItemBean() {
		return pItemBean;
	}

}
