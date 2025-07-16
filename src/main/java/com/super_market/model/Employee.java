package com.super_market.model;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String f_name;
    private String l_name;
    private String address;
    private String email;
    private Long phoneNo;
    private int salary;
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

}
