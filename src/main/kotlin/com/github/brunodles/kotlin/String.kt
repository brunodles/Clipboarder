package com.github.brunodles.kotlin

import java.nio.charset.Charset

/**
 * Created by bruno on 13/08/16.
 */
fun String.encondeInto(charset: String): String {
    return this.toByteArray().toString(Charset.forName(charset))
}

fun String.notEquals(string: String?): Boolean {
    return !this.equals(string)
}