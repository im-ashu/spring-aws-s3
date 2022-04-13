package com.learning.awss3.user.dao;

import com.learning.awss3.user.model.UserProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class UserProfileDao {

  private static final List<UserProfile> userProfiles = new ArrayList<>();

  static {
    userProfiles.add(new UserProfile(UUID.fromString("2c0e13e0-c408-43f2-8830-88cfbf9ff916"), "Sachin172", null));
    userProfiles.add(new UserProfile(UUID.fromString("db612cb9-c251-42df-8056-f8a647edcead"), "Sachin155", null));
  }

  public List<UserProfile> getUserProfiles(){
    return userProfiles;
  }

}
