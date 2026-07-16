package com.heitor.checkingaccountoperation.functional;

import com.heitor.checkingaccountoperation.client.CheckingAccountOperationClient;
import com.heitor.checkingaccountoperation.dto.NoteOutputDto;
import com.heitor.checkingaccountoperation.dto.TransactionInputDto;
import com.heitor.checkingaccountoperation.dto.TransactionOutputDTO;
import com.heitor.checkingaccountoperation.util.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.UUID.randomUUID;
import static org.apache.http.HttpStatus.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AllureJunit5.class)
@TestMethodOrder(OrderAnnotation.class)
class ApiWithdrawalFunctionalTest {

    private CheckingAccountOperationClient checkingAccountOperationClient = new CheckingAccountOperationClient();

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
    void shouldWithdrawals() throws JsonProcessingException {
        Response response = checkingAccountOperationClient
                .postWithdrawals(String.valueOf(ACCOUNT_ID), new TransactionInputDto(VALUE), UUID)
                .statusCode(SC_CREATED)
                .extract()
                .response();

        List<NoteOutputDto> listMoney = Util.convertToListNoteOutputDto(response.getBody().print());

        assertThat(listMoney.get(1).getNote()).isEqualTo(10);
        assertThat(listMoney.get(1).getQuantity()).isEqualTo(1);
        assertThat(listMoney.get(0).getNote()).isEqualTo(20);
        assertThat(listMoney.get(0).getQuantity()).isEqualTo(1);
    }

    @Test
    @Order(3)
    void shouldNotWithdrawalsWithSameUUID() throws JsonProcessingException {
        checkingAccountOperationClient
                .postWithdrawals(String.valueOf(ACCOUNT_ID), new TransactionInputDto(30), UUID)
                .statusCode(SC_UNPROCESSABLE_ENTITY);
    }

    @Test
    @Order(4)
    void shouldGetWithdrawals() {
        Response response = checkingAccountOperationClient.getTrasanctions(String.valueOf(ACCOUNT_ID))
                .statusCode(SC_OK)
                .extract().response();

        List<TransactionOutputDTO> list = Util.convertToListTransactionOutputDTO(response.getBody().print());

        assertThat(list.get(0).getValue()).isEqualTo(VALUE);
        assertThat(list.get(0).getAccount()).isEqualTo(ACCOUNT_ID);
    }

}