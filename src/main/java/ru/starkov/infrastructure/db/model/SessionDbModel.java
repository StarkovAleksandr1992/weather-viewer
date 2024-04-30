package ru.starkov.infrastructure.db.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "weather_viewer", name = "t_sessions")
@Data
public class SessionDbModel {

    @Id
    @Column(name = "c_session_id")
    private UUID id;

    @Column(name = "c_customer_id")
    private UUID customerId;

    @Column(name = "c_expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "c_status")
    private String status;
}
