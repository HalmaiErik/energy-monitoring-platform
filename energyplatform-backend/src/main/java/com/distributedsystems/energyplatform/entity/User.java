package com.distributedsystems.energyplatform.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "platform_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate birthday;
    private String address;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Device> devices;
    private String username;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
}
