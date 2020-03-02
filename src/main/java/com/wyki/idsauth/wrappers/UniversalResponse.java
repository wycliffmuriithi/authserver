package com.wyki.idsauth.wrappers;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UniversalResponse {
    private int status;
    private Object data;
    private String timestamp;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object metadata;

    public UniversalResponse() {
        this.timestamp = new Date().toString();
    }

    public UniversalResponse(int status, Object data) {
        this.status = status;
        this.data = data;
        this.timestamp = new Date().toString();
    }
}
