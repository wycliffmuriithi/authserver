package com.wyki.idsauth.controllers.wrappers;

import lombok.Data;

@Data
public class UserWrapper {
    String name;
    String email;
    String phonenumber;
    String password;

}
