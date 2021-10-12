package com.wyki.idsauth.controllers.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data @JsonIgnoreProperties(ignoreUnknown = true)
public class UserWrapper {
    String name;
    String email;
    String phonenumber;
    String password;
    Long roleid;
}
