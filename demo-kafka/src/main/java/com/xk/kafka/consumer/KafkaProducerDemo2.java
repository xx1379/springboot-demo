package com.xk.kafka.consumer;

import com.xk.kafka.config.CompanySerializer;
import com.xk.kafka.config.DemoPartitioner;
import com.xk.kafka.po.Company;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/4/24 22:44
 * 自定义序列化器和分区器
 */
public class KafkaProducerDemo2 {
    public static final String BROKE_LIST = "101.132.169.208:9092";
    public static final String TOPIC = "topic-demo";

    public static Properties initConfig() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKE_LIST);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CompanySerializer.class.getName());
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, DemoPartitioner.class.getName());
        return props;
    }

    public static void main(String[] args) {
        KafkaProducer<String, Company> producer = new KafkaProducer<>(initConfig());
        Company company = Company.builder().name("xk").address("china").build();
        ProducerRecord<String, Company> record = new ProducerRecord<>(TOPIC, company);
        try {
            producer.send(record).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
