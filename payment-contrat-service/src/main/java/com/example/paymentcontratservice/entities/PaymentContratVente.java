package com.example.paymentcontratservice.entities;

import com.example.paymentcontratservice.entities.enums.MethodePaymentContratVente;
import com.example.paymentcontratservice.entities.enums.TypePaymentContratVente;
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
@Table(name = "payment_contrat_ventes")
@SQLDelete(sql = "UPDATE payment_contrat_ventes SET is_delete = true WHERE id=?")
@Builder
public class PaymentContratVente extends PaymentContrat{

    @Enumerated(EnumType.STRING)
    private TypePaymentContratVente typePaymentContratVente;
    @Enumerated(EnumType.STRING)
    private MethodePaymentContratVente methodePaymentContratVente;
    private double montantRester;

    private boolean isDelete = Boolean.FALSE;
}
