package com.wyki.idsauth.controllers.wrappers;

import lombok.Data;

@Data
public class PasswordDTO {
    String password;
    String otp;
    String nationalid;
}
