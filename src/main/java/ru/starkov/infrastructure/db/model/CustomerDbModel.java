package ru.starkov.infrastructure.db.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "weather_viewer", name = "t_customers")
@Data
public class CustomerDbModel {

    @Id
    @Column(name = "c_customer_id")
    private UUID id;
    @Column(name = "c_login", unique = true)
    private String login;
    @Column(name = "c_password")
    private String password;

}
