package com.heitor.checkingaccountoperation.event;

import com.google.gson.Gson;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;

public class Producer {

    private final KafkaProducer<String, String> producer;

    public Producer() {
        this.producer = new KafkaProducer<>(properties());
    }

    public void sendEvent(String topic, Object payload) {
        String json;
        if (payload instanceof String) {
            json = (String) payload;
        } else {
            var gson = new Gson();
            json = gson.toJson(payload);
        }

        var record = new ProducerRecord<>(topic, payload.getClass().getSimpleName(), json);
        try {
            producer.send(record).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
