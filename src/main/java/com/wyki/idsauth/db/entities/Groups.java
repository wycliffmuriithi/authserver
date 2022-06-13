package com.wyki.idsauth.db.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Table(name="miniagri_groups")
@Entity
@Data
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String groupname;
    @CreationTimestamp
    Date creationdate;
}
