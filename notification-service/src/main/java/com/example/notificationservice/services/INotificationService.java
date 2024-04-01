package com.example.notificationservice.services;

import com.example.evenementservice.dto.EvenementProducerDto;
import com.example.notificationservice.dtos.NotificationDto;
import com.example.notificationservice.entities.Notification;
import com.example.paymentsyndecalservice.dtos.PaymentSyndecalDto;
import com.example.paymentsyndecalservice.dtos.PaymentSyndicatProducerDto;
import com.example.plainte.dto.PlainteProducerDto;
import com.example.travauxservice.dto.TravauxProducerDto;

import java.util.List;

public interface INotificationService {

    void sendTravauxNotifications(TravauxProducerDto travauxProducerDto);
    void sendTravauxNotificationForCreator(TravauxProducerDto travauxProducerDto);
    List<NotificationDto> allNotificationByMember(Long userId);
    void markNotificationAsSeen(Long notificationId, Long userId);
    NotificationDto byId(Long id, Long userId);
    void sendPaymentsSyndicatNotifications(PaymentSyndicatProducerDto paymentSyndicatProducerDto);
    void sendPayementsSyndicatNotificationForPayer(PaymentSyndicatProducerDto paymentSyndicatProducerDto);
    void sendEvenementNotifications(EvenementProducerDto evenementProducerDto);
    void sendPlaintesNotifications(PlainteProducerDto plainteProducerDto);
    void sendPlainteNotificationForCreator(PlainteProducerDto plainteProducerDto);
    void sendEvenementNotificationForCreator(EvenementProducerDto evenementProducerDto);

}
