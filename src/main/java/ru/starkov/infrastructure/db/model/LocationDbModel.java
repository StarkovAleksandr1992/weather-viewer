package ru.starkov.infrastructure.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(schema = "weather_viewer", name = "t_locations",
        indexes = {@Index(name = "ix_name", columnList = "c_name")},
        uniqueConstraints = {@UniqueConstraint(columnNames = {
                "c_customer_id",
                "c_name",
                "c_country_code",
                "c_latitude",
                "c_longitude"})})
@Data
public class LocationDbModel {

    @Id
    @Column(name = "c_location_id")
    private UUID id;

    @Column(name = "c_customer_id")
    private UUID customerId;

    @Column(name = "c_name")
    private String name;

    @Column(name = "c_country_code")
    private String countryCode;

    @Column(name = "c_latitude")
    private double latitude;

    @Column(name = "c_longitude")
    private double longitude;
}
