import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;


public class ProducerTest {

    @BeforeAll
    void setUp(){
        KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));
    }


    @Test
    void testTopicIsCreated() throws Exception {

    }

    @Test
    void testSendKeyToTopic(String expected, String actual) throws Exception {

    }

    @Test
    void testAggregate(){

    }

}
