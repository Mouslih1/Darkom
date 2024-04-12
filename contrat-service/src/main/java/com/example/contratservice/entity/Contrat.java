package com.example.contratservice.entity;

import com.example.contratservice.entity.enums.TypeContrat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "contrats")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE contrats SET is_delete = true WHERE id=?")
@Where(clause = "is_delete = false")
@EntityListeners(AuditingEntityListener.class)
public class Contrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String refContrat;
    private TypeContrat typeContrat;
    private LocalDate dateSignature;
    private Long appartementId;
    private Long propreitaireId;
    private double montant;
    private Long agenceId;

    @CreatedBy
    private String agentCreatedBy;
    @LastModifiedBy
    private String agentUpdatedBy;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    private boolean isDelete = Boolean.FALSE;
}
