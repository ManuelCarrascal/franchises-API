package com.nequi.franchise.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class FranchiseRequestDto {
    @NotBlank
    private String name;
}
