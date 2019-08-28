package com.ampos.restaurant.model.dto;

import java.io.Serializable;
import java.util.List;

public class BillReportDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long billId;
    private Double total;
    private List<BillItemDTO> billItems;

    public BillReportDTO() {

    }

    public BillReportDTO(Long billId, Double total, List<BillItemDTO> billItems) {
        super();
        this.billId = billId;
        this.total = total;
        this.billItems = billItems;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<BillItemDTO> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItemDTO> billItems) {
        this.billItems = billItems;
    }
}
