package com.ampos.restaurant.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class BillDetailKey implements Serializable {

    @Column(name = "menu_item_id")
    Long menuItemId;

    @Column(name = "bill_id")
    Long billId;

    public BillDetailKey(Long billId, Long menuItemId) {
        super();
        this.menuItemId = menuItemId;
        this.billId = billId;
    }
}
