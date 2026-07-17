package com.heitor.checkingaccountoperation.functional;

import com.heitor.checkingaccountoperation.client.CheckingAccountOperationClient;
import com.heitor.checkingaccountoperation.dto.TransactionOutputDTO;
import com.heitor.checkingaccountoperation.event.Producer;
import com.heitor.checkingaccountoperation.util.Util;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.Response;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import static java.util.UUID.randomUUID;
import static org.apache.http.HttpStatus.*;
import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(OrderAnnotation.class)
class KafkaWithdrawalFunctionalTest {

    private CheckingAccountOperationClient checkingAccountOperationClient = new CheckingAccountOperationClient();
    private Producer producer = new Producer();
    public static final Integer ACCOUNT_ID = ThreadLocalRandom.current().nextInt(10000, 100000);
    public static final Integer VALUE = 30;
    public static final String UUID = randomUUID().toString();

    @Test
    @Order(1)
    void shouldNotGetWithdrawalsNotFound() {
        checkingAccountOperationClient.getTrasanctions(String.valueOf(ACCOUNT_ID))
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @Order(2)
    void shouldWithdrawals() throws ExecutionException, InterruptedException {
        String jsonPayload = "{\"accountId\": " + ACCOUNT_ID + ", \"uuid\":\"" + UUID + "\", \"amount\": " + VALUE + "}";
        producer.sendEvent("account-withdrawals", jsonPayload);
        Thread.sleep(4000);

        Response response = checkingAccountOperationClient.getTrasanctions(String.valueOf(ACCOUNT_ID))
                .statusCode(SC_OK)
                .extract().response();

        List<TransactionOutputDTO> list = Util.convertToListTransactionOutputDTO(response.getBody().print());
        assertThat(list.get(0).getValue()).isEqualTo(VALUE);
        assertThat(list.get(0).getAccount()).isEqualTo(ACCOUNT_ID);
    }
}
