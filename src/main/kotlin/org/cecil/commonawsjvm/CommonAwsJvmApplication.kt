package org.cecil.commonawsjvm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication class CommonAwsJvmApplication

fun main(args: Array<String>) {
  runApplication<CommonAwsJvmApplication>(*args)
}
