package com.heitor.checkingaccountoperation.chaos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.heitor.checkingaccountoperation.client.CheckingAccountOperationClient;
import com.heitor.checkingaccountoperation.dto.TransactionInputDto;
import com.heitor.checkingaccountoperation.event.Producer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.UUID.randomUUID;
import static org.apache.http.HttpStatus.*;

public class DataBaseChaosTest {

    private static DockerClient dockerClient;
    private static final String DB_CONTAINER_NAME = "checking_account_db";
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
    public void testApplicationResilienceAndSelfHealingWhenDatabaseRecovers() throws InterruptedException {
        try {
            dockerClient.stopContainerCmd(DB_CONTAINER_NAME).exec();
            Thread.sleep(8000);

            checkingAccountOperationClient
                .postWithdrawals(String.valueOf(ACCOUNT_ID), new TransactionInputDto(VALUE), UUID)
                .statusCode(SC_INTERNAL_SERVER_ERROR);

            dockerClient.startContainerCmd(DB_CONTAINER_NAME).exec();
            Thread.sleep(31000);

            checkingAccountOperationClient
                    .postWithdrawals(String.valueOf(ACCOUNT_ID), new TransactionInputDto(VALUE), UUID)
                    .statusCode(SC_CREATED);

            checkingAccountOperationClient.getTrasanctions(String.valueOf(ACCOUNT_ID))
                    .statusCode(SC_OK);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                dockerClient.startContainerCmd(DB_CONTAINER_NAME).exec();
                Thread.sleep(5000);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}