package simplearchitect;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MessageProducer {
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public Future<Void> sendMessage(String message) throws UnknownHostException {
        return executor.submit(() -> {
            Properties config = new Properties();
            config.put("client.id", InetAddress.getLocalHost().getHostName());
            config.put("bootstrap.servers", "localhost:31090");
            config.put("acks", "all");
            config.put("key.serializer",
                    "org.apache.kafka.common.serialization.StringSerializer");

            config.put("value.serializer",
                    "org.apache.kafka.common.serialization.StringSerializer");
            Producer<String, String> producer = new KafkaProducer<String, String>(config);
            for (int i = 0; i < 10; i++) {
                System.out.println("Sendding message");
                producer.send(new ProducerRecord<String, String>(
                        "test10",
                        Integer.toString(i), "[MessageProducer]: Hello " + message + Integer.toString(i)
                ));
            }
            return null;
        });
    }
}
