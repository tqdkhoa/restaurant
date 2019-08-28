package com.ampos.restaurant.model.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BillDTO implements Serializable {

    private Long id;
    Set<BillDetailDTO> billDetails = new HashSet<>();
    private Double total;
}
