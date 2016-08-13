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

class ClipboardController : ClipboardOwner {

    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    var clipValue = ""
    val thread = Thread(Runnable {
        while (!Thread.interrupted()) {
            val clip = clipboard.getClipboardText()
            onNewValue(clip)
            Thread.sleep(1000)

        }
    });
    val flavorListener = FlavorListener { onNewValue(clipboard.getClipboardText()) }

    fun start() {
        clipboard.addFlavorListener(flavorListener)
        thread.start()
    }

    fun stop() {
        clipboard.removeFlavorListener(flavorListener)
        thread.interrupt()
        thread.join()
    }

    private fun onNewValue(clip: String) {
        if (clip != clipValue) {
            clipValue = clip
            println(clip)
        }
    }

    override fun lostOwnership(clipboard: Clipboard?, contents: Transferable?) {
        println("lostOwnership")
    }


}