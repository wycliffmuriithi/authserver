package com.wyki.idsauth.wrappers;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class name: RabbitUpdateuserPassword
 * Creater: wgicheru
 * Date:2/4/2020
 */
@Data
@AllArgsConstructor
public class RabbitUpdateuserPassword {
    String email;
    String phonenumber;
    String password;
}
