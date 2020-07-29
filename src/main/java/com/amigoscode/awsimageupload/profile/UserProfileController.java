package com.amigoscode.awsimageupload.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/user-profile")
@CrossOrigin("*")
//the above statement means that the request to access our backend database is accepted from any server on any host
public class UserProfileController
{
    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public List<UserProfile> getUserProfiles()
    {
        return userProfileService.getUserProfiles();
    }
    //the function below is used to add a path the our image that is initially set as null
    //we provide the path and also the pass the request parameters on the basis of which we will gather our image URL.
    @PostMapping(
            path="{userProfileId}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces =MediaType.APPLICATION_JSON_VALUE
     )
    public void uploadUserProfileImage(@PathVariable("userProfileId")UUID userProfileId,
                                       @RequestParam("file")MultipartFile file) throws IOException {
         userProfileService.uploadUserProfileImage(userProfileId,file);
    }
    @GetMapping("{userProfileId}/image/download")
   public byte[] downloadUserProfileImage(@PathVariable("userProfileId")UUID userProfileId)
   {
         return userProfileService.downloadUserProfileImage(userProfileId);
   }
}