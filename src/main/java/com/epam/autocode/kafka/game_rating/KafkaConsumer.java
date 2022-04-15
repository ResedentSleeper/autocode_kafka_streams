package com.epam.autocode.kafka.game_rating;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.stream.StreamSupport;

public class KafkaConsumer {

    private static Logger logger = LoggerFactory.getLogger(Game.class);

    private Consumer<String, Integer> consumer;
    private java.util.function.Consumer<Throwable> exceptionConsumer;
    private java.util.function.Consumer<Game> gameConsumer;

    public KafkaConsumer(Consumer<String, Integer> consumer, java.util.function.Consumer<Throwable> exceptionConsumer, java.util.function.Consumer<Game> gameConsumer) {
        this.consumer = consumer;
        this.exceptionConsumer = exceptionConsumer;
        this.gameConsumer = gameConsumer;
    }

    void startBySubscribing(String topic) {
        consume(() -> consumer.subscribe(Collections.singleton(topic)));
    }

    void startByAssigning(String topic, int partition) {
        consume(() -> consumer.assign(Collections.singleton(new TopicPartition(topic, partition))));
    }

    private void consume(Runnable beforePollingTask) {
        try {
            beforePollingTask.run();
            while (true) {
                ConsumerRecords<String, Integer> records = consumer.poll(Duration.ofMillis(1000));
                StreamSupport.stream(records.spliterator(), false)
                        .map(record -> new Game(record.key(), record.value()))
                        .forEach(gameConsumer);
                consumer.commitSync();
            }
        } catch (WakeupException e) {
            logger.info("Shutting down...");
        } catch (RuntimeException ex) {
            exceptionConsumer.accept(ex);
        } finally {
            consumer.close();
        }
    }

    public void stop() {
        consumer.wakeup();
    }
}