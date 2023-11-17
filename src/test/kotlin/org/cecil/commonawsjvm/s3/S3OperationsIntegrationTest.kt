package org.cecil.commonawsjvm.s3

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import org.junit.Rule
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.CreateBucketRequest

@Testcontainers
@SpringBootTest
class S3OperationsIntegrationTest {
  private final var localstackImage: DockerImageName =
      DockerImageName.parse("localstack/localstack:latest")

  @JvmField
  @Rule
  final var localstack: LocalStackContainer =
      LocalStackContainer(localstackImage).withServices(LocalStackContainer.Service.S3)
  lateinit var s3Operations: S3Operations
  lateinit var s3: S3Client

  @BeforeTest
  fun setupClass() {
    localstack.start()
    s3 =
        S3Client.builder()
            .endpointOverride(localstack.endpoint)
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(localstack.accessKey, localstack.secretKey)))
            .region(Region.of(localstack.region))
            .build()
    s3Operations = S3Operations(s3)
  }

  @Test
  fun should_list_buckets() {

    s3.createBucket(CreateBucketRequest.builder().bucket("foobar").build())
    assertNotNull(
        s3Operations.listBuckets().buckets().first { bucket -> bucket.name().equals("foobar") })
  }
}
