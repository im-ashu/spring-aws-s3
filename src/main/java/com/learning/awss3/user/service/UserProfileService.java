package com.learning.awss3.user.service;

import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

import com.learning.awss3.bucket.BucketName;
import com.learning.awss3.filestore.FileStore;
import com.learning.awss3.user.dao.UserProfileDao;
import com.learning.awss3.user.model.UserProfile;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserProfileService {

  private final UserProfileDao userProfileDao;
  private final FileStore fileStore;

  @Autowired
  public UserProfileService(UserProfileDao userProfileDao,
      FileStore fileStore) {
    this.userProfileDao = userProfileDao;
    this.fileStore = fileStore;
  }

  public List<UserProfile> getUserProfiles() {
    return userProfileDao.getUserProfiles();
  }

  public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
    isFileEmpty(file);
    isFileImage(file);
    UserProfile userProfile = getUserProfile(userProfileId);

    Map<String, String> metaData = new HashMap<>();
    extractMetadata(file, metaData);

    String path = String.format("%s/%s", BucketName.PROFILE_NAME.getBucketName(), userProfileId);
    String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

    try {
      fileStore.save(path,
          fileName,
          Optional.of(metaData),
          file.getInputStream()
      );
      userProfile.setUserProfileImageLink(fileName);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to upload");
    }
  }


  public byte[] downloadUserProfileImage(UUID userProfileId) {
    UserProfile userProfile = getUserProfile(userProfileId);
    String path = String.format("%s/%s", BucketName.PROFILE_NAME.getBucketName(), userProfileId);
    return userProfile.getUserProfileImageLink().map(key -> fileStore.fetch(path, key))
        .orElse(new byte[0]);
  }

  private void extractMetadata(MultipartFile file, Map<String, String> metaData) {
    metaData.put("Content-Type", file.getContentType());
    metaData.put("Content-Length", String.valueOf(file.getSize()));
  }

  private UserProfile getUserProfile(UUID userProfileId) {
    return userProfileDao.getUserProfiles().stream()
        .filter(user -> user.getUserProfileId().equals(userProfileId)).findFirst()
        .orElseThrow(
            () -> new IllegalStateException(String.format("User %s not found", userProfileId)));
  }

  private void isFileImage(MultipartFile file) {
    if (!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType())
        .contains(file.getContentType())) {
      throw new IllegalStateException("File is not an image");
    }
  }

  private void isFileEmpty(MultipartFile file) {
    if (file.isEmpty()) {
      throw new IllegalStateException("File is empty");
    }
  }
}
