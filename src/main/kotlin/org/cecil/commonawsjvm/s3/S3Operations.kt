package org.cecil.commonawsjvm.s3

import java.io.File
import lombok.extern.slf4j.Slf4j
import org.cecil.commonawsjvm.util.CommonAwsResponse
import org.cecil.commonawsjvm.util.Retryable
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.ListBucketsResponse
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectResponse

/**
 * An abstraction for several common [S3Client] operations. Handles retrying and returns errors in
 * human-readable form.
 */
@Service
@Slf4j
class S3Operations(@Qualifier("defaultClient") val s3: S3Client) : Retryable<CommonAwsResponse> {

  /** Lists all buckets the user has access to */
  fun listBuckets(): ListBucketsResponse {
    return s3.listBuckets()
  }

  /** Uploads a [File] to the designated `bucketName` with the provided object `key`. */
  fun upload(bucketName: String, key: String, file: File): Result<UploadResponse> {
    return withRetry {
      val response = s3.putObject(createPutObjectRequest(bucketName, key, file), file.toPath())
      mapToUploadResponse(response)
    }
  }

  private fun createPutObjectRequest(
      bucketName: String,
      key: String,
      file: File
  ): PutObjectRequest {
    return PutObjectRequest.builder() // .checksumSHA256(file)
        .bucket(bucketName)
        .key(key)
        .build()
  }

  private fun mapToUploadResponse(response: PutObjectResponse): UploadResponse {
    return UploadResponse(
        statusCode = response.sdkHttpResponse().statusCode(),
        eTag = response.eTag(),
        versionId = response.versionId())
  }
}

class UploadResponse(eTag: String?, versionId: String?, statusCode: Int) :
    CommonAwsResponse(statusCode, true)
