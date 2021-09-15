package com.wyki.idsauth.db.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name = "oauth_access_token") @Data
public class AccessToken {
    @Column(name = "token_id") @Id
    String token_id;
    @Column(name = "token",columnDefinition = "LONGBLOB")
    String token;
    @Column(name = "authentication_id")
    String authentication_id;
    @Column(name = "user_name")
    String user_name;
    @Column(name = "client_id")
    String client_id;
    @Column(name = "authentication",columnDefinition = "LONGBLOB")
    String authentication;
    @Column(name = "refresh_token")
    String refresh_token;
}
