package com.wyki.idsauth.controllers.wrappers;

import lombok.Data;

@Data
public class ResponseWrapper {
    String status;
    Object body;
}
