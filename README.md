# kafka-autocode-game_rating
Imagine a site that has an Apache Kafka cluster. Messages about pre-orders of two(or more) popular games are sent to the topics.
## Description
You need to proceed to [KafkaProducer](src/main/java/com/epam/autocode/kafka/game_rating/KafkaProducer.java)
and write a method for this class that will send new
[ProducerRecord](https://kafka.apache.org/23/javadoc/org/apache/kafka/clients/producer/ProducerRecord.html)
to selected Topic. Each game has its own topic.
Method have <b>three</b> String arguments. Selected topic, key, and value.

    public void send(String topicName, String key, String value) {}

Also, you need to write method that will create a new topic from your application.
You can create new topic via [KafkaTopicBuilder](src/main/java/com/epam/autocode/kafka/game_rating/KafkaTopicBuilder.java)

    public void createTopic(String topicName) {}


## Example
The site has an Apache Kafka cluster. Messages about pre-orders of two(or more) popular games are sent to the topic. Need to create an app that will display daily(or other period) pre-order statistics and the winner of “salles race”.
Topics creates for <i>each</i> game.

To calculate it, you need to aggregate the amounts from pre-orders into separate topics.
