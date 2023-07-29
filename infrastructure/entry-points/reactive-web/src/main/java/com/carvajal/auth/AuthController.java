package com.carvajal.auth;

import com.carvajal.auth.service.JwtUtilService;
import com.carvajal.http.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService usuarioDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationReq authenticationReq) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationReq.getEmail(),
                        authenticationReq.getPassword()));

        final UserDetails userDetails = usuarioDetailsService.loadUserByUsername(
                authenticationReq.getEmail());

        final String jwt = jwtUtilService.generateToken(userDetails);

        logger.info("Auth: client {} authenticated", authenticationReq.getEmail());
        return ResponseHandler.success("Success", new TokenInfo(jwt));
    }
}
