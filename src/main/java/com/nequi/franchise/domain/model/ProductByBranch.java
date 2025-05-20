package com.nequi.franchise.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductByBranch {
    private Long branchId;
    private String branchName;
    private String productName;
    private Integer stock;
}
