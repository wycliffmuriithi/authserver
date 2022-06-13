package com.wyki.idsauth.controllers.wrappers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ResponseWrapper {

    String status;
    String message;
    Object body;

    public ResponseWrapper(String status,String message){
        this.status=status;
        this.message=message;
    }
}
