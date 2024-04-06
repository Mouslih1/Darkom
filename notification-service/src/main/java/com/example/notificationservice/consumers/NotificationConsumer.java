package com.example.notificationservice.consumers;

import com.example.evenementservice.dto.EvenementProducerDto;
import com.example.notificationservice.services.INotificationService;
import com.example.paymentsyndecalservice.dtos.PaymentSyndicatProducerDto;
import com.example.plainte.dto.PlainteProducerDto;
import com.example.travauxservice.dto.TravauxProducerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {

    private final INotificationService notificationService;

    @KafkaListener(topics = "travaux-topic", groupId = "myGroup")
    public void sendTravauxNotification(TravauxProducerDto travauxProducerDto)
    {
        log.info("Received travauxProducerDto: {}", travauxProducerDto);
        notificationService.sendTravauxNotifications(travauxProducerDto);
        notificationService.sendTravauxNotificationForCreator(travauxProducerDto);
    }

    @KafkaListener(topics = "plainte-topic", groupId = "myGroup")
    public void sendPlaintesNotification(PlainteProducerDto plainteProducerDto)
    {
        log.info("Received plainteProducerDto: {}", plainteProducerDto);
        notificationService.sendPlaintesNotifications(plainteProducerDto);
        notificationService.sendPlainteNotificationForCreator(plainteProducerDto);
    }

    @KafkaListener(topics = "evenement-topic", groupId = "myGroup")
    public void sendEvenementsNotification(EvenementProducerDto evenementProducerDto)
    {
        log.info("Received evenementProducerDto: {}", evenementProducerDto);
        notificationService.sendEvenementNotifications(evenementProducerDto);
        notificationService.sendEvenementNotificationForCreator(evenementProducerDto);
    }

    @KafkaListener(topics = "payment-syndicat-topic", groupId = "myGroup")
    public void sendPaymentSyndicatNotification(PaymentSyndicatProducerDto paymentSyndicatProducerDto)
    {
        log.info("Received evenementProducerDto: {}", paymentSyndicatProducerDto);
        notificationService.sendPaymentsSyndicatNotifications(paymentSyndicatProducerDto);
        notificationService.sendPayementsSyndicatNotificationForPayer(paymentSyndicatProducerDto);
    }
}
