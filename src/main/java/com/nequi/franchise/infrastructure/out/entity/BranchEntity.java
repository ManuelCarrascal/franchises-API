package com.nequi.franchise.infrastructure.out.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("branch")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchEntity {
    @Id
    private Long id;
    private String name;
    private String address;
    private Long franchiseId;
}