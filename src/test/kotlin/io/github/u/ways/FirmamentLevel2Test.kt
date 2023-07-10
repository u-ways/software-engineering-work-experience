package io.github.u.ways

import io.github.u.ways.domain.Request
import io.kotest.matchers.shouldBe
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.booleans.shouldBeFalse
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.stream.Stream
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class FirmamentLevel2Test {

    private val standardOut = System.out
    private val outputStreamCaptor = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
    }

    @Test
    fun `should invalidate request when no products are requested`() {
        val request = Request(
            name = "John Doe",
            email = "john.doe@email.com",
            phone = "0123456789",
            address = "123, Some Street, Some City, Some Country",
            internet = false,
            tv = false,
            mobile = false,
            landline = false,
        )

        val result = FirmamentLevel2.solution(request)

        result.shouldBeFalse()
        outputStreamCaptor.toString().trim() shouldBe "No products requested!"
    }

    @ParameterizedTest
    @MethodSource("provideProductCombinations")
    fun `should validate requeest when at least one product requested`(internet: Boolean, tv: Boolean, mobile: Boolean, landline: Boolean) {
        val request = Request(
            name = "John Doe",
            email = "john.doe@email.com",
            phone = "0123456789",
            address = "123, Some Street, Some City, Some Country",
            internet = internet,
            tv = tv,
            mobile = mobile,
            landline = landline,
        )

        val result = FirmamentLevel2.solution(request)

        result.shouldBeTrue()
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