package com.xk.kafka.consumer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.ExecutionException;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/4/24 22:08
 */
@SpringBootTest
public class KafkaProducerTest {
    public static final String TOPIC = "topic-demo";

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    public void testKafkaProducer() throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, "hello, kafkaTemplate!");
        kafkaTemplate.send(record).get();
    }
}
