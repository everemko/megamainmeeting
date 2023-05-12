package com.megamainmeeting.database.dto;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "admin_users")
public class AdminUserDb {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;
    private boolean enabled;
    private String authority;
}
