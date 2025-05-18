package com.nequi.franchise.application.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class ProductRequestDto {
    private String name;
    private Double price;
    private Integer stock;
    private Long branchId;
}
