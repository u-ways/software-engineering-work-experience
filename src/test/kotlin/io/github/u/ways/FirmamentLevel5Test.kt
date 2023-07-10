package io.github.u.ways

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.stream.IntStream
import java.util.stream.Stream
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class FirmamentLevel5Test {

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
    @MethodSource("provideRoutingRequests")
    fun `should route to appropriate department`(request: List<String>, expectedOutput: String) {
        val result = FirmamentLevel5.solution(request)
        result.shouldBeTrue()
        outputStreamCaptor.toString().trim().contains(expectedOutput).shouldBeTrue()
    }

    @Test
    fun `should reject when no product is requested`() {
        val request = listOf(
            "John Doe",
            "john.doe@email.com",
            "0123456789",
            "123, Some Street, Some City, Some Country",
            "false",
            "false",
            "false",
            "false"
        )

        val result = FirmamentLevel5.solution(request)
        result.shouldBeFalse()
        outputStreamCaptor.toString().trim() shouldBe "No products requested!"
    }

    @ParameterizedTest
    @MethodSource("provideInvalidRequests")
    fun `should reject when mandatory field is missing`(request: List<String>) {
        val result = FirmamentLevel5.solution(request)
        result.shouldBeFalse()
        outputStreamCaptor.toString().trim() shouldBe "A mandatory field is missing!"
    }

    @ParameterizedTest
    @MethodSource("provideProductRequests")
    fun `should print product codes`(request: List<String>, expectedOutput: String) {
        val result = FirmamentLevel5.solution(request)
        result.shouldBeTrue()
        outputStreamCaptor.toString().trim() shouldBe expectedOutput
    }

    companion object {

        @JvmStatic
        fun provideRoutingRequests(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    listOf(
                        "John Doe",
                        "john.doe@email.com",
                        "0123456789",
                        "123, Some Street, Some City, Some Country",
                        "true",  // Internet
                        "false",
                        "false",
                        "false"
                    ),
                    "Routing to Broadband department!\nInternet product code: F_004"
                ),
                Arguments.of(
                    listOf(
                        "John Doe",
                        "john.doe@email.com",
                        "0123456789",
                        "123, Some Street, Some City, Some Country",
                        "false",
                        "true",  // TV
                        "false",
                        "false"
                    ),
                    "Routing to Broadband department!\nTV product code: F_003"
                ),
                Arguments.of(
                    listOf(
                        "John Doe",
                        "john.doe@email.com",
                        "0123456789",
                        "123, Some Street, Some City, Some Country",
                        "false",
                        "false",
                        "true",  // Mobile
                        "false"
                    ),
                    "Routing to Telecom department!\nMobile product code: F_002"
                ),
                Arguments.of(
                    listOf(
                        "John Doe",
                        "john.doe@email.com",
                        "0123456789",
                        "123, Some Street, Some City, Some Country",
                        "false",
                        "false",
                        "false",
                        "true"  // Landline
                    ),
                    "Routing to Telecom department!\nLandline product code: F_001"
                ),
                Arguments.of(
                    listOf(
                        "John Doe",
                        "john.doe@email.com",
                        "0123456789",
                        "123, Some Street, Some City, Some Country",
                        "true",  // Internet
                        "true",  // TV
                        "true",  // Mobile
                        "true"  // Landline
                    ),
                    "Routing to Broadband & Telecom departments!\nInternet product code: F_004\nTV product code: F_003\nMobile product code: F_002\nLandline product code: F_001"
                )
            )
        }

        @JvmStatic
        fun provideInvalidRequests(): Stream<Arguments> {
            val baseRequest = listOf(
                "John Doe",
                "john.doe@email.com",
                "0123456789",
                "123, Some Street, Some City, Some Country",
                "true",
                "false",
                "false",
                "false",
            )

            return IntStream.range(0, 4)  // Change fields from 0 to 3 (NAME to ADDRESS)
                .mapToObj { i ->
                    val modifiedRequest = baseRequest.toMutableList().apply {
                        this[i] = ""
                    }
                    Arguments.of(modifiedRequest)
                }
        }

        @JvmStatic
        fun provideProductRequests(): Stream<Arguments> {
            val baseRequest = listOf(
                "John Doe",
                "john.doe@email.com",
                "0123456789",
                "123, Some Street, Some City, Some Country",
                "false",
                "false",
                "false",
                "false",
            )

            val productMap = mapOf(
                4 to "Routing to Broadband department!\nInternet product code: F_004",
                5 to "Routing to Broadband department!\nTV product code: F_003",
                6 to "Routing to Telecom department!\nMobile product code: F_002",
                7 to "Routing to Telecom department!\nLandline product code: F_001"
            )
            val productRequests = productMap.keys.map { product ->
                val modifiedRequest = baseRequest.toMutableList().apply {
                    this[product] = "true"
                }
                Arguments.of(modifiedRequest, productMap[product])
            }

            val allProductMap = mapOf(
                4 to "Routing to Broadband & Telecom departments!\nInternet product code: F_004",
                5 to "TV product code: F_003",
                6 to "Mobile product code: F_002",
                7 to "Landline product code: F_001"
            )
            val allProductsRequest = baseRequest.toMutableList().apply {
                this[4] = "true"
                this[5] = "true"
                this[6] = "true"
                this[7] = "true"
            }
            val allProductsOutput = allProductMap.values.joinToString("\n")

            return Stream.concat(
                productRequests.stream(),
                Stream.of(Arguments.of(allProductsRequest, allProductsOutput))
            )
        }
    }
}
