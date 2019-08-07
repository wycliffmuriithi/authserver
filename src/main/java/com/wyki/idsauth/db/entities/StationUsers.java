package com.wyki.idsauth.db.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Class name: StationUsers
 * Creater: wgicheru
 * Date:6/17/2019
 */
@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class StationUsers implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long stationuserid;
    @Column(name="stations_stationid")
    long stationid;
    @Column(columnDefinition="longtext")
    String rules;
    @Column(columnDefinition = "tinyint")
    boolean active;
    long createdby;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date createdon;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    Date modified;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="users_userid",referencedColumnName = "userid")
    Users user;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="rolesconfig_roleid",referencedColumnName = "roleid")
    Roles roles;
}
