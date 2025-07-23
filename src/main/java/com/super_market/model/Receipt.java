package com.super_market.model;

// The @JsonFormat import is no longer needed
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.relational.core.sql.In;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Receipt")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private Instant dateTime;

    private double totalAmount;

    private String cashierEmail;

    @ElementCollection
    private List<String> items;
}