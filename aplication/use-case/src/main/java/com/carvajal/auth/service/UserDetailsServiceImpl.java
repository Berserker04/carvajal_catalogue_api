package com.carvajal.auth.service;

import com.carvajal.client.Client;
import com.carvajal.client.gatewey.out.ClientRepository;
import com.carvajal.client.properties.Email;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute("userSession", user);

        return User
                .withUsername(email)
                .password(user.getPassword().getValue())
                .roles(user.getRole().getValue())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

}
