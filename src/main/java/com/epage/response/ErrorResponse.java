package com.epage.response;

public class ErrorResponse {
    private String error;

    // 생성자
    public ErrorResponse(String error) {
        this.error = error;
    }

    // Getter와 Setter
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
