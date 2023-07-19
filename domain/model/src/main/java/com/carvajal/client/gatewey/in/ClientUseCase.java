package com.carvajal.client.gatewey.in;

import com.carvajal.client.Client;
import reactor.core.publisher.Mono;

public interface ClientUseCase {
    Mono<Client> createClient(Client client);
    Mono<Client> getClientByEmail(Long client);
    Mono<Client> updateClient(Client client);
    Mono<Boolean> deleteClient(Long email);
}
