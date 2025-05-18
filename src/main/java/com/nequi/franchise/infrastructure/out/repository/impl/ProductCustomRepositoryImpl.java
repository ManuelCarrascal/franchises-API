package com.nequi.franchise.infrastructure.out.repository.impl;

import com.nequi.franchise.domain.model.ProductByBranchProjection;
import com.nequi.franchise.infrastructure.out.repository.ProductCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
@Repository
@Primary
@RequiredArgsConstructor
public class ProductCustomRepositoryImpl  implements ProductCustomRepository {

    private final DatabaseClient databaseClient;

    @Override
    public Flux<ProductByBranchProjection> findTopStockProductByFranchise(Long franchiseId) {
        String query = """
            SELECT b.id AS branchId, b.name AS branchName, p.name AS productName, p.stock
            FROM branch b
            JOIN product p ON b.id = p.branch_id
            WHERE b.franchise_id = :franchiseId
            AND p.stock = (
                SELECT MAX(p2.stock)
                FROM product p2
                WHERE p2.branch_id = b.id
            )
            """;

        return databaseClient.sql(query)
                .bind("franchiseId", franchiseId)
                .map((row, meta) -> new ProductByBranchProjection(
                        row.get("branchId", Long.class),
                        row.get("branchName", String.class),
                        row.get("productName", String.class),
                        row.get("stock", Integer.class)))
                .all();
    }
}
