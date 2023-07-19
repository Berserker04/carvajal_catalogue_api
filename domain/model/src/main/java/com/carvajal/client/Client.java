package com.carvajal.client;

import com.carvajal.client.properties.Email;
import com.carvajal.client.properties.Password;
import com.carvajal.client.properties.Role;
import com.carvajal.commons.properties.Id;
import com.carvajal.commons.properties.State;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {
    private Id id;
    private Email email;
    private Password password;
    private Role role;
    private State state;

    public Client(Id id, Email email, Password password, Role role, State state) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.state = state;
    }
}
