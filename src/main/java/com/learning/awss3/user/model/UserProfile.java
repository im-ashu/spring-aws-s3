package com.learning.awss3.user.model;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserProfile {

  private final UUID userProfileId;
  private final String username;
  private String userProfileImageLink;

  public UserProfile(UUID userProfileId, String username, String userProfileImageLink) {
    this.userProfileId = userProfileId;
    this.username = username;
    this.userProfileImageLink = userProfileImageLink;
  }

  public UUID getUserProfileId() {
    return userProfileId;
  }

  public String getUsername() {
    return username;
  }

  public Optional<String> getUserProfileImageLink() {
    return Optional.ofNullable(userProfileImageLink);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserProfile)) {
      return false;
    }
    UserProfile that = (UserProfile) o;
    return userProfileId.equals(that.userProfileId) &&
        username.equals(that.username) &&
        Objects.equals(userProfileImageLink, that.userProfileImageLink);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userProfileId, username, userProfileImageLink);
  }

  public void setUserProfileImageLink(String userProfileImageLink) {
    this.userProfileImageLink = userProfileImageLink;
  }


}
