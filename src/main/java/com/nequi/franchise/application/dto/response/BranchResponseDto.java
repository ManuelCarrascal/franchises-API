package com.nequi.franchise.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BranchResponseDto {
    private Long id;
    private Long franchiseId;
    private String name;
    private String address;
}
