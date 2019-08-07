package com.wyki.idsauth.db.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Class name: OauthclientDetails
 * Creater: wgicheru
 * Date:6/18/2019
 */
@Entity
@Table(name = "oauth_client_details")
@Data
public class OauthclientDetails implements Serializable {
    @Id
    @Column(name = "client_id")
    String clientId;
    @Column(name = "resource_ids")
    String resourceIds;
    @Column(name = "client_secret")
    String clientSecret;
    String scope;
    @Column(name = "authorized_grant_types")
    String authorizedGrantTypes;
    @Column(name = "web_server_redirect_uri")
    String webServerRedirectUri;
    String authorities;
    @Column(name = "access_token_validity")
    int accessTokenValidity;
    @Column(name = "refresh_token_validity")
    int refreshTokenValidity;
    @Column(name = "additional_information")
    String additionalInformation;
    String autoapprove;

}
