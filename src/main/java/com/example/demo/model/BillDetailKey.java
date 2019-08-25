package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BillDetailKey implements Serializable {

	@Column(name = "menu_item_id")
	Long menuItemId;
	
	@Column(name = "bill_id")
	Long billId;
	
	public BillDetailKey() {
		
	}

	public BillDetailKey(Long billId, Long menuItemId) {
		super();
		this.menuItemId = menuItemId;
		this.billId = billId;
	}

	public Long getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(Long menuItemId) {
		this.menuItemId = menuItemId;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((menuItemId == null) ? 0 : menuItemId.hashCode());
        result = prime * result + ((billId == null) ? 0 : billId.hashCode());
        return result;
    }
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BillDetailKey other = (BillDetailKey) obj;
        if (menuItemId == null) {
            if (other.menuItemId != null)
                return false;
        } else if (!menuItemId.equals(other.menuItemId))
            return false;
        if (billId == null) {
            if (other.billId != null)
                return false;
        } else if (!billId.equals(other.billId))
            return false;
        return true;
    }
	
}
