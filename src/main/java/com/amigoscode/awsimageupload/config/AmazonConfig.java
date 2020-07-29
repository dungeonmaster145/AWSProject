package com.amigoscode.awsimageupload.config;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//this class will give us the S3 client wherein we will interact with AWS for storage access of our data

//this class is available for us to inject

@Configuration
public class AmazonConfig
{
    @Bean
    public AmazonS3 S3()
    {
        AWSCredentials awsCredentials=new BasicAWSCredentials(
                "AKIAJT4HFY7IUOZBPW6A",
                "AOL0J9jjyTzLfvNULT49/hggLzwWydPWLQJhzpVa");
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion("ap-south-1")
                .build();
    }
}
