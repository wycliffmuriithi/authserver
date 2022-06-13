package com.wyki.idsauth.db.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Class name: Userroles
 * Creater: wgicheru
 * Date:3/2/2020
 */
@Table(name="miniagri_usersroles") @Entity @Data
public class Userroles {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    Long userid;
    Long grouproleid;

    @CreationTimestamp
    Date creationdate;
    Long createdby;
}
