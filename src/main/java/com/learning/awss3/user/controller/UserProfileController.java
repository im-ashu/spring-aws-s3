package com.learning.awss3.user.controller;

import com.learning.awss3.user.model.UserProfile;
import com.learning.awss3.user.service.UserProfileService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api-v1-profiles")
public class UserProfileController {

  private final UserProfileService userProfileService;

  @Autowired
  public UserProfileController(UserProfileService userProfileService) {
    this.userProfileService = userProfileService;
  }

  @GetMapping
  public List<UserProfile> getUserProfiles() {
    return userProfileService.getUserProfiles();
  }

  @PostMapping(
      path = "{userProfileId}/image/upload",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public void uploadUserProfileImage(@PathVariable("userProfileId") UUID userProfileId,
      @RequestParam("file")
          MultipartFile file) {
    userProfileService.uploadUserProfileImage(userProfileId, file);
  }

  @GetMapping(
      path = "{userProfileId}/image/download"
  )
  public byte[] downloadUserProfileImage(@PathVariable("userProfileId") UUID userProfileId) {
    return userProfileService.downloadUserProfileImage(userProfileId);
  }

}
