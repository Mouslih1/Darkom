package com.example.travauxservice.producers;

import com.example.travauxservice.dto.TravauxProducerDto;
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
public class TravauxProducer {

    private final KafkaTemplate<String, TravauxProducer> kafkaTemplate;

    public void producerMessage(TravauxProducerDto travauxProducerDto)
    {
        Message<TravauxProducerDto> message = MessageBuilder
                .withPayload(travauxProducerDto)
                .setHeader(KafkaHeaders.TOPIC, "travaux-topic")
                .build();

        kafkaTemplate.send(message);
    }
}
