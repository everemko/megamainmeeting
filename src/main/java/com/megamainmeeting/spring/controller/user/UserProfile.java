package com.megamainmeeting.spring.controller.user;

import lombok.Data;

@Data
public class UserProfile {

    private long userId;
    private String city;
    private String country;
    private String profession;
    private String aboutMyself;
    private int height;
    private int wight;
    private String firstDateIdeal;


}
