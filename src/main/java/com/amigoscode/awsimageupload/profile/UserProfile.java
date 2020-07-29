package com.amigoscode.awsimageupload.profile;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserProfile {
    private final UUID userProfileId;
    private final String username;
    private String UserProfileImageLink; //this is the s3 link

    public UserProfile(UUID userProfileId, String username, String userProfileImageLink) {
        this.userProfileId = userProfileId;
        this.username = username;
        UserProfileImageLink = userProfileImageLink;//this is the S3 image link
    }

    public UUID getUserProfileId() {
        return userProfileId;
    }

    /*public void setUserProfileId(UUID userProfileId) {
        this.userProfileId = userProfileId;
    }
    */


    public String getUsername() {
        return username;
    }
/*
    public void setUsername(String username) {
        this.username = username;
    }
*/
    public Optional<String> getUserProfileImageLink() {
        return Optional.ofNullable(UserProfileImageLink);
        //this will check for any null or absent value

    }

    public void setUserProfileImageLink(String userProfileImageLink) {
        UserProfileImageLink = userProfileImageLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(userProfileId,that.userProfileId) &&
                Objects.equals(username,that.username) &&
                Objects.equals(UserProfileImageLink,that.UserProfileImageLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userProfileId, username, UserProfileImageLink);
    }
}
