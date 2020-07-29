package com.amigoscode.awsimageupload.profile;

import com.amigoscode.awsimageupload.bucket.BucketName;
import com.amigoscode.awsimageupload.filestore.FileStore;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

//this is where our business logic really happens
@Service
public class UserProfileService {
private final UserProfileDataAccessService userProfileDataAccessService;
private final FileStore fileStore;
@Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService,
                              FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
    this.fileStore = fileStore;
}
    List<UserProfile> getUserProfiles()
    {
        return userProfileDataAccessService.getUserProfiles();
    }

    void uploadUserProfileImage(UUID userProfileId, MultipartFile file) throws IOException {
        //first we check that whether the file is empty or not
        if (file.isEmpty()) {

            throw new IllegalStateException("Cannot upload empty file [" + file.getSize() + "]");
        }
        //now we check if the file is an image file or not
        if (!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(),
                ContentType.IMAGE_PNG.getMimeType(),
                ContentType.IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("Please upload the file of correct format");
        }
        // now we check whether the user exists in our database
        UserProfile user = getUserProfileOrThrow(userProfileId);
        //we are making use of datastreams here as we are not using any database


        //step 4 we will grab some metadata from the file if any
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        //now what we do is that we simply insert the file to our S3 bucket and update database with the image link
        //inside our bucket we will have folder for each file that we are going to save there
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketname(), user.getUserProfileId());
        String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        //here we see that we have actually saved the image but we have not uploaded it yet
        try {
            fileStore.save(path, fileName, Optional.of(metadata), file.getInputStream());
            //after we have saved the file we will give the user profile image link here
            user.setUserProfileImageLink(fileName);
        }
        catch(IOException e)
        {
            throw new IllegalStateException(e);
        }
    }

    private UserProfile getUserProfileOrThrow(UUID userProfileId) {
        return userProfileDataAccessService
                    .getUserProfiles()
                    .stream()
                    .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userProfileId)));
    }

    public byte[] downloadUserProfileImage(UUID userProfileId) {
   UserProfile user =getUserProfileOrThrow(userProfileId);
        String path = String.format("%s/%s",
                BucketName.PROFILE_IMAGE.getBucketname(),
                user.getUserProfileId());
      return  user.getUserProfileImageLink()
                .map(key -> fileStore.download(path,key))
                .orElse(new byte[0]);
        //now we create a download button that will take the full path


    }




}
