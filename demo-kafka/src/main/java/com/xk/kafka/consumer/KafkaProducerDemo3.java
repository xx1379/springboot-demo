package com.xk.kafka.consumer;

import com.xk.kafka.config.ProducerInterceptorPrefix;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/4/24 23:33
 * 自定义拦截器
 */
public class KafkaProducerDemo3 {
    public static final String BROKE_LIST = "101.132.169.208:9092";
    public static final String TOPIC = "topic-demo";

    public static Properties initConfig() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKE_LIST);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, ProducerInterceptorPrefix.class.getName());
        return props;
    }

    public static void main(String[] args) throws InterruptedException {
        /*创建生产者*/
        KafkaProducer<String, String> producer = new KafkaProducer<>(initConfig());
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, "hello, Kafka!");

        try {
            producer.send(record).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.close();
    }
}
