package com.uninorte.base.api.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.io.IOException;

public abstract class Base {
    public Base fromJson(String json) {
        Base object = null;
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            object =  mapper.readValue(json, this.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return object;
    }

    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(this);
        }  catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    public String toString() {
        return "Base class";
    }

    public static Base createFromJson(String json) {
        return null;
    }
}
