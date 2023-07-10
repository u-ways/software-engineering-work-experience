package io.github.u.ways

import io.github.u.ways.domain.Request
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FirmamentLevel1Test {
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
    fun `should route to broadband department`() {
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

        FirmamentLevel1.solution(request)

        outputStreamCaptor.toString().trim() shouldContain "Routing to Broadband department!"
    }

    @Test
    fun `should route to telecom department`() {
        val request = Request(
            name = "John Doe",
            email = "john.doe@email.com",
            phone = "0123456789",
            address = "123, Some Street, Some City, Some Country",
            internet = false,
            tv = false,
            mobile = true,
            landline = false,
        )

        FirmamentLevel1.solution(request)

        outputStreamCaptor.toString().trim() shouldBe "Routing to Telecom department!"
    }

    @Test
    fun `should route to both departments`() {
        val request = Request(
            name = "John Doe",
            email = "john.doe@email.com",
            phone = "0123456789",
            address = "123, Some Street, Some City, Some Country",
            internet = true,
            tv = true,
            mobile = true,
            landline = false,
        )

        FirmamentLevel1.solution(request)

        outputStreamCaptor.toString().trim() shouldBe "Routing to Broadband & Telecom departments!"
    }
}