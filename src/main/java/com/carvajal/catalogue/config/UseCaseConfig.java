package com.carvajal.catalogue.config;

import com.carvajal.client.ClientMapper;
import com.carvajal.client.ClientRepositoryAdapter;
import com.carvajal.client.ClientUseCaseImp;
import com.carvajal.client.gatewey.out.ClientRepository;
import com.carvajal.client.services.ClientService;
import com.carvajal.product.ProductMapper;
import com.carvajal.product.ProductRepositoryAdapter;
import com.carvajal.product.ProductUseCaseImp;
import com.carvajal.product.gatewey.out.ProductRepository;
import com.carvajal.product.services.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UseCaseConfig {
    //Client
    @Bean
    public ClientMapper clientMapper(){
        return new ClientMapper();
    }

    @Bean("clientServicePrimary")
    @Primary
    public ClientService clientService(ClientRepository clientRepository, BCryptPasswordEncoder passwordEncoder) {
        return new ClientService(
                new ClientUseCaseImp(clientRepository, passwordEncoder)
        );
    }

    @Bean
    public ClientRepository clientRepository(ClientRepositoryAdapter clientRepositoryAdapter){
        return clientRepositoryAdapter;
    }

    //Product
    @Bean
    public ProductMapper productMapper(){
        return new ProductMapper();
    }

    @Bean("productServicePrimary")
    @Primary
    public ProductService productService(ProductRepository accountRepository) {
        return new ProductService(
                new ProductUseCaseImp(accountRepository)
        );
    }

    @Bean
    public ProductRepository accountRepository(ProductRepositoryAdapter productRepositoryAdapter){
        return productRepositoryAdapter;
    }
}
