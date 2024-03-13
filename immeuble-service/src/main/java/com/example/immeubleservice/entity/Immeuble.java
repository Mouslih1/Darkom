package com.example.immeubleservice.entity;

import com.example.immeubleservice.entity.enums.StatusImmeuble;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "immeubles")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE immeubles SET is_delete = true WHERE id=?")
@Where(clause = "is_delete = false")
public class Immeuble {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String referenceImmeuble;
    private String address;
    private int numberEtage;
    private int numberApparetement;
    private Date anneeConstruction;
    private Long agenceId;
    @Enumerated(value = EnumType.STRING)
    private StatusImmeuble statusImmeuble;
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
