package com.heitor.checkingaccountoperation.chaos;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.heitor.checkingaccountoperation.client.CheckingAccountOperationClient;
import com.heitor.checkingaccountoperation.dto.TransactionOutputDTO;
import com.heitor.checkingaccountoperation.event.Producer;
import com.heitor.checkingaccountoperation.util.Util;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.UUID.randomUUID;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class KafkaChaosTest {

    private static DockerClient dockerClient;
    private static final String API_CONTAINER_NAME = "checking-account-app";
    private CheckingAccountOperationClient checkingAccountOperationClient = new CheckingAccountOperationClient();
    private Producer producer = new Producer();
    public static final Integer ACCOUNT_ID = ThreadLocalRandom.current().nextInt(10000, 100000);
    public static final Integer VALUE = 30;
    public static final String UUID = randomUUID().toString();

    @BeforeAll
    public static void setup() {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();

        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();

        dockerClient = DockerClientImpl.getInstance(config, httpClient);
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void shouldProcessMessageSentWhenApiIsDead() throws InterruptedException {
        try {
            dockerClient.stopContainerCmd(API_CONTAINER_NAME).exec();
            Thread.sleep(8000);

            String jsonPayload = "{\"accountId\": " + ACCOUNT_ID + ", \"uuid\":\"" + UUID + "\", \"amount\": " + VALUE + "}";
            producer.sendEvent("account-withdrawals", jsonPayload);

            dockerClient.startContainerCmd(API_CONTAINER_NAME).exec();
            Thread.sleep(16000);

            Response response = checkingAccountOperationClient.getTrasanctions(String.valueOf(ACCOUNT_ID))
                    .statusCode(SC_OK)
                    .extract().response();

            List<TransactionOutputDTO> list = Util.convertToListTransactionOutputDTO(response.getBody().print());
            assertThat(list.get(0).getValue()).isEqualTo(VALUE);
            assertThat(list.get(0).getAccount()).isEqualTo(ACCOUNT_ID);

        } finally {
            try {
                dockerClient.startContainerCmd(API_CONTAINER_NAME).exec();
                Thread.sleep(5000);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}