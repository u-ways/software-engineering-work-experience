package io.github.u.ways

import org.junit.jupiter.api.Test

class Challenge1Test: ChallengeBaseTest() {
    @Test
    fun `should route to broadband department`() {
        challenge1(withRequest(internet = true))
        withExpectedOutput("Routing to Broadband department!")
    }

    @Test
    fun `should route to telecom department`() {
        challenge1(withRequest(mobile = true))
        withExpectedOutput("Routing to Telecom department!")
    }

    @Test
    fun `should route to both departments`() {
        challenge1(withRequest(internet = true, mobile = true))
        withExpectedOutput("Routing to Broadband & Telecom departments!")
    }
}