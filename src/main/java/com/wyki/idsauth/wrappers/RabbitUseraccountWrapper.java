package com.wyki.idsauth.wrappers;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class name: RabbitUseraccountWrapper
 * Creater: wgicheru
 * Date:2/5/2020
 */
@Data
@AllArgsConstructor
public class RabbitUseraccountWrapper {
    String email;
    String phonenumber;
}
