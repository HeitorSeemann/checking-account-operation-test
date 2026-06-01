package com.pereira.contacorrente;

import com.pereira.contacorrente.client.CheckingAccountOperationClient;
import com.pereira.contacorrente.dto.NoteOutputDto;
import com.pereira.contacorrente.dto.TransactionInputDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.pereira.contacorrente.util.Util;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import java.util.List;

import static java.util.UUID.randomUUID;
import static org.apache.http.HttpStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.AssertJUnit.assertTrue;

@TestMethodOrder(OrderAnnotation.class)
class TransactionWithdrawalsTest {

    private CheckingAccountOperationClient checkingAccountOperationClient = new CheckingAccountOperationClient();

    public static final String ACCOUNT_ID = "12";
    public static final Integer VALUE = 30;
    public static final String UUID = randomUUID().toString();

    @Test
    @Order(1)
    void shouldWithdrawals() throws JsonProcessingException {
        Response response = checkingAccountOperationClient.postWithdrawals(ACCOUNT_ID, new TransactionInputDto(VALUE), UUID);
        List<NoteOutputDto> listMoney = Util.convertToListNoteOutputDto(response.getBody().print());

        assertThat(response.getStatusCode()).isEqualTo(SC_CREATED);
        assertThat(listMoney.get(0).getNote()).isEqualTo(10);
        assertThat(listMoney.get(0).getQuantity()).isEqualTo(1);
        assertThat(listMoney.get(1).getNote()).isEqualTo(20);
        assertThat(listMoney.get(1).getQuantity()).isEqualTo(1);
    }

    @Test
    @Order(2)
    void shouldNotWithdrawalsWithSameUUID() throws JsonProcessingException {
        Response response = checkingAccountOperationClient.postWithdrawals(ACCOUNT_ID, new TransactionInputDto(30), UUID);
        assertThat(response.getStatusCode()).isEqualTo(SC_UNPROCESSABLE_ENTITY);
    }

    @Test
    @Order(3)
    void shouldGetWithdrawals() {
        Response response = checkingAccountOperationClient.getTrasanctions(ACCOUNT_ID);
        assertThat(response.getStatusCode()).isEqualTo(SC_OK);
        assertTrue(response.getBody().print().contains(VALUE.toString()));
    }

}
