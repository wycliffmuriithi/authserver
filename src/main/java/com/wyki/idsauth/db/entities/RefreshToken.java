package com.wyki.idsauth.db.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "oauth_refresh_token")
@Data
public class RefreshToken {
    @Column(name = "token_id") @Id
    String tokenid;
    @Column(name = "token")@Lob
    String token;
    @Column(name = "authentication")@Lob
    String authentication;
}
