package com.megamainmeeting.db.dto;

import com.megamainmeeting.entity.chat.ChatMessage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;

@Getter
@Setter
@Table(name = "chat_messages")
@Entity
public class ChatMessageDb {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String message;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "room_id")
    private RoomDb room;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDb user;

    @Column(name = "image_url")
    private String imageUrl;

    private LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
    private boolean isRead = false;

    @PrePersist
    public void prePersist(){
        room.addMessage(this);
    }

    @PreUpdate
    public void preUpdate(){
        room.addMessage(this);
    }
}
