package com.wyki.idsauth.wrappers;

import lombok.Data;
import lombok.ToString;

/**
 * Class name: RabbitResourceCreation
 * Creater: wgicheru
 * Date:1/30/2020
 */
@Data @ToString
public class RabbitResourceCreation {
    String resourcename;
    String secret;
}
