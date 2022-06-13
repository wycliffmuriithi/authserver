package com.wyki.idsauth.db.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Table(name="miniagri_grouproles")
@Entity
@Data
public class GroupRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    Long roleid;

    @CreationTimestamp
    Date creationdate;
}
