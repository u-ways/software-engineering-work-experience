package io.github.u.ways

import io.github.u.ways.domain.Request
import java.util.stream.Stream
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class Challenge4Test : ChallengeBaseTest() {
    @ParameterizedTest(name = "given scenario \"{1}\"")
    @MethodSource("provideSingleProductRequests")
    fun `should print product code`(request: Request, expectedOutput: String) {
        challenge4(request)
        withExpectedOutput(expectedOutput)
    }

    @Test
    fun `should output all product codes when everything is requested`() {
        challenge4(withRequest(internet = true, voip = true, mobile = true, landline = true))
        PRODUCT_TO_CODE_MAP.values.forEach(::withExpectedOutput)
    }

    companion object {
        internal val PRODUCT_TO_CODE_MAP = mapOf(
            "internet" to "Internet product code: F_004",
            "voip" to "VoIP product code: F_003",
            "mobile" to "Mobile product code: F_002",
            "landline" to "Landline product code: F_001",
        )

        @JvmStatic
        fun provideSingleProductRequests(): Stream<Arguments> =
            PRODUCT_TO_CODE_MAP.keys.map { product ->
                val modifiedRequest = withRequest().let { request ->
                    when (product) {
                        "internet" -> request.copy(internet = true)
                        "voip" -> request.copy(voip = true)
                        "mobile" -> request.copy(mobile = true)
                        "landline" -> request.copy(landline = true)
                        else -> request
                    }
                }
                Arguments.of(modifiedRequest, PRODUCT_TO_CODE_MAP[product])
            }.stream()
    }
}