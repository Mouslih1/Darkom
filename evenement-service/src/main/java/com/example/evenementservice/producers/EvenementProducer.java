package com.example.evenementservice.producers;

import com.example.evenementservice.dto.EvenementProducerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EvenementProducer {

    private final KafkaTemplate<String, EvenementProducerDto> kafkaTemplate;

    public void producerMessage(EvenementProducerDto evenementProducerDto)
    {
        Message<EvenementProducerDto> message = MessageBuilder
                .withPayload(evenementProducerDto)
                .setHeader(KafkaHeaders.TOPIC, "evenement-topic")
                .build();

        kafkaTemplate.send(message);
    }}
