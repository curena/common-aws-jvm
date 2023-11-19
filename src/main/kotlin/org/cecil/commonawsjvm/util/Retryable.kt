package org.cecil.commonawsjvm.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.cecil.commonawsjvm.s3.S3OperationFailedException
import software.amazon.awssdk.core.exception.SdkException

interface Retryable<T : CommonAwsResponse> {
  fun <T> withRetry(maxRetries: Int = 3, delayMillis: Long = 1000, operation: () -> T): Result<T> =
      runBlocking {
        var lastThrown: Throwable? = null

        for (attempt in 1..maxRetries) {
          try {
            return@runBlocking Result.success(operation())
          } catch (e: SdkException) {
            lastThrown = e
            delay(delayMillis)
          }
        }

        Result.failure(
            lastThrown
                ?: S3OperationFailedException(
                    "The S3 operation failed and all retries were exhausted.", lastThrown))
      }
}
