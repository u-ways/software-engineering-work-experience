package io.github.u.ways

import io.github.u.ways.domain.Request
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import java.util.stream.Stream
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class Challenge3Test : ChallengeBaseTest() {

    @Test
    fun `should validate request when all mandatory fields are present`() {
        challenge3(
            withRequest(
                name = "John Doe", email = "john.doe@email.com",
                phone = "0123456789", address = "123, Some Street, Some City, Some Country",
                internet = true, tv = false, mobile = false, landline = false
            )
        ).shouldBeTrue()
    }

    @ParameterizedTest
    @MethodSource("provideInvalidRequests")
    fun `should invalidate request when a mandatory field is missing`(request: Request) {
        challenge3(request).shouldBeFalse()
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