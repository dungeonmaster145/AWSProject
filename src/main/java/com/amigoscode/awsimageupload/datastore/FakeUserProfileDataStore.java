package com.amigoscode.awsimageupload.datastore;
//we are creating fake database users to use

import com.amigoscode.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {


    private static  final List<UserProfile> USER_PROFILES=new ArrayList<>();
    static
    {
        USER_PROFILES.add(new UserProfile(UUID.fromString("ad2d0f72-739d-4570-b3c9-7e83068fd990"),"Mayank Joshi",null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("4b5c7d2c-38d9-4c67-bbde-19d85b02c9c1"),"Akshit Joshi",null));

    }
    public List<UserProfile> getUserProfiles()
    {
        return USER_PROFILES;
    }
}
