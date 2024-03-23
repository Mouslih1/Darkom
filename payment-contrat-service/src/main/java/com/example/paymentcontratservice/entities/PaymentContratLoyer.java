package com.example.paymentcontratservice.entities;

import com.example.paymentcontratservice.entities.enums.MethodePaymentContratLoyer;
import com.example.paymentcontratservice.entities.enums.TypePaymentContratLoyer;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment_contrat_loyers")
@SQLDelete(sql = "UPDATE payment_contrat_loyers SET is_delete = true WHERE id=?")
@Builder
public class PaymentContratLoyer extends PaymentContrat{

    @Enumerated(EnumType.STRING)
    private TypePaymentContratLoyer typePaymentContratLoyer;
    @Enumerated(EnumType.STRING)
    private MethodePaymentContratLoyer methodePaymentContratLoyer;
    private boolean isDelete = Boolean.FALSE;
}
