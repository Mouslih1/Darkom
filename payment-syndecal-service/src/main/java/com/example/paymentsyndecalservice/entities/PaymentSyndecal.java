package com.example.paymentsyndecalservice.entities;

import com.example.paymentsyndecalservice.entities.enums.MethodePaymentSyndecal;
import com.example.paymentsyndecalservice.entities.enums.StatusPaymentSyndecal;
import com.example.paymentsyndecalservice.entities.enums.TypePaymentSyndecal;
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

@Entity
@Table(name = "payment_syndecals")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE payment_syndecals SET is_delete = true WHERE id=?")
@Where(clause = "is_delete = false")
@EntityListeners(AuditingEntityListener.class)
@Builder
public class PaymentSyndecal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private double montantPaye;
    @Enumerated(EnumType.STRING)
    private TypePaymentSyndecal typePaymentSyndecal;
    @Enumerated(EnumType.STRING)
    private MethodePaymentSyndecal methodePaymentSyndecal;
    @Enumerated(EnumType.STRING)
    private StatusPaymentSyndecal statusPaymentSyndecal;
    private Long agenceId;
    private Long payerId;
    @CreatedBy
    private String agentCreatedBy;
    @LastModifiedBy
    private String agentUpdatedBy;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updateAt;
    private boolean isDelete = Boolean.FALSE;
}
