package io.github.u.ways.domain

/**
 * This is a data class that holds information about the customer's
 * order request and their contact information.
 *
 * @see: https://kotlinlang.org/docs/data-classes.html
 */
data class Request(
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val internet: Boolean,
    val tv: Boolean,
    val mobile: Boolean,
    val landline: Boolean
)
