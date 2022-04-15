package com.epam.autocode.kafka.game_rating;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;

import java.util.Collections;
import java.util.Properties;

public class KafkaTopicBuilder {

    private final Properties properties;

    public KafkaTopicBuilder(Properties properties) {
        this.properties = properties;
    }

    public void createTopic(String topicName) throws Exception {
        try (Admin admin = Admin.create(properties)) {
            int partitions = 1;
            short replicationFactor = 1;
            NewTopic newTopic = new NewTopic(topicName, partitions, replicationFactor);

            CreateTopicsResult result = admin.createTopics(Collections.singleton(newTopic));

            KafkaFuture<Void> future = result.values()
                .get(topicName);

            future.get();
        }
    }

}
