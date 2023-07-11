package io.github.u.ways

fun challenge5(request: List<String>): Boolean {
    println(
        """
            I've received the following request:
            - Name: ${request[0]}
            - Email: ${request[1]}
            - Phone: ${request[2]}
            - Address: ${request[3]}
            - Internet: ${request[4]}
            - TV: ${request[5]}
            - Mobile: ${request[6]}
            - Landline: ${request[7]}
        """.trimIndent()
    )
    return false
}