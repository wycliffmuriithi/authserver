package com.wyki.idsauth.db.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Class name: Users
 * Creater: wgicheru
 * Date:6/12/2019
 */
@Table(name="users")
@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class Users {
    private String firstname;
    @Column(columnDefinition = "longtext")
    private String password;
    private String email;
    private String phonenumber;
    private  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long  userid;
    private  String othernames;
    private Date dateofbirth;
    private String gender;
    @Column(columnDefinition = "longtext")
    private String nationality;
    @Column(columnDefinition = "longtext")
    private String nationalidnumber;
    @Column(columnDefinition = "tinyint")
    private boolean active;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date registrationdate;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private StationUsers stationuser;
}
