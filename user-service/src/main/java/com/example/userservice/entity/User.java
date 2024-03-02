package com.example.userservice.entity;

import com.example.userservice.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE users SET is_delete = true WHERE id=?")
@Where(clause = "is_delete = false")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String firstname;
    private String lastname;
    private String address;
    private String ville;
    private Long agentCreatedBy;
    private String telephone;
    private Role role;
    private LocalDateTime dateNaissance;
    private LocalDateTime createdAt;
    private boolean isDelete = Boolean.FALSE;

}
