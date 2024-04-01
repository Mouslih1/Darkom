package com.example.paymentsyndecalservice.producers;

import com.example.paymentsyndecalservice.dtos.PaymentSyndicatProducerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentSyndicatProducer {

    private final KafkaTemplate<String, PaymentSyndicatProducerDto> kafkaTemplate;

    public void producerMessage(PaymentSyndicatProducerDto paymentSyndicatProducerDto)
    {
        Message<PaymentSyndicatProducerDto> message = MessageBuilder
                .withPayload(paymentSyndicatProducerDto)
                .setHeader(KafkaHeaders.TOPIC, "payment-syndicat-topic")
                .build();

        kafkaTemplate.send(message);
    }
}
