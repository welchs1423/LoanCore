package com.finance.dto;

public class ApiResponse<T> {
    private int status;
    private String message;
    private String code;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(int status, String message, String code, T data) {
        this.status = status;
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>(200, "성공", "S000", data);
    }

    // 수정된 부분: <Void> 대신 <T>를 사용하여 어떤 컨트롤러 응답 타입이든 맞춰줄 수 있게 변경
    public static <T> ApiResponse<T> error(int status, String message, String code) {
        return new ApiResponse<T>(status, message, code, null);
    }
}