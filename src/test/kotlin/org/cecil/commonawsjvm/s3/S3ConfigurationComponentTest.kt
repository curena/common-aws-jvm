package org.cecil.commonawsjvm.s3

import kotlin.test.Test
import kotlin.test.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class S3ConfigurationComponentTest {
  @Autowired lateinit var s3Operations: S3Operations

  @Test
  fun should_autowire_s3Operations() {
    assertNotNull(s3Operations)
    assertNotNull(s3Operations.s3)
  }
}
