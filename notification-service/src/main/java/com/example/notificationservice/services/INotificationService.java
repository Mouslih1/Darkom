package com.example.notificationservice.services;

import com.example.evenementservice.dto.EvenementProducerDto;
import com.example.paymentsyndecalservice.dtos.PaymentSyndicatProducerDto;
import com.example.plainte.dto.PlainteProducerDto;
import com.example.travauxservice.dto.TravauxProducerDto;


public interface INotificationService {

    void sendTravauxNotifications(TravauxProducerDto travauxProducerDto);
    void sendTravauxNotificationForCreator(TravauxProducerDto travauxProducerDto);
    void sendPaymentsSyndicatNotifications(PaymentSyndicatProducerDto paymentSyndicatProducerDto);
    void sendPayementsSyndicatNotificationForPayer(PaymentSyndicatProducerDto paymentSyndicatProducerDto);
    void sendEvenementNotifications(EvenementProducerDto evenementProducerDto);
    void sendPlaintesNotifications(PlainteProducerDto plainteProducerDto);
    void sendPlainteNotificationForCreator(PlainteProducerDto plainteProducerDto);
    void sendEvenementNotificationForCreator(EvenementProducerDto evenementProducerDto);
    void deleteNotificationFirebase(String key);
    void markAsSeenFirebase(String key);
}
