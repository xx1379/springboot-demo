package com.xk.kafka.consumer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/4/25 23:15
 * 测试事务
 */
public class KafkaProducerDemo4 {
    public static final String BROKE_LIST = "101.132.169.208:9092";
    public static final String TOPIC = "topic-demo";

    public static Properties initConfig() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKE_LIST);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 设置事务 id，事务 id 任意起名
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction_id_0");
        return props;
    }

    public static void main(String[] args) throws InterruptedException {
        KafkaProducer<String, String> producer = new KafkaProducer<>(initConfig());
        // 初始化事务
        producer.initTransactions();
        // 开启事务
        producer.beginTransaction();
        try {
            for (int i = 0; i < 5; i++) {
                producer.send(new ProducerRecord<>(TOPIC, "hello, Kafka: " + i));
                ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, "hello, Kafka: " + i);
            }

            // 提交事务
            producer.commitTransaction();
        } catch (Exception e) {
            // 终止事务
            producer.abortTransaction();
        } finally {
            producer.close();
        }
    }
}
