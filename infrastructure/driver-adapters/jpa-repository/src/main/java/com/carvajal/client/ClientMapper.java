package com.carvajal.client;

import com.carvajal.client.properties.Email;
import com.carvajal.client.properties.Password;
import com.carvajal.client.properties.Role;
import com.carvajal.commons.properties.Id;
import com.carvajal.commons.properties.State;
import reactor.core.publisher.Mono;

public class ClientMapper {
    public final Mono<ClientData> toEntityData(Client client) {
        return Mono.just(ClientData.builder()
                .id(client.getId().getValue())
                .email(client.getEmail().getValue())
                .password(client.getPassword().getValue())
                .role(client.getRole().getValue())
                .state(client.getState().getValue())
                .build());
    }

    public final Mono<ClientData> toNewEntityData(Client client) {
        return Mono.just(ClientData.builder()
                .email(client.getEmail().getValue())
                .password(client.getPassword().getValue())
                .role(client.getRole().getValue())
                .state(client.getState().getValue())
                .build());
    }

    public final Mono<ClientData> toUpdateEntityData(Client client) {
        return Mono.just(ClientData.builder()
                .id(client.getId().getValue())
                .password(client.getPassword().getValue())
                .build());
    }

    public final Client toDomainModel(ClientData clientData) {
        return new Client(
//                new Address(clientData.getAddress()),
//                new CellPhone(clientData.getCellPhone()),
//                new Gender(clientData.getGender()),
                new Id(clientData.getId()),
//                new Identification(clientData.getIdentification()),
//                new FullName(clientData.getFullName()),
                new Email(clientData.getEmail()),
                new Password(clientData.getPassword()),
                new Role(clientData.getRole()),
                new State(clientData.getState()));
    }
}