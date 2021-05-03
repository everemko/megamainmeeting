package com.megamainmeeting.db.dto;

import com.megamainmeeting.domain.open.UserOpenType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_profile")
public class UserProfileDb {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private UserDb user;

    private String city;
    private String country;
    private String profession;
    private String aboutMyself;
    private String photo;
    private int height;
    private int weight;
    private String firstDateIdeal;

    public String getByType(UserOpenType type){
        switch (type){
            case City: return city;
            case Photo: return photo;
            case Weight: return String.valueOf(weight);
            case Height: return String.valueOf(height);
            case Country: return country;
            case Profession: return profession;
            case AboutMyself: return aboutMyself;
            case FirstDateIdeal: return firstDateIdeal;
        }
        return null;
    }
}
