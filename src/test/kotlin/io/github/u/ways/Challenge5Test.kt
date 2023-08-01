package io.github.u.ways

import io.github.u.ways.Challenge1Test.Companion.Department
import io.github.u.ways.Challenge1Test.Companion.routingTo
import io.github.u.ways.Challenge4Test.Companion.PRODUCT_TO_CODE_MAP
import io.github.u.ways.domain.Request
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/** NOTE:
 *   All tests here SHOULD be derived from previous challenges.
 *   I've decided to copy/paste the tests and add an adapter to the request
 *   instead of using inheritance because I wanted to keep the tests independent
 *   in the interest of readability purposes.
 *
 *   This is an experimental approach and I might change it in the future.
 */
class Challenge5Test : ChallengeBaseTest() {

    @Nested
    inner class Challenge1Scenarios {
        @Test
        fun `should route to broadband department when internet product is selected`() {
            challenge5(adapt(withRequest(internet = true)))
            withExpectedOutput(routingTo(Department.BROADBAND))
        }

        @Test
        fun `should route to broadband department when voip product is selected`() {
            challenge5(adapt(withRequest(voip = true)))
            withExpectedOutput(routingTo(Department.BROADBAND))
        }

        @Test
        fun `should route to telecom department when mobile product is selected`() {
            challenge5(adapt(withRequest(mobile = true)))
            withExpectedOutput(routingTo(Department.TELECOM))
        }

        @Test
        fun `should route to telecom department when landline product is selected`() {
            challenge5(adapt(withRequest(landline = true)))
            withExpectedOutput(routingTo(Department.TELECOM))
        }

        @ParameterizedTest(name = "should route to both departments when a product from each department is selected: internet={0}, voip={1}, mobile={2}, landline={3}")
        @MethodSource("io.github.u.ways.Challenge1Test#scenariosThatRouteToBothDepartments")
        fun `should route to both departments when a product from each department is selected`(
            internet: Boolean, voip: Boolean = false, mobile: Boolean, landline: Boolean = false,
        ) {
            challenge5(adapt(withRequest(internet = internet, voip = voip, mobile = mobile, landline = landline)))
            withExpectedOutput(routingTo(Department.BOTH))
        }
    }

    @Nested
    inner class Challenge2Scenarios {
        @Test
        fun `should invalidate request when no products are requested`() {
            challenge5(adapt(withRequest(internet = false, voip = false, mobile = false, landline = false)))
            withExpectedOutput("No products requested!")
        }

        @ParameterizedTest(name = "should consider the request valid when a product is requested: internet={0}, voip={1}, mobile={2}, landline={3}")
        @MethodSource("io.github.u.ways.Challenge2Test#provideProductCombinations")
        fun `should consider the request valid when at least one product is requested`(
            internet: Boolean,
            voip: Boolean,
            mobile: Boolean,
            landline: Boolean,
        ) {
            challenge5(
                adapt(
                    withRequest(
                        internet = internet,
                        voip = voip,
                        mobile = mobile,
                        landline = landline,
                    )
                )
            )
            shouldNotOutput("No products requested!")
        }
    }

    @Nested
    inner class Challenge3Scenarios {
        @Test
        fun `should not output an error message when all mandatory fields are present`() {
            challenge5(
                adapt(
                    withRequest(
                        name = "John Doe", email = "john.doe@email.com",
                        phone = "0123456789", address = "123, Some Street, Some City, Some Country",
                        internet = true, voip = false, mobile = false, landline = false
                    )
                )
            )
            shouldNotOutput("A mandatory field is missing!")
        }

        @ParameterizedTest(name = "should invalidate a request when \"{0}\" is empty")
        @MethodSource("io.github.u.ways.Challenge3Test#provideInvalidRequests")
        fun `should invalidate a request when a mandatory field is empty`(missingField: String) {
            challenge5(adapt(withRequest().let { r ->
                when (missingField) {
                    "name" -> r.copy(name = "")
                    "email" -> r.copy(email = "")
                    "phone" -> r.copy(phone = "")
                    "address" -> r.copy(address = "")
                    else -> r.copy("", "", "", "")
                }
            }))
            withExpectedOutput("A mandatory field is missing!")
        }
    }

    @Nested
    inner class Challenge4Scenarios {
        @ParameterizedTest(name = "should output \"{1}\" when the subjected product is requested.")
        @MethodSource("io.github.u.ways.Challenge4Test#provideSingleProductRequests")
        fun `should print product codes`(request: Request, expectedOutput: String) {
            challenge5(adapt(request))
            withExpectedOutput(expectedOutput)
        }

        @Test
        fun `should output all product codes when everything is requested`() {
            challenge5(adapt(withRequest(internet = true, voip = true, mobile = true, landline = true)))
            PRODUCT_TO_CODE_MAP.values.forEach(::withExpectedOutput)
        }
    }

    companion object {
        private fun adapt(withRequest: Request): List<String> = listOf(
            withRequest.name,
            withRequest.email,
            withRequest.phone,
            withRequest.address,
            withRequest.internet.toString(),
            withRequest.voip.toString(),
            withRequest.mobile.toString(),
            withRequest.landline.toString()
        )
    }
}
