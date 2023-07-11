package io.github.u.ways

import io.github.u.ways.domain.Request
import io.kotest.matchers.string.shouldContain
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

abstract class ChallengeBaseTest {
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

    internal fun withExpectedOutput(expected: String) =
        outputStreamCaptor.toString().trim() shouldContain expected

    companion object {
        internal fun withRequest(
            name: String = "John Doe",
            email: String = "john.doe@email.com",
            phone: String = "0123456789",
            address: String = "123, Some Street, Some City, Some Country",
            internet: Boolean = false,
            tv: Boolean = false,
            mobile: Boolean = false,
            landline: Boolean = false,
        ) = Request(
            name = name,
            email = email,
            phone = phone,
            address = address,
            internet = internet,
            tv = tv,
            mobile = mobile,
            landline = landline,
        )
    }
}