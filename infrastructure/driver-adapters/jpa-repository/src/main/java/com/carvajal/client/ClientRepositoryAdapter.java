package com.carvajal.client;

import com.carvajal.client.gatewey.out.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClientRepositoryAdapter implements ClientRepository {

    private final ClientMapper mapper;
    private final ClientDataRepository repository;
    @Override
    public Mono<Client> save(Client client) {
        return Mono.just(client)
                .flatMap(mapper::toNewEntityData)
                .flatMap(repository::save)
                .map(mapper::toDomainModel);
    }

    @Override
    public Mono<Client> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomainModel);
    }

    @Override
    public Mono<Client> findByEmail(Long client) {
        return repository.findByEmail(client)
                .map(mapper::toDomainModel);
    }

    @Override
    public Mono<Integer> update(Client client) {
        return repository.existsById(client.getId().getValue())
                .flatMap(exists -> {
                    if(exists){
                        return Mono.just(client)
                                .flatMap(mapper::toUpdateEntityData)
                                .flatMap(c->{
                                    repository.updateFieldsByEmail(
                                            c.getPassword(),
                                            c.getId()
                                    )
                                            .subscribe(integer -> {
                                                System.out.println(integer);
                                            });

                                    return Mono.just(1);
                                });
                    }
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Boolean> deleteById(Long email) {
        return repository.findByEmail(email)
                .flatMap(client -> {
                    if(client != null){
                        repository.deleteClient(email).subscribe();
                        return Mono.just(true);
                    }
                    return Mono.just(false);
                });
    }
}
