package com.learning.awss3.bucket;

public enum BucketName {
  PROFILE_NAME("thebucketofashu");
  private final String bucketName;

  BucketName(String bucketName) {
    this.bucketName = bucketName;
  }

  public String getBucketName() {
    return bucketName;
  }
}




