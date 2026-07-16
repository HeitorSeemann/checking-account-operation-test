package com.heitor.checkingaccountoperation.dto;

import lombok.Getter;

public class NoteOutputDto {

    private Integer note;
    private Integer quantity;

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}