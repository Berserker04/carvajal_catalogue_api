package com.carvajal.client;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder(toBuilder = true)
@Table(name = "clients")
public class ClientData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column("id")
    private Long id;
    @Column("email")
    private Long email;
    private String password;
    private String role;
    private String state;
}