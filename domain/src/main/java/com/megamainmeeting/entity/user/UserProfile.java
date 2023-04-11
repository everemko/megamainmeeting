package com.megamainmeeting.entity.user;

import lombok.Data;

@Data
public class UserProfile {

    private long userId;
    private String city;
    private String country;
    private String profession;
    private String aboutMyself;
    private int height;
    private int weight;
    private String firstDateIdeal;
    private String avatar;


}
