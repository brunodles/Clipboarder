package com.github.brunodles.desktop.util

import java.awt.Toolkit
import java.awt.datatransfer.*
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

/**
 * Created by bruno on 13/08/16.
 */
private val ENCODING = "ISO-8859-9"

fun Clipboard.getClipboardText(): String {
    var result = ""
    val contents = getContents(this)
    val hasTransferableText = contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)
    if (hasTransferableText) {
        try {
            result = contents!!.getTransferData(
                    DataFlavor.stringFlavor) as String
        } catch (ex: UnsupportedFlavorException) {
            println(ex)
            ex.printStackTrace()
        } catch (ex: IOException) {
            println(ex)
            ex.printStackTrace()
        }
    }
    try {

        return result.encondeInto(ENCODING)
    } catch (ex: UnsupportedEncodingException) {
    }
    return result
}

fun Clipboard.clearClipBoard(owner: ClipboardOwner) {
    setClipboardText("", owner)
}

fun Clipboard.setClipboardText(text: String, owner: ClipboardOwner) {
    try {
        setContents(StringSelection(text.encondeInto(ENCODING)), owner)
    } catch (ex: UnsupportedEncodingException) {
        setContents(StringSelection(text), owner)
    }
}