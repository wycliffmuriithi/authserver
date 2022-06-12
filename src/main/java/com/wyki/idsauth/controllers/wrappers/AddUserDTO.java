package com.wyki.idsauth.controllers.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data @JsonIgnoreProperties(ignoreUnknown = true) @JsonInclude(JsonInclude.Include.NON_NULL)
public class AddUserDTO {
    String name;
    String nationalid;
    String email;
    String phonenumber;
}
