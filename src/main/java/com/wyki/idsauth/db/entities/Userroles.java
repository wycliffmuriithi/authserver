package com.wyki.idsauth.db.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * Class name: Userroles
 * Creater: wgicheru
 * Date:3/2/2020
 */
@Table(name="users_roles") @Entity @Data
public class Userroles {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne
    @JoinColumn(name="userid")
    Users users;
    @ManyToOne
    @JoinColumn(name="roleid")
    Roles roles;
}
