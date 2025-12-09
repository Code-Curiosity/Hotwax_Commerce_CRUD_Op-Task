package com.example.ecomm.CRUD.entity;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contact_mech")     // MUST match your MySQL table name exactly
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactMech {
// I have created this entity like this to show that the database table can be made from the entity file
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_mech_id")
    private Integer contactMechId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)  // FK to customer table
    private Customer customer;

    @Column(name = "street_address", nullable = false, length = 100)
    private String streetAddress;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "state", nullable = false, length = 50)
    private String state;

    @Column(name = "postal_code", nullable = false, length = 20)
    private String postalCode;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "email", length = 100)
    private String email;
}
