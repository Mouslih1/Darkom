package com.example.plainte.entity;

import com.example.plainte.entity.enums.StatusPlainte;
import com.example.plainte.entity.enums.Urgence;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name = "plaintes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SQLDelete(sql = "UPDATE plaintes SET is_delete = true WHERE id=?")
@Where(clause = "is_delete = false")
@EntityListeners(AuditingEntityListener.class)
public class Plainte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sujet;
    private String description;

    @Enumerated(EnumType.STRING)
    private StatusPlainte statusPlainte;
    @Enumerated(EnumType.STRING)
    private Urgence urgence;
    private Long immeubleId;
    private Long agenceId;

    @CreatedBy
    private String propreitaireCreatedBy;
    @LastModifiedBy
    private String propreitaireUpdatedBy;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;

    private boolean isDelete = Boolean.FALSE;
}
