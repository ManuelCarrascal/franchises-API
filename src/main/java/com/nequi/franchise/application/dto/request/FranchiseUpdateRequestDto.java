package com.nequi.franchise.application.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class FranchiseUpdateRequestDto {
    private String name;
}
