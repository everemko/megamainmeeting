package com.megamainmeeting.database.dto;

import com.megamainmeeting.domain.open.UserOpenType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Setter
@Getter
@Table(name = "user_open_up")
@Entity
public class UserOpenUpDb {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UserDb user;

    @ManyToOne()
    @JoinColumn(name = "open_request_id")
    private OpenRequestDb openRequest;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_open_type")
    private UserOpenType userOpenType;

    private LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);

    @PreRemove
    private void preRemove(){
        user.remove(this);
    }

    @PrePersist
    private void prePersist(){
        user.addUserOpens(this);
        openRequest.addUserOpen(this);
    }
}
