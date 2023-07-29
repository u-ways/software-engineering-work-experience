package io.github.u.ways

import io.github.u.ways.domain.Request
import java.util.stream.Stream
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class Challenge3Test : ChallengeBaseTest() {

    @Test
    fun `should not output an error message when all mandatory fields are present`() {
        challenge3(
            withRequest(
                name = "John Doe", email = "john.doe@email.com",
                phone = "0123456789", address = "123, Some Street, Some City, Some Country",
                internet = true, voip = false, mobile = false, landline = false
            )
        )
        shouldNotOutput("A mandatory field is missing!")
    }

    @ParameterizedTest
    @MethodSource("provideInvalidRequests")
    fun `should invalidate a request when a mandatory field is empty`(request: Request) {
        challenge3(request)
        withExpectedOutput("A mandatory field is missing!")
    }

    companion object {
        @JvmStatic
        fun provideInvalidRequests(): Stream<Arguments> =
            listOf("name", "email", "phone", "address", "everything")
                .map { field ->
                    Arguments.of(
                        withRequest().let { request ->
                            when (field) {
                                "name" -> request.copy(name = "")
                                "email" -> request.copy(email = "")
                                "phone" -> request.copy(phone = "")
                                "address" -> request.copy(address = "")
                                else -> request.copy("", "", "", "")
                            }
                        }
                    )
                }.stream()
    }
}