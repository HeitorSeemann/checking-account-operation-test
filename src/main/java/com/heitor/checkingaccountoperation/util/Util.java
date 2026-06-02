package com.heitor.checkingaccountoperation.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heitor.checkingaccountoperation.dto.NoteOutputDto;
import com.heitor.checkingaccountoperation.dto.TransactionOutputDTO;

import java.util.List;

public class Util {

    public static String toJson(Object object) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }

    public static List<NoteOutputDto> convertToListNoteOutputDto(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, new TypeToken<List<NoteOutputDto>>(){}.getType());
    }

    public static List<TransactionOutputDTO> convertToListTransactionOutputDTO(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, new TypeToken<List<TransactionOutputDTO>>(){}.getType());
    }

}