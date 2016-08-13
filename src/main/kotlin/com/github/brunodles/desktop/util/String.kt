package com.github.brunodles.desktop.util

import java.nio.charset.Charset

/**
 * Created by bruno on 13/08/16.
 */
fun String.encondeInto(charset:String): String {
    return this.toByteArray().toString(Charset.forName(charset))
}