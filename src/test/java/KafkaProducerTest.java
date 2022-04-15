package com.epam.autocode.kafka.game_rating;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

class KafkaProducerTest {

    private KafkaProducer kafkaProducer;
    private MockProducer<String, String> mockProducer;

    private void buildMockProducer(boolean autoComplete) {
        this.mockProducer = new MockProducer<>(autoComplete, new StringSerializer(), new StringSerializer());
    }

    @Test
    void testSend() throws ExecutionException, InterruptedException {
        buildMockProducer(true);

        kafkaProducer = new KafkaProducer(mockProducer);
        Future<RecordMetadata> recordMetadataFuture = kafkaProducer.send("Dad of Boy GOTY","preorder", "4000");

        assertTrue(mockProducer.history().size() == 1);
        assertTrue(mockProducer.history().get(0).key().equalsIgnoreCase("data"));
        assertTrue(recordMetadataFuture.get().partition() == 0);

    }

}