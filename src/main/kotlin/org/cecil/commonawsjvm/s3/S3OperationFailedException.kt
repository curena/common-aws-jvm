package org.cecil.commonawsjvm.s3

class S3OperationFailedException(message: String?, cause: Throwable?) :
    RuntimeException(message, cause)
