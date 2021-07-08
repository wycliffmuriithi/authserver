package com.wyki.idsauth.wrappers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class name: RabbitUpdateuserPassword
 * Creater: wgicheru
 * Date:2/4/2020
 */
@Data
public class RabbitUpdateuserPassword {
    String email;
    String phonenumber;
    String password;
}
