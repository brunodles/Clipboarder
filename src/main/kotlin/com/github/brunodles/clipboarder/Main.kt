package com.github.brunodles.clipboarder

import com.github.brunodles.desktop.util.ClipboardController

/**
 * Created by bruno on 13/08/16.
 */

fun main(args: Array<String>) {
    val controller = ClipboardController()
    controller.start()
    Thread.sleep(60000)
    controller.stop()
}