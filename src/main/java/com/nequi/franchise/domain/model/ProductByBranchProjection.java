package com.nequi.franchise.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductByBranchProjection {
    private Long branchId;
    private String branchName;
    private String productName;
    private Integer stock;
}