package com.nequi.franchise.application.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class BranchRequestDto {
    private Long franchiseId;
    private String name;
    private String address;
}