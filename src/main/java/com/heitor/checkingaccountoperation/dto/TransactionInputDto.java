package com.heitor.checkingaccountoperation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class TransactionInputDto {

    private Integer value;

    public TransactionInputDto(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}