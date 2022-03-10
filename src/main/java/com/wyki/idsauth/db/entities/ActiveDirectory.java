package com.wyki.idsauth.db.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ad")
@Entity
@Data
public class ActiveDirectory {
    @Id
    Long id;
    @Column(name="staffName")
    String staffname;
    @Column(name="staffNo")
    String staffno;
    String email;
    String telephone;

}
