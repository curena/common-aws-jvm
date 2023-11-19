## Now where was I?
<details>
<summary>2023-11-19</summary>

<details>
<summary>Done</summary>

#### Done:
* Added `upload` API to `S3Operations` with signature: 
    ```kotlin
    fun upload(bucketName: String, key: String, file: File): Result<UploadResponse>
    ```
* Tried to use Kotlin's idiomatic `Result<T>` mechanism to implement a sort of "railway oriented programming" API flow.
  * The idea is to encapsulate the flow
  
    ```text
        request
            --> error?
                error-response
            --> success?
                success-response
        response
    ```
* Created rudimentary retry mechanism using Kotlinx Coroutines and extracted to an interface `Retryable`
* Created happy-path integration test in `S3OperationsIntegrationTest`
  * Uploads file `src/test/resources/s3-operations/test-file.txt` and asserts the response is successful
</details>
<details>
<summary>Next</summary>

#### Next
* More robust testing of `upload` mechanism
  * Perhaps testing a failure loop
* Try to incorporate retry approach on next S3 operation (perhaps GetObject)
</details>
</details>