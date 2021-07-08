package com.wyki.idsauth.wrappers;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Class name: RabbitUserCreation
 * Creater: wgicheru
 * Date:1/30/2020
 */
@Data @ToString
public class MessageBrokerUserCreation {
    String firstname;
    String othernames;
    String email;
    String phonenumber;
    Date dateofbirth;
    String gender;
    String nationality;
    String nationalidnumber;
    String resourceid;
    String rolename;
}
