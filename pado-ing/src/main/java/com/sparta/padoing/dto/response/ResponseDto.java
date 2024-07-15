package com.sparta.padoing.dto.response;

import java.time.LocalDate;

public class ResponseDto<T> {

    private String resultCode;
    private T data;
    private String message;
    private LocalDate startDate;
    private LocalDate endDate;

    public ResponseDto() {
        super();
    }

    public ResponseDto(String resultCode, T data, String message) {
        super();
        this.resultCode = resultCode;
        this.data = data;
        this.message = message;
    }

    public ResponseDto(String resultCode, T data, String message, LocalDate startDate, LocalDate endDate) {
        super();
        this.resultCode = resultCode;
        this.data = data;
        this.message = message;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}