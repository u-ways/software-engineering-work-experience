package io.github.u.ways

import java.util.stream.Stream
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class Challenge1Test : ChallengeBaseTest() {
    @Test
    fun `should route to broadband department when internet product is selected`() {
        challenge1(withRequest(internet = true))
        withExpectedOutput(routingTo(Department.BROADBAND))
    }

    @Test
    fun `should route to broadband department when tv product is selected`() {
        challenge1(withRequest(tv = true))
        withExpectedOutput(routingTo(Department.BROADBAND))
    }

    @Test
    fun `should route to telecom department when mobile product is selected`() {
        challenge1(withRequest(mobile = true))
        withExpectedOutput(routingTo(Department.TELECOM))
    }

    @Test
    fun `should route to telecom department when landline product is selected`() {
        challenge1(withRequest(landline = true))
        withExpectedOutput(routingTo(Department.TELECOM))
    }

    @ParameterizedTest
    @MethodSource("scenariosThatRouteToBothDepartments")
    fun `should route to both departments when a product from each department is selected`(
        internet: Boolean, tv: Boolean = false, mobile: Boolean, landline: Boolean = false,
    ) {
        challenge1(withRequest(internet = internet, tv = tv, mobile = mobile, landline = landline))
        withExpectedOutput(routingTo(Department.BOTH))
    }

    companion object {
        @JvmStatic
        fun scenariosThatRouteToBothDepartments(): Stream<Arguments> =
            listOf(true, false)
                .cartesianPower(4)
                .filter(::meetsRoutingToBothDepartmentsCriteria)
                .map { Arguments.of(it[0], it[1], it[2], it[3]) }
                .stream()

        // The cartesian power of n of a set S will generate a tuple of n elements.
        private fun <T> List<T>.cartesianPower(n: Int): List<List<T>> =
            if (n == 0) listOf(emptyList())
            else cartesianPower(n - 1).flatMap { list -> this.map { element -> list + element } }

        private fun meetsRoutingToBothDepartmentsCriteria(scenario: List<Boolean>): Boolean {
            val (internet, tv, mobile, landline) = scenario
            return (internet || tv) && (mobile || landline)
        }

        internal enum class Department(val id: String) {
            BROADBAND("Broadband"), TELECOM("Telecom"), BOTH("Broadband & Telecom")
        }

        internal fun routingTo(department: Department) = "Routing to ${department.id} ${
            if (department == Department.BOTH) "departments" else "department"
        }!"
    }
}