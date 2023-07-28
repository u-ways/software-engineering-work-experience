package io.github.u.ways.config

object Constants {
    /**
     * This is the name of the system property that will be used to determine if the test should be inverted.
     * This is used for reverse the challenge matcher tests to ensure they're not passing by accident when the
     * challenges are not implemented. (i.e. everything should fail when the challenges are not implemented)
     */
    internal const val INVERSE_MATCHER = "inverseMatcher"
}