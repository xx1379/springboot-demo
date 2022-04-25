package com.xk.kafka.consumer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/4/22 22:07
 * 基本使用
 */
public class KafkaProducerDemo1 {
    public static final String BROKE_LIST = "101.132.169.208:9092";
    public static final String TOPIC = "topic-demo";

    public static Properties initConfig() {
        Properties props = new Properties();
        /*
            bootstrap.servers用来指定生产者客户端连接Kafka集群所需的broker地址清单，格式为host1:port1,host2:port2
            建议至少要设置两个以上的broker地址信息，当任意一个宕机时，生产者仍然可以连接到Kafka
         */
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKE_LIST);
        /*
            必须指定key和value的序列化器，注意这两个参数无默认值，并且必须填写序列化器的全限定类名
         */
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        /*
            client.id指定客户端id，不设置kafka会自动生成
         */
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "producer.client.id.demo");
        /*
            重试次数，默认为0。配置后，对于可重试的异常，如果在规定的重试次数内自行恢复，就不会抛异常。
         */
        props.put(ProducerConfig.RETRIES_CONFIG, 10);
        return props;
    }

    public static void main(String[] args) throws InterruptedException {
        /*创建生产者*/
        KafkaProducer<String, String> producer = new KafkaProducer<>(initConfig());
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, "hello, Kafka!");
        /*
            发送消息模式一：发后即忘（fire-and-forget）
         */
//        try {
//            producer.send(record);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        /*
            发送消息模式二：同步（sync）
         */
//        try {
//            producer.send(record).get();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

         /*
            发送消息模式三：异步（async）
         */
        try {
            producer.send(record, (recordMetadata, e) -> {
                if (e != null) {
                    e.printStackTrace();
                } else {
                    System.out.println(recordMetadata.topic() + "-" + recordMetadata.partition()
                            + ":" + recordMetadata.offset());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread.sleep(1000);
        producer.close();
    }
}
