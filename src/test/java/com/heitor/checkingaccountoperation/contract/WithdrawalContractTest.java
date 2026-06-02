package com.heitor.checkingaccountoperation.contract;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.heitor.checkingaccountoperation.client.CheckingAccountOperationClient;
import com.heitor.checkingaccountoperation.dto.TransactionInputDto;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.util.UUID.randomUUID;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WithdrawalContractTest {

    private CheckingAccountOperationClient checkingAccountOperationClient = new CheckingAccountOperationClient();

    public static final String ACCOUNT_ID = String.valueOf(ThreadLocalRandom.current().nextInt(10000, 100000));
    public static final Integer VALUE = 30;
    public static final String UUID = randomUUID().toString();

    @Test
    @Order(1)
    public void shouldValidatecontractPostWithdrawal() throws JsonProcessingException {
        checkingAccountOperationClient.postWithdrawals(ACCOUNT_ID, new TransactionInputDto(VALUE), UUID)
                .statusCode(SC_CREATED)
                .contentType(ContentType.JSON)
                .body(matchesJsonSchemaInClasspath("json-schemas/post-withdrawal-json-schema.json"));
    }

    @Test
    @Order(2)
    public void shouldValidatecontractGetWithdrawal() {
        checkingAccountOperationClient.getTrasanctions(ACCOUNT_ID)
                .statusCode(SC_OK)
                .contentType(ContentType.JSON)
                .body(matchesJsonSchemaInClasspath("json-schemas/withdrawals-history-schema.json"));
    }

}