package org.cecil.commonawsjvm.generic

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider

@Configuration
class AwsCredentialConfiguration {
  @Value("\${aws.accessKey}") lateinit var accessKeyId: String

  @Value("\${aws.secretAccessKey}") lateinit var secretAccessKey: String

  @Bean
  fun basicCredentials(): AwsCredentials {
    return AwsBasicCredentials.create(accessKeyId, secretAccessKey)
  }

  @Bean
  fun staticCredentialsProvider(basicCredentials: AwsCredentials): StaticCredentialsProvider {
    return StaticCredentialsProvider.create(basicCredentials)
  }
}
