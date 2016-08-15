package com.github.brunodles.desktop.util

import com.github.brunodles.clipboarder.FileDao
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.ClipboardOwner
import java.awt.datatransfer.FlavorListener
import java.awt.datatransfer.Transferable

/**
 * Created by bruno on 13/08/16.
 */

class ClipboardController(val valueClipboardListener: (String) -> Unit) : ClipboardOwner {

    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    private var clipValue = ""
    private val thread = Thread(Runnable {
        while (!Thread.interrupted()) {
            val clip = clipboard.getClipboardText()
            onNewValue(clip)
            try {
                Thread.sleep(1000)
            } catch(e: InterruptedException) {
                break
            }
        }
    });
    private val flavorListener = FlavorListener { onNewValue(clipboard.getClipboardText()) }

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
            valueClipboardListener.invoke(clip)
        }
    }

    override fun lostOwnership(clipboard: Clipboard?, contents: Transferable?) {
        println("lostOwnership")
    }

    fun setText(text: String) {
        clipboard.setClipboardText(text, this)
    }

    fun cleanText() {
        clipboard.clearClipBoard(this)
    }
}