package com.carvajal.auth.service;

import com.carvajal.client.Client;
import com.carvajal.client.gatewey.out.ClientRepository;
import com.carvajal.client.properties.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ClientRepository clientRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Client user = clientRepository.findByEmail(new Email(email).getValue()).block();

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return User
                .withUsername(email)
                .password(user.getPassword().getValue())
                .roles(user.getRole().getValue())
                .build();
    }

}
