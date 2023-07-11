package io.github.u.ways

import io.github.u.ways.domain.Request
import io.kotest.matchers.shouldBe
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.stream.Stream
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class Challenge4Test {

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

    @ParameterizedTest
    @MethodSource("provideProductRequests")
    fun `should print product codes`(request: Request, expectedOutput: String) {
        challenge4(request)
        outputStreamCaptor.toString().trim() shouldBe expectedOutput
    }

    companion object {
        @JvmStatic
        fun provideProductRequests(): Stream<Arguments> {
            val baseRequest = Request(
                name = "John Doe",
                email = "john.doe@email.com",
                phone = "0123456789",
                address = "123, Some Street, Some City, Some Country",
                internet = false,
                tv = false,
                mobile = false,
                landline = false,
            )

            val productMap = mapOf(
                "internet" to "Internet product code: F_004",
                "tv" to "TV product code: F_003",
                "mobile" to "Mobile product code: F_002",
                "landline" to "Landline product code: F_001",
            )

            val productRequests = productMap.keys.map { product ->
                val modifiedRequest = baseRequest.copy(
                    internet = if (product == "internet") true else baseRequest.internet,
                    tv = if (product == "tv") true else baseRequest.tv,
                    mobile = if (product == "mobile") true else baseRequest.mobile,
                    landline = if (product == "landline") true else baseRequest.landline,
                )
                Arguments.of(modifiedRequest, productMap[product])
            }

            val allProductsRequest = baseRequest.copy(
                internet = true,
                tv = true,
                mobile = true,
                landline = true,
            )
            val allProductsOutput = productMap.values.joinToString("\n")

            return Stream.concat(
                productRequests.stream(),
                Stream.of(Arguments.of(allProductsRequest, allProductsOutput))
            )
        }
    }
}