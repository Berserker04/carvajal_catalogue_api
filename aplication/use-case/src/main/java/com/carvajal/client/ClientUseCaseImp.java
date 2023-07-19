package com.carvajal.client;

import com.carvajal.client.gatewey.in.ClientUseCase;
import com.carvajal.client.gatewey.out.ClientRepository;
import com.carvajal.client.properties.Email;
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
    private static final int ACCOUNT_NUMBER_LENGTH = 10;

    @Override
    public Mono<Client> createClient(Client client) {
        String encodePassword = encryptPassword(client.getPassword().getValue());
        client.setPassword(new Password(encodePassword));
//        client.setEmail(new Email(generateEmail()));
        client.setState(new State("True"));
        client.setRole(new Role("CLIENT"));
        return clientRepository.save(client);
    }

    @Override
    public Mono<Client> getClientByEmail(Long id) {
        return clientRepository.findByEmail(id);
    }

    @Override
    public Mono<Client> updateClient(Client client) {
        String encodePassword = encryptPassword(client.getPassword().getValue());
        client.setPassword(new Password(encodePassword));
        return clientRepository.update(client)
                .flatMap(result-> {
                    if(result >= 1){
                        return clientRepository.findById(client.getId().getValue());
                    }
                    return null;
                });
    }

    @Override
    public Mono<Boolean> deleteClient(Long email) {
        return clientRepository.deleteById(email);
    }

    public String encryptPassword(String password) {
        return  bCryptPasswordEncoder.encode(password);
    }

//    public static Long generateEmail() {
//        Random random = new Random();
//        StringBuilder sb = new StringBuilder();
//        while (sb.length() < ACCOUNT_NUMBER_LENGTH) {
//            int digit = random.nextInt(10);
//            sb.append(digit);
//        }
//        return Long.valueOf(sb.toString());
//    }
}
