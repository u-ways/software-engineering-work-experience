package io.github.u.ways

import java.util.stream.IntStream
import java.util.stream.Stream
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class Challenge5Test : ChallengeBaseTest() {
    @ParameterizedTest
    @MethodSource("provideRoutingRequests")
    fun `should route to appropriate department`(request: List<String>, expectedOutput: String) {
        challenge5(request)
        withExpectedOutput(expectedOutput)
    }

    @Test
    fun `should reject when no product is requested`() {
        challenge5(withListRequest(internet = false, tv = false, mobile = false, landline = false))
        withExpectedOutput("No products requested!")
    }

    @ParameterizedTest
    @MethodSource("provideInvalidRequests")
    fun `should reject when mandatory field is missing`(request: List<String>) {
        challenge5(request)
        withExpectedOutput("A mandatory field is missing!")
    }

    @ParameterizedTest
    @MethodSource("provideSingleProductRequests")
    fun `should print product codes`(request: List<String>, expectedOutput: String) {
        challenge5(request)
        withExpectedOutput(expectedOutput)
    }

    @ParameterizedTest
    @MethodSource("provideAllProductsRequest")
    fun `should print all product codes when everything is requested`(request: List<String>, expectedOutput: String) {
        challenge5(request)
        withExpectedOutput(expectedOutput)
    }

    companion object {
        private fun withListRequest(
            name: String = "John Doe",
            email: String = "john.doe@email.com",
            phone: String = "0123456789",
            address: String = "123, Some Street, Some City, Some Country",
            internet: Boolean = false,
            tv: Boolean = false,
            mobile: Boolean = false,
            landline: Boolean = false,
        ): List<String> = listOf(
            name,
            email,
            phone,
            address,
            internet.toString(),
            tv.toString(),
            mobile.toString(),
            landline.toString()
        )

        @JvmStatic
        fun provideRoutingRequests(): Stream<Arguments> = Stream.of(
            Arguments.of(
                withListRequest(internet = true),
                "Routing to Broadband department!\nInternet product code: F_004"
            ),
            Arguments.of(
                withListRequest(tv = true),
                "Routing to Broadband department!\nTV product code: F_003"
            ),
            Arguments.of(
                withListRequest(mobile = true),
                "Routing to Telecom department!\nMobile product code: F_002"
            ),
            Arguments.of(
                withListRequest(landline = true),
                "Routing to Telecom department!\nLandline product code: F_001"
            ),
            Arguments.of(
                withListRequest(internet = true, tv = true, mobile = true, landline = true),
                "Routing to Broadband & Telecom departments!\nInternet product code: F_004\nTV product code: F_003\nMobile product code: F_002\nLandline product code: F_001"
            )
        )

        @JvmStatic
        fun provideInvalidRequests(): Stream<Arguments> = IntStream
            .range(0, 4)  // Change fields from 0 to 3 (NAME to ADDRESS)
            .mapToObj { i -> Arguments.of(withListRequest().toMutableList().apply { this[i] = "" }) }

        @JvmStatic
        fun provideSingleProductRequests(): Stream<Arguments> {
            val internetRequest = withListRequest(internet = true)
            val tvRequest = withListRequest(tv = true)
            val mobileRequest = withListRequest(mobile = true)
            val landlineRequest = withListRequest(landline = true)

            return Stream.of(
                Arguments.of(internetRequest, "Internet product code: F_004"),
                Arguments.of(tvRequest, "TV product code: F_003"),
                Arguments.of(mobileRequest, "Mobile product code: F_002"),
                Arguments.of(landlineRequest, "Landline product code: F_001")
            )
        }

        @JvmStatic
        fun provideAllProductsRequest(): Stream<Arguments> {
            val allProductsRequest = withListRequest(
                internet = true,
                tv = true,
                mobile = true,
                landline = true
            )

            val allProductsOutput = "Routing to Broadband & Telecom departments!\n" +
                "Internet product code: F_004\n" +
                "TV product code: F_003\n" +
                "Mobile product code: F_002\n" +
                "Landline product code: F_001"

            return Stream.of(Arguments.of(allProductsRequest, allProductsOutput))
        }
    }
}
