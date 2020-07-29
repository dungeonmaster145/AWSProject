package com.amigoscode.awsimageupload.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class FileStore
{
    private final AmazonS3 s3;

//the class that actually stores our data in s3 bucket
    @Autowired
    public FileStore(AmazonS3 s3) {
        this.s3 = s3;
    }

    //below we create the save method
    public void save(String path, String filename, Optional<Map<String,String>>optionalMetadata, InputStream inputStream)
    {
        ObjectMetadata metadata=new ObjectMetadata();
        optionalMetadata.ifPresent(map ->{
            if(!map.isEmpty())
            {

                map.forEach(metadata::addUserMetadata); //here we are adding key value for each metadata
            }
        });
        try
        {

            s3.putObject(path,filename,inputStream,metadata);
        }
        catch(AmazonServiceException e)
        {
            throw new IllegalStateException("Failed to store file in s3",e);
        }

    }

    public byte[] download(String path, String key) {
    //return new byte[0];
    try
    {
        //the getObject method takes two things the path and the key
       S3Object s3Object= s3.getObject(path,key);
       //now we will get the content of our file

      return IOUtils.toByteArray(s3Object.getObjectContent());
    }
    catch(AmazonServiceException | IOException e)
    {
       throw new IllegalStateException("Failed to download the file",e);
    }
    }
}
