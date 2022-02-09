package com.wyki.idsauth.db.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oauth_refresh_token")
@Data
public class RefreshToken {
    @Column(name = "token_id") @Id
    String tokenid;
    @Column(name = "token",columnDefinition = "BLOB")
    String token;
    @Column(name = "authentication",columnDefinition = "BLOB")
    String authentication;
}
