package com.pereira.contacorrente.client;

import static io.restassured.RestAssured.given;

import com.pereira.contacorrente.dto.TransactionInputDto;
import com.pereira.contacorrente.util.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CheckingAccountOperationClient {

    public Response getTrasanctions(String account) {
        return
            given().
                pathParam("accountId", account).
            when().
                get("http://localhost:8080/accounts/withdrawals/{accountId}").
            then().
                extract().
                    response();
    }

    public Response postWithdrawals(String account, TransactionInputDto dto, String key) throws JsonProcessingException {
        return
            given().
                pathParam("accountId", account).
                header("Idempotency-Key", key).
                contentType(ContentType.JSON).
                body(Util.toJson(dto)).
            when().
                post("http://localhost:8080/accounts/withdrawals/{accountId}").
            then().
                extract().
                response();
    }

}
