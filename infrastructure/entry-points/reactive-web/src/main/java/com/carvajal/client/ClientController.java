package com.carvajal.client;

import com.carvajal.client.services.ClientService;
import com.carvajal.http.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    private final ClientMapper mapper;
    private final ClientService clientService;
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @PostMapping()
    public Mono<ResponseEntity<?>> createClient(@RequestBody Client client) {
        logger.info("Client: creating new client");

        return clientService.createClient(client)
                .flatMap(result -> {
                    if (result == null) {
                        return Mono.just(ResponseHandler.success("Can't register client"));
                    } else {
                        return mapper.toEntityData(result)
                                .map(entityData -> ResponseEntity.status(HttpStatus.CREATED).body(ResponseHandler.success("Success", entityData)));
                    }
                })
                .onErrorResume(IllegalArgumentException.class, e -> {
                    logger.info(e.getMessage());
                    return Mono.just(ResponseHandler.success(e.getMessage()));
                })
                .onErrorResume(Exception.class, e -> {
                    logger.info(e.getMessage());
                    return Mono.just(ResponseHandler.error("Internal server error"));
                });
    }


    @GetMapping("/{email}")
    public ResponseEntity<?> getClientByEmail(@PathVariable String email) {
        try {
            SecurityContextHolder.getContext().getAuthentication();

            Client result = clientService.getClientByEmail(email).block();

            if (result == null) return ResponseHandler.success("Client not found");
            return ResponseHandler.success("Success", mapper.toEntityData(result).block());
        } catch (IllegalArgumentException e) {
            logger.info(e.getMessage());
            return ResponseHandler.success(e.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseHandler.error("Internal server error");
        }
    }
}
