package io.github.u.ways

import io.github.u.ways.domain.Request
import io.kotest.matchers.shouldBe
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.booleans.shouldBeFalse
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class Challenge3Test {

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
    fun `should validate request when all mandatory fields are present`() {
        val request = Request(
            name = "John Doe",
            email = "john.doe@email.com",
            phone = "0123456789",
            address = "123, Some Street, Some City, Some Country",
            internet = true,
            tv = false,
            mobile = false,
            landline = false,
        )

        val result = challenge3(request)
        result.shouldBeTrue()
    }

    @ParameterizedTest
    @MethodSource("provideInvalidRequests")
    fun `should invalidate request when a mandatory field is missing`(request: Request) {
        val result = challenge3(request)
        result.shouldBeFalse()
        outputStreamCaptor.toString().trim() shouldBe "A mandatory field is missing!"
    }

    companion object {
        @JvmStatic
        fun provideInvalidRequests(): Stream<Arguments> {
            val baseRequest = Request(
                name = "John Doe",
                email = "john.doe@email.com",
                phone = "0123456789",
                address = "123, Some Street, Some City, Some Country",
                internet = true,
                tv = false,
                mobile = false,
                landline = false,
            )

            val fields = listOf("name", "email", "phone", "address")

            return fields.map { field ->
                val modifiedRequest = baseRequest.copy(
                    name = if (field == "name") "" else baseRequest.name,
                    email = if (field == "email") "" else baseRequest.email,
                    phone = if (field == "phone") "" else baseRequest.phone,
                    address = if (field == "address") "" else baseRequest.address,
                )
                Arguments.of(modifiedRequest)
            }.stream()
        }
    }
}