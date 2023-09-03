package org.cecil.commonawsjvm

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.with

@TestConfiguration(proxyBeanMethods = false)
class TestCommonAwsJvmApplication

fun main(args: Array<String>) {
	fromApplication<CommonAwsJvmApplication>().with(TestCommonAwsJvmApplication::class).run(*args)
}
