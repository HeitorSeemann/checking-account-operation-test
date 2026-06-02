package com.heitor.checkingaccountoperation.client;

import static io.restassured.RestAssured.given;

import com.heitor.checkingaccountoperation.dto.TransactionInputDto;
import com.heitor.checkingaccountoperation.util.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class CheckingAccountOperationClient {

    public ValidatableResponse getTrasanctions(String account) {
        return
            given().
                pathParam("accountId", account).
            when().
                get("http://localhost:8080/accounts/withdrawals/{accountId}").
            then();
    }

    public ValidatableResponse postWithdrawals(String account, TransactionInputDto dto, String key) throws JsonProcessingException {
        return
            given().
                pathParam("accountId", account).
                header("Idempotency-Key", key).
                contentType(ContentType.JSON).
                body(Util.toJson(dto)).
            when().
                post("http://localhost:8080/accounts/withdrawals/{accountId}").
            then();
    }

}