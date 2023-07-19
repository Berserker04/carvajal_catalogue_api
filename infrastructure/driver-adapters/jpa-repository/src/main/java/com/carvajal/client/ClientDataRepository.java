package com.carvajal.client;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClientDataRepository extends ReactiveCrudRepository<ClientData, Long> {
    Mono<ClientData> findByEmail(Long idClient);
    @Query("UPDATE clients SET password = :?, address = :?, cellPhone = :?, gender = :? WHERE id = :?")
    Mono<Integer> updateFieldsByEmail(String password, Long idClient);

    @Query("UPDATE clients SET state = 'False' WHERE email = :?")
    Mono<Integer> deleteClient(Long idClient);
}
