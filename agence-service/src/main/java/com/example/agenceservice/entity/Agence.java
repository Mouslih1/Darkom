package com.example.agenceservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "agences")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE agences SET is_delete = true WHERE id=?")
@Where(clause = "is_delete = false")
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String address;
    private String ville;
    private String telephone;
    @Column(unique = true)
    private String email;
    private Long agentCreatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isDelete = Boolean.FALSE;
}
