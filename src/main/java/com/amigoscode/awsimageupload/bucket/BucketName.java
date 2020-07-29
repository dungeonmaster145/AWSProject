package com.amigoscode.awsimageupload.bucket;

public enum BucketName {


    PROFILE_IMAGE("mayank-image-bucket");
    private final String bucketname;

    BucketName(String bucketname) {
        this.bucketname = bucketname;
    }

    public String getBucketname() {
        return bucketname;
    }
}
