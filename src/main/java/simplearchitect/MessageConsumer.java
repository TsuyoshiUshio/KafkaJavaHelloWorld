package simplearchitect;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MessageConsumer {
    static final Logger LOG = LoggerFactory.getLogger(MessageConsumer.class);
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public Future<Void> consumeMessage() {
        return executor.submit(() -> {
            Properties config = new Properties();
            config.put("client.id", InetAddress.getLocalHost().getHostName());
            config.put("group.id", "$Default");
            config.put("bootstrap.servers", "localhost:31090");
            config.put("key.deserializer",
                    "org.apache.kafka.common.serialization.StringDeserializer");

            config.put("value.deserializer",
                    "org.apache.kafka.common.serialization.StringDeserializer");
            KafkaConsumer<String, String> consumer = new KafkaConsumer<String,String>(config);
            List<String> topics = new ArrayList<String>();
            topics.add("test10");
            consumer.subscribe(topics);
            for (int i = 0; i < 10; i++) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds((10)));
                process(records);
                consumer.commitSync();
            }

          return null;
        });
    }

    private void process(ConsumerRecords<String, String> records) {
        records.forEach((x) -> {
            LOG.info("[Consumer]: " + x.value());
        });
    }
}
