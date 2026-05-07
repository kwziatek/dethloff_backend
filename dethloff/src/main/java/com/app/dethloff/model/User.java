package com.app.dethloff.model;

import com.app.dethloff.login.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "client")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    // tells hibernate to treat enum as a String
    private Role role;
}
