package com.example.plainte.producers;

import com.example.plainte.dto.PlainteProducerDto;
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
public class PlainteProducer {

    private final KafkaTemplate<String, PlainteProducerDto> kafkaTemplate;

    public void producerMessage(PlainteProducerDto plainteProducerDto)
    {
        Message<PlainteProducerDto> message = MessageBuilder
                .withPayload(plainteProducerDto)
                .setHeader(KafkaHeaders.TOPIC, "plainte-topic")
                .build();

        kafkaTemplate.send(message);
    }
}
