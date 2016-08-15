package com.github.brunodles.clipboarder

import com.github.brunodles.desktop.util.ClipboardController
import com.github.brunodles.desktop.util.InputReader
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by bruno on 13/08/16.
 */

private val fileDao = FileDao(".clipboard")
private val controller = ClipboardController() { txt -> fileDao.save(System.currentTimeMillis().toString(), txt) }
private var files: HashMap<String, List<String>>? = null

fun main(args: Array<String>) {
    controller.start()

    val format = SimpleDateFormat("HH:mm:ss")
    println("Start ${format.format(Date())}")

    InputReader("exit")
            .add(Regex("list"), ::list)
            .add(Regex("apply (\\d+)"), ::apply)
            .start()

    println("Stop ${format.format(Date())}")
    controller.stop()
}

fun apply(matchResult: MatchResult) {
    val id: String = matchResult.groupValues[1]
    val text = files()[id]?.joinToString("\n")
    if (text == null)
        controller.cleanText()
    else
        controller.setText(text)
}

fun list(matchResult: MatchResult) {
    files().forEach { name, s -> println("$name -> ${s.joinToString(" ")}") }
}

private fun files(): HashMap<String, List<String>> {
    files = fileDao.list()
    return files as HashMap<String, List<String>>
}
