package org.cecil.commonawsjvm.s3

import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
@ComponentScan(basePackages = ["org.cecil.commonawsjvm"])
@Slf4j
class S3Configuration {
  @Value("\${aws.s3.region}") lateinit var region: String

  /**
   * Creates an Amazon S3 Client using the default credentials provider chain. See:
   * [Default credentials provider chain](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/credentials-chain.html)
   */
  @Bean
  fun defaultClient(): S3Client {
    return S3Client.builder()
        .credentialsProvider(DefaultCredentialsProvider.create())
        .region(Region.of(region))
        .build()
  }

  /**
   * Creates an Amazon S3 client based no provided credentials (awsAccessKeyId, awsSecretAccessKey)
   */
  @Bean
  fun staticCredentialsClient(staticCredentialsProvider: StaticCredentialsProvider): S3Client {
    return S3Client.builder()
        .credentialsProvider(staticCredentialsProvider)
        .region(Region.of(region))
        .build()
  }
}
