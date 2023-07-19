package com.carvajal.catalogue.config;

import com.carvajal.client.ClientMapper;
import com.carvajal.client.ClientRepositoryAdapter;
import com.carvajal.client.ClientUseCaseImp;
import com.carvajal.client.gatewey.out.ClientRepository;
import com.carvajal.client.services.ClientService;
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

//    //Account
//    @Bean
//    public AccountMapper accountMapper(){
//        return new AccountMapper();
//    }
//
//    @Bean("accountServicePrimary")
//    @Primary
//    public AccountService accountService(AccountRepository accountRepository, ClientRepository clientRepository) {
//        return new AccountService(
//                new AccountUseCaseImp(accountRepository, clientRepository)
//        );
//    }
//
//    @Bean
//    public AccountRepository accountRepository(AccountRepositoryAdapter accountRepositoryAdapter){
//        return accountRepositoryAdapter;
//    }

//    //Movement
//    @Bean
//    public MovementMapper movementMapper(){
//        return new MovementMapper();
//    }
//
//    @Bean("movementServicePrimary")
//    @Primary
//    public MovementService movementService(MovementRepository movementRepository, AccountRepository accountRepository) {
//        return new MovementService(
//                new MovementUseCaseImp(movementRepository, accountRepository)
//        );
//    }
//
//    @Bean
//    public MovementRepository movementRepository(MovementRepositoryAdapter movementRepositoryAdapter){
//        return movementRepositoryAdapter;
//    }

//    //Report
//    @Bean
//    public ReportMapper reportMapper(){
//        return new ReportMapper();
//    }
//
//    @Bean("reportServicePrimary")
//    @Primary
//    public ReportService reportService(ReportRepository reportRepository) {
//        return new ReportService(
//                new ReportUseCaseImp(reportRepository)
//        );
//    }
//
//    @Bean
//    public ReportRepository reportRepository(ReportRepositoryAdapter reportRepositoryAdapter){
//        return reportRepositoryAdapter;
//    }
}
