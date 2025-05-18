package com.nequi.franchise.application.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BranchUpdateRequestDtoTest {

    @Test
    void testAllArgsConstructorAndGetter() {
        String name = "Updated Branch Name";

        BranchUpdateRequestDto dto = new BranchUpdateRequestDto(name);

        assertEquals(name, dto.getName());
    }

    @Test
    void testNoArgsConstructorAndSetter() {
        BranchUpdateRequestDto dto = new BranchUpdateRequestDto();

        dto.setName("New Branch Name");

        assertEquals("New Branch Name", dto.getName());
    }
}
