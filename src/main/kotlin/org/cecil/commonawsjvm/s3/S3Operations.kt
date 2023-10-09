package org.cecil.commonawsjvm.s3

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.ListBucketsResponse

@Service
class S3Operations(@Qualifier("defaultClient") val s3: S3Client) {
    fun listBuckets(): ListBucketsResponse {
        return s3.listBuckets()
    }
}