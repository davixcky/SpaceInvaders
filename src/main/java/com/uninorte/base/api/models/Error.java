package com.uninorte.base.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.awt.*;
import java.util.Arrays;

public class Error extends Base {
    @JsonProperty("errors") protected String[] errors;

    public Error(String[] errors) {
        this.errors = errors;
    }

    public Error(String message) {
        errors = new String[] {message};
    }

    public Error() {
    }

    public static Error createFromJson(String json) {
        return (Error) new Error().fromJson(json);
    }

    public String getErrorMessage() {
        return errors[0];
    }

    @Override
    public String toString() {
        return "Error{" +
                "errors=" + Arrays.toString(errors) +
                '}';
    }
}