package io.github.u.ways

import io.github.u.ways.domain.Request
import java.util.stream.Stream
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class Challenge4Test : ChallengeBaseTest() {
    @ParameterizedTest
    @MethodSource("provideSingleProductRequests")
    fun `should print product codes`(request: Request, expectedOutput: String) {
        challenge4(request)
        withExpectedOutput(expectedOutput)
    }

    @ParameterizedTest
    @MethodSource("provideAllProductsRequest")
    fun `should print all product codes when everything is requested`(request: Request, expectedOutput: String) {
        challenge4(request)
        withExpectedOutput(expectedOutput)
    }

    companion object {
        @JvmStatic
        fun provideSingleProductRequests(): Stream<Arguments> {
            val productMap = mapOf(
                "internet" to "Internet product code: F_004",
                "tv" to "TV product code: F_003",
                "mobile" to "Mobile product code: F_002",
                "landline" to "Landline product code: F_001",
            )
            return productMap.keys.map { product ->
                val modifiedRequest = withRequest().let { request ->
                    when (product) {
                        "internet" -> request.copy(internet = true)
                        "tv" -> request.copy(tv = true)
                        "mobile" -> request.copy(mobile = true)
                        "landline" -> request.copy(landline = true)
                        else -> request
                    }
                }
                Arguments.of(modifiedRequest, productMap[product])
            }.stream()
        }

        @JvmStatic
        fun provideAllProductsRequest(): Stream<Arguments> {
            val request = withRequest(internet = true, tv = true, mobile = true, landline = true)
            val expectedOutput = """
                Internet product code: F_004
                TV product code: F_003
                Mobile product code: F_002
                Landline product code: F_001
            """.trimIndent()
            return Stream.of(Arguments.of(request, expectedOutput))
        }
    }
}