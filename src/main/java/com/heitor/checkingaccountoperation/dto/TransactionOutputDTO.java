package com.heitor.checkingaccountoperation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class TransactionOutputDTO {

    private Long id;

    private Integer account;

    private Integer value;

    private String type;

    private String date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}