package com.nequi.franchise.application.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BranchRequestDtoTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        Long franchiseId = 10L;
        String name = "Branch A";
        String address = "123 Main St";

        BranchRequestDto dto = new BranchRequestDto(franchiseId, name, address);

        assertEquals(franchiseId, dto.getFranchiseId());
        assertEquals(name, dto.getName());
        assertEquals(address, dto.getAddress());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        BranchRequestDto dto = new BranchRequestDto();

        dto.setFranchiseId(5L);
        dto.setName("Branch B");
        dto.setAddress("456 Another St");

        assertEquals(5L, dto.getFranchiseId());
        assertEquals("Branch B", dto.getName());
        assertEquals("456 Another St", dto.getAddress());
    }
}
