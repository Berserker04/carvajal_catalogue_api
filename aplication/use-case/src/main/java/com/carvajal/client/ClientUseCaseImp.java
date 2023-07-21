package com.carvajal.client;

import com.carvajal.client.gatewey.in.ClientUseCase;
import com.carvajal.client.gatewey.out.ClientRepository;
import com.carvajal.client.properties.Password;
import com.carvajal.client.properties.Role;
import com.carvajal.commons.properties.State;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import reactor.core.publisher.Mono;

import java.util.Random;

@RequiredArgsConstructor
public class ClientUseCaseImp implements ClientUseCase {
    private final ClientRepository clientRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Mono<Client> createClient(Client client) {
        String encodePassword = encryptPassword(client.getPassword().getValue());
        client.setPassword(new Password(encodePassword));
//        client.setEmail(new Email(generateEmail()));
        client.setState(new State("Active"));
        client.setRole(new Role("CLIENT"));
        return clientRepository.save(client);
    }

    @Override
    public Mono<Client> getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public String encryptPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
