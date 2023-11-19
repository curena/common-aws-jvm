package org.cecil.commonawsjvm.s3

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.CreateBucketRequest

private const val BUCKET_NAME = "test-bucket"

@Testcontainers
@SpringBootTest
class S3OperationsIntegrationTest {
  companion object {
    private var localstackImage: DockerImageName =
        DockerImageName.parse("localstack/localstack:latest")

    @JvmField
    @Container
    var localstack: LocalStackContainer =
        LocalStackContainer(localstackImage).withServices(LocalStackContainer.Service.S3)
  }

  lateinit var s3Operations: S3Operations
  lateinit var s3: S3Client

  @BeforeTest
  fun setupClass() {
    s3 =
        S3Client.builder()
            .endpointOverride(localstack.endpoint)
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(localstack.accessKey, localstack.secretKey)))
            .region(Region.of(localstack.region))
            .build()
    s3Operations = S3Operations(s3)
    s3.createBucket(CreateBucketRequest.builder().bucket(BUCKET_NAME).build())
  }

  @Test
  fun should_list_buckets() {
    assertNotNull(
        s3Operations.listBuckets().buckets().first { bucket -> bucket.name().equals(BUCKET_NAME) })
  }

  @Test
  fun upload_with_file_should_succeed() {
    val bucketName = "test-bucket"
    val key = "test-file.txt"
    val fileResource = ClassPathResource("s3-operations/test-file.txt")

    val result = s3Operations.upload(bucketName, key, fileResource.file)

    assertThat(result.isFailure).isFalse()
    assertThat(result.getOrNull() as UploadResponse).isNotNull
  }
}
