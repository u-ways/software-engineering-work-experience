package io.github.u.ways

import io.github.u.ways.domain.Request

fun challenge1(request: Request) {
    println(
        """
            I've received the following request: 
            - Name: ${request.name}
            - Email: ${request.email}
            - Phone: ${request.phone}
            - Address: ${request.address}
            - Internet: ${request.internet}
            - TV: ${request.tv}
            - Mobile: ${request.mobile}
            - Landline: ${request.landline}
        """.trimIndent()
    )
}