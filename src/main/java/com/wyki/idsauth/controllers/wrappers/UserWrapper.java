package com.wyki.idsauth.controllers.wrappers;

import lombok.Data;

import java.util.Date;

@Data
public class UserWrapper {
    long userid;
    String name;
    String nationalid;
    String email;
    String phonenumber;

    boolean active;

    Date createdon;
}
