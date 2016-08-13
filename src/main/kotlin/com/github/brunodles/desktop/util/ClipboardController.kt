package com.github.brunodles.desktop.util

import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.ClipboardOwner
import java.awt.datatransfer.FlavorListener
import java.awt.datatransfer.Transferable

/**
 * Created by bruno on 13/08/16.
 */
class ClipboardController : ClipboardOwner {

    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    var clipValue = ""
    val thread = Thread(Runnable {
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