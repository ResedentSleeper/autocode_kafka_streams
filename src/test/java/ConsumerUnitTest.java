package com.epam.autocode.kafka.game_rating;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class ConsumerUnitTest {

    private static final String TOPIC = "Game";
    private static final String GAME_1 = "Dad of Boy";
    private static final String GAME_2 = "Rush B Simulator";
    private static final int PARTITION = 0;

    private KafkaConsumer consumerKafka;

    private List<Game> updates;
    private Throwable pollException;

    private MockConsumer<String, Integer> consumer;

    @BeforeEach
    void setUp() {
        consumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
        updates = new ArrayList<>();
        consumerKafka = new KafkaConsumer(consumer, ex -> this.pollException = ex, updates::add);
    }

    @Test
    void testConsumeTopicPartition() {
        consumer.schedulePollTask(() -> consumer.addRecord(record(TOPIC, PARTITION, GAME_1, 10000)));
        consumer.schedulePollTask(() -> consumerKafka.stop());

        HashMap<TopicPartition, Long> startOffsets = new HashMap<>();
        TopicPartition tp = new TopicPartition(TOPIC, PARTITION);
        startOffsets.put(tp, 0L);
        consumer.updateBeginningOffsets(startOffsets);

        consumerKafka.startByAssigning(TOPIC, PARTITION);

        assertThat(updates).hasSize(1);
        assertThat(consumer.closed()).isTrue();
    }

    @Test
    void  testConsumeSubscribingToTopic() {
        consumer.schedulePollTask(() -> {
            consumer.rebalance(Collections.singletonList(new TopicPartition(TOPIC, 0)));
            consumer.addRecord(record(TOPIC, PARTITION, GAME_2, 50000));
        });
        consumer.schedulePollTask(() -> consumerKafka.stop());

        HashMap<TopicPartition, Long> startOffsets = new HashMap<>();
        TopicPartition tp = new TopicPartition(TOPIC, PARTITION);
        startOffsets.put(tp, 0L);
        consumer.updateBeginningOffsets(startOffsets);

        consumerKafka.startBySubscribing(TOPIC);

        assertThat(updates).hasSize(1);
        assertThat(consumer.closed()).isTrue();
    }

    private ConsumerRecord<String, Integer> record(String topic, int partition, String gameName, int preorders) {
        return new ConsumerRecord<>(topic, partition, 0, gameName, preorders);
    }
}