package io.github.u.ways

import java.util.stream.Stream
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class Challenge2Test : ChallengeBaseTest() {
    @Test
    fun `should invalidate request when no products are requested`() {
        challenge2(withRequest(internet = false, voip = false, mobile = false, landline = false))
        withExpectedOutput("No products requested!")
    }

    @ParameterizedTest(name = "given scenario: internet={0}, voip={1}, mobile={2}, landline={3}")
    @MethodSource("provideProductCombinations")
    fun `should consider the request valid when at least one or more product is requested`(
        internet: Boolean,
        voip: Boolean,
        mobile: Boolean,
        landline: Boolean,
    ) {
        challenge2(
            withRequest(
                internet = internet,
                voip = voip,
                mobile = mobile,
                landline = landline,
            )
        )
        shouldNotOutput("No products requested!")
    }

    companion object {
        @JvmStatic
        fun provideProductCombinations(): Stream<Arguments> {
            return (1..15).map { i ->
                val binaryString = i.toString(radix = 2).padStart(length = 4, padChar = '0')
                Arguments.of(*Array(binaryString.length) { binaryString[it] == '1' })
            }.stream()
        }
    }
}