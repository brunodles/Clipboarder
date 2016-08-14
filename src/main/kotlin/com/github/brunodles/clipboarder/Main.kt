package com.github.brunodles.clipboarder

import com.github.brunodles.desktop.util.ClipboardController
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by bruno on 13/08/16.
 */

fun main(args: Array<String>) {
    val fileDao = FileDao(".clipboard")
    val controller = ClipboardController() { txt -> fileDao.save(System.currentTimeMillis().toString(), txt) }
    controller.start()
    val format = SimpleDateFormat("HH:mm:ss")
    println("Start ${format.format(Date())}")
    Thread.sleep(15000)
    println("Stop ${format.format(Date())}")
    controller.stop()
}
