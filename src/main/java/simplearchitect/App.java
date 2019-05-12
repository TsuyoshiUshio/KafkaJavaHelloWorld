package simplearchitect;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsOptions;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaFuture;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Hello world!
 *
 */
public class App 
{
    static final Logger LOG = LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {

//        System.out.println("Create Topic");
//        createTopic();
//        System.out.println("Done");
        LOG.info("hello log *****************");
        MessageProducer producer = new MessageProducer();
        try {

            Future<Void> future = producer.sendMessage("World");
            future.get();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    private static AdminClient createAdminClient() {
        Map<String, Object> conf = new HashMap<>();
        conf.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:31090");
        conf.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, "5000");
        return AdminClient.create(conf);
    }
    public static void createTopic() {
        AdminClient client = createAdminClient();
        int partitions = 3;
        short replicationFactor = 2;
        try {
            KafkaFuture<Void> future = client.createTopics(Collections.singleton(new NewTopic("test10", partitions, replicationFactor)), new CreateTopicsOptions().timeoutMs(10000)).all();
            future.get();
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
    }
}
